/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetPositionDetailsTest {

  private static final int POSITION_ID = 123;

//  private GetPositionDetails getPositionDetails;

  @Mock
  private PositionRepository mockPositionRepository;
  @Mock
  private ThreadExecutor mockThreadExecutor;
  @Mock
  private PostExecutionThread mockPostExecutionThread;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  // TOdo : complete the test
  @Before
  public void setUp() {
//    getPositionDetails = new GetPositionDetails(mockPositionRepository, mockThreadExecutor,
//        mockPostExecutionThread);
  }

  // TOdo : complete the test after class creation

  @Test
  public void testGetPositionDetailsUseCaseObservableHappyCase() {
//    getPositionDetails.buildUseCaseObservable(Params.forPosition(POSITION_ID));

    verify(mockPositionRepository).getOnePosition(POSITION_ID);
    verifyNoMoreInteractions(mockPositionRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldFailWhenNoOrEmptyParameters() {
    expectedException.expect(NullPointerException.class);
//    getPositionDetails.buildUseCaseObservable(null);
  }
}
