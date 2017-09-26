package com.adnanbal.fxdedektifi.forex.domain.interactor;

import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Position}.
 */
public class GetPositionList extends UseCase<List<Position>, Void> {

  private final PositionRepository positionRepository;

  @Inject
  GetPositionList(PositionRepository positionRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.positionRepository = positionRepository;
  }

  @Override
  Observable<List<Position>> buildUseCaseObservable(Void unused) {
    return this.positionRepository.getPositionsList();
  }

}
