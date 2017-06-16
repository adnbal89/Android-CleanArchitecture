/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adnanbal.fxdedektifi.sample.domain.interactor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.adnanbal.fxdedektifi.sample.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.sample.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.sample.domain.repository.PositionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetPositionListTest {

  private GetPositionList getPositionList;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private PositionRepository mockPositionRepository;

  @Before
  public void setUp() {
    getPositionList = new GetPositionList(mockPositionRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetPositionListUseCaseObservableHappyCase() {
    getPositionList.buildUseCaseObservable(null);

    verify(mockPositionRepository).getPositionsList();
    verifyNoMoreInteractions(mockPositionRepository);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
