package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.activities.MainActivity;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.presenter.TranslatePresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TranslateFragment extends Fragment implements ITranslateFragment {

    @BindView(R.id.translate_text)
    EditText mTranslateText;

    @BindView(R.id.translated_text)
    TextView mTranslatedText;

    @BindView(R.id.original_text)
    TextView mOriginalText;

    @BindView(R.id.dictionary_rv)
    RecyclerView dictionaryRv;

    TextView tvTranslateFrom;
    TextView tvTranslateTo;
    ImageButton btnSwitchLang;

    private Disposable mDisposableChangeText;
    private TranslatePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TranslatePresenter(getContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translate_fragment, container, false);
        ButterKnife.bind(this, view);
        dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initRv();
        initSupportActionBarView();
        setListeners();
        return view;
    }

    private void initRv() {
        dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initSupportActionBarView() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.translate_action_bar);
            tvTranslateFrom = (TextView) actionBar.getCustomView()
                    .findViewById(R.id.translate_from);
            tvTranslateFrom.setText("Английский");
            tvTranslateTo = (TextView) actionBar.getCustomView()
                    .findViewById(R.id.translate_to);
            btnSwitchLang = (ImageButton) actionBar.getCustomView()
                    .findViewById(R.id.btn_switch_lang);

            updateActionBar();
        }
    }

    private void setListeners() {
        btnSwitchLang.setOnClickListener(view -> mPresenter.switchLang());
    }

    @Override
    public void onResume() {
        super.onResume();
        mDisposableChangeText = RxTextView
                .textChanges(mTranslateText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(text -> text.toString().trim())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(s ->
                {
                    if (s.isEmpty()){
                        clearText();
                        mPresenter.getRvDictionaryAdapter().clear();
                    }
                    return !s.isEmpty();
                })
                .subscribe(mPresenter::translate);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.dismiss();
    }

    @Override
    public void displayTranslateResult(String originalText, String translatedText) {
        mOriginalText.setText(originalText);
        mTranslatedText.setText(translatedText);
    }

    private void clearText() {
        mOriginalText.setText("");
        mTranslatedText.setText("");
    }

    public void updateActionBar() {
        tvTranslateFrom.setText(mPresenter.getTranslateFromName());
        tvTranslateTo.setText(mPresenter.getTranslateToName());
    }
}
