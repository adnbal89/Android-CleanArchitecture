package com.adnanbal.fxdedektifi.forex.data.net;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

public interface RestApiPosition {


  String API_BASE_URL_POSITION =
      "http://94.102.9.6:3000/";

  String API_BASE_URL_SIGNAL =
      "http://94.102.9.6:3001/";

  String API_BASE_URL_HISTORY =
      "http://94.102.9.6:3002/";

  /**
   * Api url for getting all positions
   */
  String API_URL_GET_POSITION_LIST =
      API_BASE_URL_POSITION + "position" + "?open=true" + "&_sort=id&_order=desc";
  /**
   * Api url for getting a position profile: Remember to concatenate id + 'json'
   */
  String API_URL_GET_POSITION_DETAILS = API_BASE_URL_POSITION + "position" + "/";

  /**
   * Api url signals
   */
  String API_URL_GET_SIGNAL = API_BASE_URL_SIGNAL + "signal" + "/";
  /**
   * Api url for deleting a position
   */
  String API_URL_DELETE_POSITION = API_BASE_URL_POSITION + "position" + "/";

  /**
   * Api url for Post a position history.
   */
  String API_URL_POST_POSITION_HISTORY =
      API_BASE_URL_HISTORY + "positionHistory";


  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PositionEntity}.
   */
  Observable<List<PositionEntity>> positionEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link PositionEntity}.
   *
   * @param positionId The positions id used to get position data.
   */
  Observable<PositionEntity> positionEntityById(final String positionId);

  /**
   * Retrieves an {@link Observable} which will emit a result
   * {@link PositionEntity} if remove
   * operation is successful.
   *
   * @param positionId The positions id used to get position data.
   */
  Observable<Boolean> removeEntityById(final String positionId);

  /**
   * Retrieves an {@link Observable} which will emit a result
   * {@link PositionEntity} if add
   * operation is successful.
   *
   * @param positionEntity The position entity used to add position data to cloud.
   */
  Observable<Boolean> addEntityToCloud(PositionEntity positionEntity);

}
