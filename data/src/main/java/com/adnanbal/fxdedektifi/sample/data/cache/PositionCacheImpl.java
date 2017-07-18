package com.adnanbal.fxdedektifi.sample.data.cache;

import android.content.Context;
import com.adnanbal.fxdedektifi.sample.data.cache.serializer.Serializer;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.exception.PositionNotFoundException;
import com.adnanbal.fxdedektifi.sample.domain.executor.ThreadExecutor;
import io.reactivex.Observable;
import java.io.File;
import javax.inject.Inject;

public class PositionCacheImpl implements PositionCache {

  private static final String SETTINGS_FILE_NAME = "com.adnanbal.fxdedektifi.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "position_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final Serializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link PositionCacheImpl}.
   *
   * @param context A
   * @param serializer {@link Serializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject
  PositionCacheImpl(Context context, Serializer serializer,
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


  private PositionEntity getEntityFromCache(final int positionId) {
    final File positionEntityFile = PositionCacheImpl.this.buildFile(positionId);
    final String fileContent = PositionCacheImpl.this.fileManager
        .readFileContent(positionEntityFile);
    final PositionEntity positionEntity =
        PositionCacheImpl.this.serializer.deserialize(fileContent, PositionEntity.class);
    return positionEntity;
  }


  @Override
  public Observable<PositionEntity> get(final int positionId) {
    return Observable.create(emitter -> {
      final PositionEntity positionEntity = getEntityFromCache(positionId);

      if (positionEntity != null) {
        emitter.onNext(positionEntity);
        emitter.onComplete();
      } else {
        emitter.onError(new PositionNotFoundException());
      }
    });
  }

  //Todo : Correct method to return boolean
  @Override
  public Observable<Boolean> put(PositionEntity positionEntity) {
    return Observable.create(emitter -> {
      if (positionEntity != null) {
        final File positionEntityFile = this.buildFile(positionEntity.getPositionId());
        if (!isCached(positionEntity.getPositionId())) {
          final String jsonString = this.serializer.serialize(positionEntity, PositionEntity.class);
          this.executeAsynchronously(
              new CacheWriter(this.fileManager, positionEntityFile, jsonString));
          setLastCacheUpdateTimeMillis();
        } else {

          //Todo : check
          PositionEntity entityFromCache = getEntityFromCache(positionEntity.getPositionId());
          if (entityFromCache.isOpen() != positionEntity.isOpen()) {
            delete(positionEntity.getPositionId());
            put(positionEntity);
          }
        }

        //TODO : correct here
        emitter.onNext(true);
        emitter.onComplete();
      } else {
        emitter.onError(new PositionNotFoundException());
      }


    });
  }

//TODO : correct
  @Override
  public Observable<Boolean> delete(int positionId) {
    return Observable.create(emitter -> {

      final File positionEntityFile = this.buildFile(positionId);

      if (positionEntityFile != null) {
        if (isCached(positionId)) {
          this.executeAsynchronously(
              new CacheDeleter(this.fileManager, positionEntityFile));
          setLastCacheUpdateTimeMillis();

        }
        emitter.onNext(true);
        emitter.onComplete();
      } else {
        emitter.onError(new PositionNotFoundException());
      }


    });
  }

  @Override
  public boolean isCached(int positionId) {
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
  private File buildFile(int positionId) {
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

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheDeleter implements Runnable {

    private final FileManager fileManager;
    private final File fileToDelete;

    CacheDeleter(FileManager fileManager, File fileToDelete) {
      this.fileManager = fileManager;
      this.fileToDelete = fileToDelete;
    }

    @Override
    public void run() {
      this.fileManager.deleteFromFile(fileToDelete);
    }
  }


}
