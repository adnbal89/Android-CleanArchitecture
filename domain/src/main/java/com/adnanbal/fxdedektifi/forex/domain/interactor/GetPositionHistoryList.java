package com.adnanbal.fxdedektifi.forex.domain.interactor;

import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionHistoryRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Position}.
 */
public class GetPositionHistoryList extends UseCase<List<Position>, Void> {

  private final PositionHistoryRepository positionHistoryRepository;

  @Inject
  GetPositionHistoryList(PositionHistoryRepository positionHistoryRepository,
      ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.positionHistoryRepository = positionHistoryRepository;
  }

  @Override
  Observable<List<Position>> buildUseCaseObservable(Void unused) {
    return this.positionHistoryRepository.getClosedPositionsList();
  }

}
