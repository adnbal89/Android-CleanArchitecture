/*
 * *
 *  * Copyright (C) 2017 Adnan BAL Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.adnanbal.fxdedektifi.forex.presentation.view.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.NewPositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.NewPositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.NewSignalPresenter;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.firebase.client.ServerValue;
import javax.inject.Inject;

public class NewSignalFragment extends BaseFragment {

  private static final String SIGNAL_OPERATION_NEW = "new";
  private static final String SIGNAL_OPERATION_EDIT = "edit";
  private static final String SIGNAL_OPERATION_CLOSE = "close";

  Builder mMaterialDialog;
  Builder mMaterialDialogConfirmEdit;


  String signalOperation;

  SignalModel signalModelToEdit;
  /*
 * Butterknife unbinder, will be executed to clear callbacks
  */
  Unbinder unbinder;
  @BindView(R.id.button_close)
  Button button_close;
  @BindView(R.id.button_ok)
  Button button_ok;
  @BindView(R.id.button_cancel)
  Button button_cancel;
  @BindView(R.id.spinner_pair)
  Spinner spinner_pair;
  @BindView(R.id.spinner_buy_sell)
  Spinner spinner_buy_sell;
  @BindView(R.id.editText_opening_price)
  EditText editText_opening_price;
  @BindView(R.id.editText_closing_price)
  EditText editText_closing_price;
  @BindView(R.id.editText_stop_loss_price)
  EditText editText_stop_loss_price;
  @BindView(R.id.editText_take_profit_price)
  EditText editText_take_profit_price;
  @BindView(R.id.editText_comment)
  EditText editTextComment;
  @BindView(R.id.textView_closing_price)
  TextView textView_closing_price;
  @BindView(R.id.textView_title_editSignal)
  TextView textView_title_editSignal;
  @BindView(R.id.checkbox_sl)
  CheckBox checkbox_sl;
  @BindView(R.id.checkbox_tp)
  CheckBox checkbox_tp;


  @Inject
  NewSignalPresenter newSignalPresenter;

  public NewSignalFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(NewPositionComponent.class).inject(this);

    mMaterialDialog = new MaterialDialog.Builder(getContext());
    mMaterialDialogConfirmEdit = new MaterialDialog.Builder(getContext());
//    UserLoginDetailsModel userLoginDetailsModel = new UserLoginDetailsModel();
//    userLoginDetailsModel.setUserUid(AndroidApplication.userUid);
//    userLoginDetailsPresenter.addUserLoginDetails(userLoginDetailsModel);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_new_signal, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);

    Bundle bundle = getArguments();
    signalModelToEdit = (SignalModel) bundle.getSerializable("signalModelToEdit");

    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ArrayAdapter<CharSequence> adapterPair;
    ArrayAdapter<CharSequence> adapterBuy_Sell;

    //Edit mode is ON. So signalModelToEdit is not null
    if (signalModelToEdit != null) {
      adapterPair = new ArrayAdapter<CharSequence>(getActivity(),
          android.R.layout.simple_spinner_item);

      adapterPair.add(signalModelToEdit.getPair());
      spinner_pair.setEnabled(false);
      signalOperation = SIGNAL_OPERATION_EDIT;
      button_ok.setText(getResources().getString(R.string.text_dialog_view_OK));

      checkbox_sl.setEnabled(true);
      checkbox_tp.setEnabled(true);

      if (signalModelToEdit.isHitTakeProfit()) {
        checkbox_tp.setChecked(true);
      } else {
        checkbox_tp.setChecked(false);

      }
      if (signalModelToEdit.isHitStopLoss()) {
        checkbox_sl.setChecked(true);
      } else {
        checkbox_sl.setChecked(false);
      }
    }
    //New signal page
    else {
      adapterPair = ArrayAdapter.createFromResource(
          getActivity(), R.array.array_pairs, android.R.layout.simple_spinner_item);
      adapterPair.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      signalOperation = SIGNAL_OPERATION_NEW;
      button_ok.setText(getResources().getString(R.string.add_new_Signal));

      checkbox_sl.setEnabled(false);
      checkbox_tp.setEnabled(false);
    }

    spinner_pair.setAdapter(adapterPair);

    adapterBuy_Sell = ArrayAdapter.createFromResource(
        getActivity(), R.array.array_buy_sell, android.R.layout.simple_spinner_item);

    adapterBuy_Sell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner_buy_sell.setAdapter(adapterBuy_Sell);

    if (signalModelToEdit != null) {
      spinner_buy_sell.setSelection(signalModelToEdit.isBuy_sell() ? 0 : 1);
      editText_opening_price
          .setText(Float.valueOf((float) signalModelToEdit.getOpeningPrice()).toString());

      if (signalModelToEdit.getTake_profit_price() != null) {
        editText_take_profit_price
            .setText(Double.valueOf(signalModelToEdit.getTake_profit_price()).toString());
      }

      if (signalModelToEdit.getStop_loss_price() != null) {
        editText_stop_loss_price
            .setText(Double.valueOf(signalModelToEdit.getStop_loss_price()).toString());
      }

      if (signalModelToEdit.getComment() != null) {
        editTextComment.setText(signalModelToEdit.getComment());
      }

      if (signalModelToEdit.getClosingPrice() != 0) {
        editText_closing_price
            .setText(Double.valueOf(signalModelToEdit.getClosingPrice()).toString());
      }

    }

    button_cancel.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().finish();
      }
    });

    button_ok.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (signalModelToEdit != null) {

          mMaterialDialogConfirmEdit.title(R.string.edit_signal)
              .content(R.string.text_dialog_view_Edit_Signal_content)
              .positiveText(R.string.ok)
              .negativeText(R.string.text_dialog_view_Cancel)
              .cancelable(true);

          mMaterialDialogConfirmEdit.show();

          mMaterialDialogConfirmEdit
              .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                  if (!editText_opening_price.getText().toString().isEmpty()
                      && !editText_stop_loss_price.getText().toString().isEmpty()
                      && !editText_take_profit_price.getText().toString().isEmpty()) {
                    editOrCloseSignal(SIGNAL_OPERATION_EDIT);
                  } else {
                    showToastMessage(getResources().getString(R.string.pleaseFillRequiredFields));

                  }

                }
              }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              dialog.dismiss();
            }
          });

        } else {
          editOrCloseSignal(SIGNAL_OPERATION_NEW);

        }
      }
    });

    if (signalModelToEdit == null) {
      button_close.setVisibility(View.GONE);
      editText_closing_price.setHint(getResources().getString(R.string.no_closing_price));
      editText_closing_price.setEnabled(false);
      textView_closing_price
          .setPaintFlags(textView_closing_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      textView_title_editSignal.setText(getResources().getString(R.string.title_new_signal));

    }

    button_close.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        mMaterialDialog.title(R.string.close_signal)
            .content(R.string.text_dialog_close_signal)
            .positiveText(R.string.ok)
            .negativeText(R.string.text_dialog_view_Cancel)
            .cancelable(true);

        mMaterialDialog.show();

        mMaterialDialog
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                if (editText_closing_price.getText().toString().isEmpty()) {
                  showToastMessage(getResources().getString(R.string.pleaseFillClosingPrice));
                } else {
                  editOrCloseSignal(SIGNAL_OPERATION_CLOSE);
                }
              }
            }).onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            dialog.dismiss();
          }
        });

        signalOperation = SIGNAL_OPERATION_CLOSE;


      }
    });


  }

  private boolean checkEditTextEmpty(EditText editText) {
    return editText.getText().toString().isEmpty() || editText
        .getText().toString().replace(" ", "").isEmpty() || !isNumeric(
        editText.getText().toString());
  }

  private void editOrCloseSignal(String editOrClose) {

    if (checkEditTextEmpty(editText_opening_price) || checkEditTextEmpty(editText_stop_loss_price)
        || checkEditTextEmpty(editText_take_profit_price)) {
      showToastMessage(getResources().getString(R.string.openPriceCannotBeBlank));
    } else {

      String comment;

      String pair = spinner_pair.getSelectedItem().toString();
      boolean buySell =
          spinner_buy_sell.getSelectedItem().toString().equals("Buy");

      Double opening_price;
      Double closing_price = 0.0;
      Double take_profit;
      Double stop_loss;

      opening_price = Double
          .valueOf(editText_opening_price.getText().toString().replace(",", "."));

      if (editText_closing_price.getText().toString().isEmpty()) {
        closing_price = 0.0;
      } else if (isNumeric(editText_closing_price.getText().toString())) {
        closing_price = Double
            .valueOf(editText_closing_price.getText().toString().replace(",", "."));
      } else if (!isNumeric(editText_closing_price.getText().toString())) {
        showToastMessage(getResources().getString(R.string.closingPriceMustBeNumeric));
        return;
      }

      take_profit = Double
          .valueOf(editText_take_profit_price.getText().toString().replace(",", "."));

      stop_loss = Double
          .valueOf(editText_stop_loss_price.getText().toString().replace(",", "."));

      comment = editTextComment.getText().toString();

      NewPositionModel newPositionModel = new NewPositionModel();

      if (editOrClose.equals(SIGNAL_OPERATION_EDIT)) {
        newPositionModel.setBuy_sell(buySell);
        newPositionModel.setClosingPrice(0);
        newPositionModel.setComment(comment);
        newPositionModel.setId(signalModelToEdit.getId());
        newPositionModel.setOpen(true);
        newPositionModel.setOpeningPrice(opening_price);
        newPositionModel.setPair(pair);
        newPositionModel.setProfit(0);
        newPositionModel.setStatus("updated");
        newPositionModel.setVolume(1);
        newPositionModel.setTake_profit_price(take_profit);
        newPositionModel.setStop_loss_price(stop_loss);
        newPositionModel.setDate(ServerValue.TIMESTAMP);
        newPositionModel.setHitStopLoss(checkbox_sl.isChecked());
        newPositionModel.setHitTakeProfit(checkbox_tp.isChecked());

        showToastMessage("Edit Confirmed");


      } else if (editOrClose.equals(SIGNAL_OPERATION_NEW)) {
        newPositionModel = new NewPositionModel();
        newPositionModel.setBuy_sell(buySell);
        newPositionModel.setClosingPrice(0);
        newPositionModel.setComment(comment);
        newPositionModel.setId("");
        newPositionModel.setOpen(true);
        newPositionModel.setOpeningPrice(opening_price);
        newPositionModel.setPair(pair);
        newPositionModel.setProfit(0);
        newPositionModel.setStatus("working");
        newPositionModel.setVolume(1);
        newPositionModel.setTake_profit_price(take_profit);
        newPositionModel.setStop_loss_price(stop_loss);
        newPositionModel.setDate(ServerValue.TIMESTAMP);
        newPositionModel.setHitStopLoss(false);
        newPositionModel.setHitTakeProfit(false);

        showToastMessage(getResources().getString(R.string.add_confirmed));


      } else if (editOrClose.equals(SIGNAL_OPERATION_CLOSE)) {
        newPositionModel = new NewPositionModel();
        newPositionModel.setBuy_sell(buySell);
        newPositionModel.setClosingPrice(closing_price);
        newPositionModel.setComment(comment);
        newPositionModel.setId(signalModelToEdit.getId());
        newPositionModel.setOpen(false);
        newPositionModel.setOpeningPrice(opening_price);
        newPositionModel.setPair(pair);
        newPositionModel.setProfit(0);
        newPositionModel.setStatus("closed");
        newPositionModel.setVolume(1);
        newPositionModel.setTake_profit_price(take_profit);
        newPositionModel.setStop_loss_price(stop_loss);
        newPositionModel.setDate(ServerValue.TIMESTAMP);
        newPositionModel.setHitStopLoss(checkbox_sl.isChecked());
        newPositionModel.setHitTakeProfit(checkbox_tp.isChecked());

        showToastMessage("Closed");

      }

      newSignalPresenter.createNewSignaltoDatabase(newPositionModel, signalOperation);

      getActivity().finish();
    }
  }

  @Override
  public void onDestroy() {
    unbinder.unbind();
    super.onDestroy();
  }

  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
  }
}