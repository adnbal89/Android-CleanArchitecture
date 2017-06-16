package com.adnanbal.fxdedektifi.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.sample.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.util.List;

/**
 * {@link RestApiPosition} implementation for retrieving data from the network.
 */
public class RestApiPositionImpl implements RestApiPosition {

  private final Context context;
  private final PositionEntityJsonMapper positionEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param positionEntityJsonMapper {@link PositionEntityJsonMapper}.
   */
  public RestApiPositionImpl(Context context, PositionEntityJsonMapper positionEntityJsonMapper) {
    if (context == null || positionEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.positionEntityJsonMapper = positionEntityJsonMapper;
  }

  @Override
  public Observable<List<PositionEntity>> positionEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responsePositionEntities = getPositionEntitiesFromApi();
          if (responsePositionEntities != null) {
            emitter.onNext(positionEntityJsonMapper.transformPositionEntityCollection(
                responsePositionEntities));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  @Override
  public Observable<PositionEntity> positionEntityById(int positionId) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responsePositionDetails = getPositionDetailsFromApi(positionId);
          if (responsePositionDetails != null) {
            emitter
                .onNext(positionEntityJsonMapper.transformPositionEntity(responsePositionDetails));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  private String getPositionEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
  }

  private String getPositionDetailsFromApi(int positionId) throws MalformedURLException {
    String apiUrl = API_URL_GET_USER_DETAILS + positionId;
    return ApiConnection.createGET(apiUrl).requestSyncCall();
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
