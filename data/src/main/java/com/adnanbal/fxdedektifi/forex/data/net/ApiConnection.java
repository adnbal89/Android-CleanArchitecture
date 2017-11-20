/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adnanbal.fxdedektifi.forex.data.net;

import android.support.annotation.Nullable;
import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements Callable<String> {


  private static final String CONTENT_TYPE_LABEL = "Content-Type";
  private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
  int i = 0;
  private URL url;
  private String response;

  private ApiConnection(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  static ApiConnection createGET(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  static ApiConnection createDelete(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  static ApiConnection createPost(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  static ApiConnection createPut(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  static ApiConnection createPatch(String url) throws MalformedURLException {
    return new ApiConnection(url);
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  String request_getSyncCall() {
    getRequestToApi();
    i++;
    return response;
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  String request_postSyncCall(PositionEntity positionEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(positionEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    postRequestToApi(body);
    i++;
    return response;
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  String request_postSyncCall(NewPositionEntity newPositionEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(newPositionEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    postRequestToApi(body);
    i++;
    return response;
  }


  @Nullable
  String request_postNewSignalSyncCall(NewPositionEntity newPositionEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(newPositionEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    postRequestToApiNewSignal(body);
    i++;
    return response;
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  String request_postUserLoginDetailEntitySyncCall(UserLoginDetailsEntity userLoginDetailsEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(userLoginDetailsEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    postRequestToApi(body);
    i++;
    return response;
  }


  @Nullable
  String request_putSyncCall(PositionEntity positionEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(positionEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    postRequestToApi(body);
    i++;
    return response;
  }

  @Nullable
  String request_putNewSignalSyncCall(NewPositionEntity newPositionEntity) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(newPositionEntity);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    putRequestToApi(body);
    i++;
    return response;
  }


  @Nullable
  String request_patchSyncCall(Map<String, Boolean> userSignalMap) {
    final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();
    String jsonInString = gson.toJson(userSignalMap);

    RequestBody body = RequestBody.create(JSON, jsonInString);

    patchRequestToApi(body);
    i++;
    return response;
  }


  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  String request_deleteSyncCall() {
    deletePatchRequestToApi();
    i++;
    return response;
  }

//  @Nullable
//  String request_deleteSyncCall(Map<String, Boolean> userSignalMap) {
//    deleteRequestToApi(userSignalMap);
//    i++;
//    return response;
//  }

  private void getRequestToApi() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .get()
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void deleteRequestToApi() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .delete()
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void deletePatchRequestToApi() {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .delete()
        .build();

    try {
      Response response = okHttpClient.newCall(request).execute();
      this.response = String.valueOf(response.code());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void postRequestToApiNewSignal(RequestBody requestBody) {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .post(requestBody)
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void postRequestToApi(RequestBody requestBody) {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .put(requestBody)
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void putRequestToApi(RequestBody requestBody) {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .put(requestBody)
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void post1RequestToApi(RequestBody requestBody) {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .post(requestBody)
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void patchRequestToApi(RequestBody requestBody) {
    OkHttpClient okHttpClient = this.createClient();
    final Request request = new Request.Builder()
        .url(this.url)
        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
        .patch(requestBody)
        .build();

    try {
      this.response = okHttpClient.newCall(request).execute().body().string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private OkHttpClient createClient() {
    final OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS)
        .readTimeout(10000, TimeUnit.MILLISECONDS);

    return okHttpClient;
  }

  @Override
  public String call() throws Exception {
    return request_getSyncCall();
  }
}
