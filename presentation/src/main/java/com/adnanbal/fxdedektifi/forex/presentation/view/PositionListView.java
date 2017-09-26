package com.adnanbal.fxdedektifi.forex.presentation.view;

import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern. In this case is used as a
 * view representing a list of {@link PositionModel}.
 */
public interface PositionListView extends LoadDataView {

  /**
   * Render a position list in the UI.
   *
   * @param positionModelCollection The collection of {@link PositionModel} that will be shown.
   */
  void renderPositionList(Collection<PositionModel> positionModelCollection);

  /**
   * View a {@link PositionModel} profile/details.
   *
   * @param positionModel The position that will be shown.
   */
  void viewPosition(PositionModel positionModel);

  /**
   * Close a {@link PositionModel} and remove it from the list.
   *
   * @param positionModel The position that will be closed.
   */
  void closePositionConfirmedOnline(PositionModel positionModel);

  /**
   * Confirmed to open a {@link PositionModel}.
   *
   * @param positionModel The position that will be opened.
   */
  void openPositionConfirmedOnline(PositionModel positionModel);
}
