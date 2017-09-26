package com.adnanbal.fxdedektifi.forex.domain.repository;

import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import io.reactivex.Observable;
import java.util.Date;
import java.util.List;

/**
 * Interface that represents a Repository for getting {@link Position} related data.
 */
public interface PositionRepository {

  /**
   * Get an {@link Observable} which will emit a List of {@link Position}.
   */
  Observable<List<Position>> getPositionsList();

  /**
   * Get an {@link Observable} which will emit a {@link Position}.
   *
   * @param positionId The position id used to retrieve position data.
   */
  Observable<Position> getOnePosition(final String positionId);


  /**
   * Get an {@link Observable} which will emit a closed {@link Position}.
   */
  Observable<Boolean> closeOpenPosition(final String positionId);

  /**
   * Get an {@link Observable} which will emit a closed {@link Position}.
   */
  Observable<Boolean> openPosition(String positionId, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, Date date);

}