/*
 * *
 *  * Copyright (C) 2017 Adnan BAL Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.adnanbal.fxdedektifi.forex.data.cache;

import android.content.Context;
import com.adnanbal.fxdedektifi.forex.data.cache.serializer.Serializer;
import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.data.exception.PositionNotFoundException;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import java.io.File;
import javax.inject.Inject;

public class PositionHistoryCacheImpl implements PositionHistoryCache {

  private static final String SETTINGS_FILE_NAME = "com.adnanbal.fxdedektifi.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "positionHistory_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final Serializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link PositionHistoryCacheImpl}.
   *
   * @param context A
   * @param serializer {@link Serializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject
  PositionHistoryCacheImpl(Context context, Serializer serializer,
      FileManager fileManager, ThreadExecutor executor) {
    if (context == null || serializer == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.serializer = serializer;
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

  @Override
  public Observable<PositionEntity> get(final String positionId) {
    return Observable.create(emitter -> {
      final File positionEntityFile = PositionHistoryCacheImpl.this.buildFile(positionId);
      final String fileContent = PositionHistoryCacheImpl.this.fileManager
          .readFileContent(positionEntityFile);
      final PositionEntity positionEntity =
          PositionHistoryCacheImpl.this.serializer.deserialize(fileContent, PositionEntity.class);

      if (positionEntity != null) {
        emitter.onNext(positionEntity);
        emitter.onComplete();
      } else {
        emitter.onError(new PositionNotFoundException());
      }
    });
  }

  @Override
  public void put(PositionEntity positionEntity) {
    if (positionEntity != null) {
      final File positionEntityFile = this.buildFile(positionEntity.getId());
      if (!isCached(positionEntity.getId())) {
        final String jsonString = this.serializer.serialize(positionEntity, PositionEntity.class);
        this.executeAsynchronously(
            new CacheWriter(this.fileManager, positionEntityFile, jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

  @Override
  public boolean isCached(String positionId) {
    final File positionEntityFile = this.buildFile(positionId);
    return this.fileManager.exists(positionEntityFile);
  }

  @Override
  public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  @Override
  public void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param positionId The id position to build the file.
   * @return A valid file.
   */
  private File buildFile(String positionId) {
    final StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(positionId);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    final long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {

    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override
    public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {

    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override
    public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
