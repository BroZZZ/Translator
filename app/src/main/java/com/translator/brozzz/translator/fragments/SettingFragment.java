package com.translator.brozzz.translator.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.interfaces.ISettingFragment;
import com.translator.brozzz.translator.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingFragment extends Fragment implements ISettingFragment {

    @BindView(R.id.et_millisecond)
    EditText mEtMillisecond;
    @BindView(R.id.sc_trastale_on_fly)
    SwitchCompat mSwitchTranslateOnFly;
    @BindView(R.id.tv_delay_before_translate)
    TextView mTvDelayText;

    private SettingPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SettingPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.action_bar_container);
        inflater.inflate(R.layout.setting_action_bar, insertPoint, true);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setListeners() {
        mEtMillisecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int delay = 0;
                if (!editable.toString().isEmpty()) delay = Integer.parseInt(editable.toString());
                mPresenter.updateDelaySetting(delay);
            }
        });
        mSwitchTranslateOnFly.setOnCheckedChangeListener((compoundButton, b) ->
        {
            mPresenter.updateTranslateOnFlySetting(b);
            mEtMillisecond.clearFocus();
            if (b) {
                changeEditTextStyle(InputType.TYPE_CLASS_NUMBER, R.color.colorBlack);
            } else {
                hideKeyboard();
                changeEditTextStyle(InputType.TYPE_NULL, R.color.colorUnselectedTab);
            }

        });

    }

    @Override
    public void updateSettingView(boolean isTranslateOnFlyOn, int delay, String Voice) {
        mSwitchTranslateOnFly.setChecked(isTranslateOnFlyOn);
        mEtMillisecond.setText(String.valueOf(delay));

        if (isTranslateOnFlyOn) {
            changeEditTextStyle(InputType.TYPE_CLASS_NUMBER, R.color.colorBlack);
        } else {
            hideKeyboard();
            changeEditTextStyle(InputType.TYPE_NULL, R.color.colorUnselectedTab);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
        setListeners();
    }

    private void changeEditTextStyle(int inputType, @ColorRes int colorId) {
        mEtMillisecond.setInputType(inputType);
        mEtMillisecond.setTextColor(ResourcesCompat.getColor(getResources(),
                colorId, getContext().getTheme()));
        mTvDelayText.setTextColor(ResourcesCompat.getColor(getResources(),
                colorId, getContext().getTheme()));
        setUnderlineDisableStyle(inputType == InputType.TYPE_NULL);
        mEtMillisecond.invalidate();
    }

    private void setUnderlineDisableStyle(boolean disable) {
        if (disable) {
            mEtMillisecond.getBackground().setColorFilter(ResourcesCompat.getColor(getResources(),
                    R.color.colorUnselectedTab, getContext().getTheme()), PorterDuff.Mode.SRC_IN);
        } else {
            mEtMillisecond.getBackground().clearColorFilter();
        }

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtMillisecond.getWindowToken(), 0);
    }
}
