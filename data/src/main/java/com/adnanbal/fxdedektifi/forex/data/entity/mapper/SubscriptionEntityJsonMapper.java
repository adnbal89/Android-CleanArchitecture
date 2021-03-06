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

package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.SubscriptionEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class SubscriptionEntityJsonMapper {

  private final Gson gson;

  @Inject
  public SubscriptionEntityJsonMapper() {
    this.gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))

        .create();

  }

  /**
   * Transform from valid json string to {@link SubscriptionEntity}.
   *
   * @param subscriptionJsonResponse A json representing a subscription.
   * @return {@link SubscriptionEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public SubscriptionEntity transformSubscriptionEntity(String subscriptionJsonResponse)
      throws JsonSyntaxException {
    final Type subscriptionEntityType = new TypeToken<SubscriptionEntity>() {
    }.getType();
    return this.gson.fromJson(subscriptionJsonResponse, subscriptionEntityType);
  }

  /**
   * Transform from valid json string to List of {@link SubscriptionEntity}.
   *
   * @param subscriptionListJsonResponse A json representing a collection of subscriptions.
   * @return List of {@link SubscriptionEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<SubscriptionEntity> transformSubscriptionEntityCollection(
      String subscriptionListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfSubscriptionEntityType = new TypeToken<List<SubscriptionEntity>>() {
    }.getType();

    List<SubscriptionEntity> list = this.gson
        .fromJson(subscriptionListJsonResponse, listOfSubscriptionEntityType);
    return this.gson.fromJson(subscriptionListJsonResponse, listOfSubscriptionEntityType);
  }


}
