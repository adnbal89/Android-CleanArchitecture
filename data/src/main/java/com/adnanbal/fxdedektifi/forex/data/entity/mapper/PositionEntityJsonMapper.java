package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
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

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class PositionEntityJsonMapper {

  private final Gson gson;

  @Inject
  public PositionEntityJsonMapper() {
    this.gson = new GsonBuilder().registerTypeAdapter(Date.class,
        (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(
            jsonElement.getAsJsonPrimitive().getAsLong()))
        .registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(
                date.getTime()))
        .create();
  }

  /**
   * Transform from valid json string to {@link PositionEntity}.
   *
   * @param positionJsonResponse A json representing a position.
   * @return {@link PositionEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public PositionEntity transformPositionEntity(String positionJsonResponse)
      throws JsonSyntaxException {
    final Type positionEntityType = new TypeToken<PositionEntity>() {
    }.getType();
    return this.gson.fromJson(positionJsonResponse, positionEntityType);
  }

  /**
   * Transform from valid json string to List of {@link PositionEntity}.
   *
   * @param positionListJsonResponse A json representing a collection of positions.
   * @return List of {@link PositionEntity}.
   * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<PositionEntity> transformPositionEntityCollection(String positionListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfPositionEntityType = new TypeToken<List<PositionEntity>>() {
    }.getType();

    List<PositionEntity> list = this.gson
        .fromJson(positionListJsonResponse, listOfPositionEntityType);
    return this.gson.fromJson(positionListJsonResponse, listOfPositionEntityType);
  }
}
