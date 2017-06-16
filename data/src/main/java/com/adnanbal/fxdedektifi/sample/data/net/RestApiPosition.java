package com.adnanbal.fxdedektifi.sample.data.net;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

public interface RestApiPosition {

  String API_BASE_URL =
      "http://192.168.1.30:3000/";

  /** Api url for getting all positions */
  String API_URL_GET_USER_LIST = API_BASE_URL + "position";
  /** Api url for getting a position profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "position" + "/";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PositionEntity}.
   */
  Observable<List<PositionEntity>> positionEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link PositionEntity}.
   *
   * @param positionId The positions id used to get position data.
   */
  Observable<PositionEntity> positionEntityById(final int positionId);


}
