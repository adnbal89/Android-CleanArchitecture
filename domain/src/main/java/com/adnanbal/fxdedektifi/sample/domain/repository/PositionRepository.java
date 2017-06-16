package com.adnanbal.fxdedektifi.sample.domain.repository;

import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import io.reactivex.Observable;
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
  Observable<Position> getOnePosition(final int positionId);
}