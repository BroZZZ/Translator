package com.translator.brozzz.translator.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.interfaces.ISettingFragment;
import com.translator.brozzz.translator.presenter.SettingPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SettingFragment extends Fragment implements ISettingFragment {

    @BindView(R.id.et_millisecond)
    EditText mEtMillisecond;

    @BindView(R.id.sc_trastale_on_fly)
    SwitchCompat mSwitchTranslateOnFly;

    @BindView(R.id.tv_delay_before_translate)
    TextView mTvDelayText;

    @BindView(R.id.spinner_voice)
    Spinner mSpinnerVoice;

    private SettingPresenter mPresenter;
    private Disposable mDisposableDelayChanged;
    private static final int DELAY_CHANGE_DEBOUNCE = 1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SettingPresenter(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.action_bar_container);
        inflater.inflate(R.layout.setting_action_bar, insertPoint, true);
        ButterKnife.bind(this, view);
        initSpinner();
        return view;
    }

    /**
     * Init spinner adapter with string array res
     */
    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.vocalize_voices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerVoice.setAdapter(adapter);
    }

    private void setListeners() {
        initDelayChangeListener();
        mSwitchTranslateOnFly.setOnCheckedChangeListener((compoundButton, b) ->
        {
            mPresenter.updateTranslateOnFlySetting(b);
            mEtMillisecond.clearFocus();
            if (b) {
                changeEditTextStyle(InputType.TYPE_CLASS_NUMBER, R.color.colorBlack);
            } else {
                //if edit text in focus, keyboard visible, we need to hide it
                hideKeyboard();
                changeEditTextStyle(InputType.TYPE_NULL, R.color.colorUnselectedTab);
            }

        });

        mSpinnerVoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.updateVoiceSetting(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void initDelayChangeListener() {
        if (mDisposableDelayChanged != null && !mDisposableDelayChanged.isDisposed())
            mDisposableDelayChanged.dispose();

        mDisposableDelayChanged = RxTextView
                .textChanges(mEtMillisecond)
                .debounce(DELAY_CHANGE_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s ->
                {
                    int delay = 0;
                    if (!s.isEmpty()) delay = Integer.parseInt(s);
                    mPresenter.updateDelaySetting(delay);
                });
    }

    @Override
    public void updateSettingView(boolean isTranslateOnFlyOn, int delay, String Voice) {
        mSwitchTranslateOnFly.setChecked(isTranslateOnFlyOn);
        mEtMillisecond.setText(String.valueOf(delay));
        mSpinnerVoice.setSelection(((ArrayAdapter) mSpinnerVoice.getAdapter()).getPosition(Voice));
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
        if (!mDisposableDelayChanged.isDisposed()) mDisposableDelayChanged.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
        setListeners();
    }

    /**
     * Changed color and edit text available
     *
     * @param inputType EditText input type (InputType const)
     * @param colorId   Color res id, for text view
     */
    private void changeEditTextStyle(int inputType, @ColorRes int colorId) {
        mEtMillisecond.setInputType(inputType);
        mEtMillisecond.setTextColor(ResourcesCompat.getColor(getResources(),
                colorId, getContext().getTheme()));
        mTvDelayText.setTextColor(ResourcesCompat.getColor(getResources(),
                colorId, getContext().getTheme()));
        setUnderlineDisableStyle(inputType == InputType.TYPE_NULL);
        mEtMillisecond.invalidate();
    }

    /**
     * Change edit text underline color
     *
     * @param disable if true set gray color
     */
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
