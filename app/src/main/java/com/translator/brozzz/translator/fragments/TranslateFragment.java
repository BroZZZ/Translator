package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @BindView(R.id.translate_from)
    TextView tvTranslateFrom;

    @BindView(R.id.translate_to)
    TextView tvTranslateTo;

    @BindView(R.id.btn_switch_lang)
    ImageButton btnSwitchLang;

    @BindView(R.id.ib_vocalize)
    ImageButton ibVocalizeOrigin;

    @BindView(R.id.ib_vocalize_translated)
    ImageButton ibVocalizeTranslated;

    @BindView(R.id.ib_favorite)
    ImageButton ibFavorite;

    @BindView(R.id.ib_recognize)
    ImageButton ibRecognize;

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
        ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.action_bar_container);
        inflater.inflate(R.layout.translate_action_bar, insertPoint, true);
        ButterKnife.bind(this, view);
        dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initRv();
        setListeners();
        updateActionBar();
        return view;
    }

    private void initRv() {
        dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setListeners() {
        btnSwitchLang.setOnClickListener(view -> mPresenter.switchLang());
        ibVocalizeOrigin.setOnClickListener(view -> mPresenter.VocalizeWithOriginalLanguage(mTranslateText.getText().toString()));
        ibVocalizeTranslated.setOnClickListener(view -> mPresenter.VocalizeWithResultLanguage(mTranslatedText.getText().toString()));
        ibRecognize.setOnClickListener(view -> mPresenter.startRecognizeInput());
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
                    if (s.isEmpty()) {
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

    @Override
    public void setFinalRecognizedText(String text) {
        int selectionStart = mTranslateText.getSelectionStart();
        int selectionEnd = mTranslateText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(mTranslateText.getText().toString());
        textBuilder.delete(selectionStart, selectionEnd);
        textBuilder.insert(selectionStart, text);
        selectionEnd = selectionStart + text.length();

        mTranslateText.setText(textBuilder.toString());
        mTranslateText.setSelection(selectionEnd);
    }

    @Override
    public void setPartialRecognizedText(String text) {
        int selectionStart = mTranslateText.getSelectionStart();
        int selectionEnd = mTranslateText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(mTranslateText.getText().toString());
        if (selectionStart == selectionEnd) {
            textBuilder.insert(selectionStart, text);
            selectionEnd = mTranslateText.length() + text.length();
        } else {
            textBuilder.delete(selectionStart, selectionEnd);
            textBuilder.insert(selectionStart, text);
            selectionEnd = selectionStart + text.length();
        }

        mTranslateText.setText(textBuilder.toString());
        mTranslateText.setSelection(selectionStart, selectionEnd);
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
