package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import static com.translator.brozzz.translator.R.id.ib_favorite;

public class TranslateFragment extends Fragment implements ITranslateFragment {

    public static final int ORIGINAL_TEXT = 0;
    public static final int TRANSLATED_TEXT = 1;

    @BindView(R.id.translate_text)
    EditText etOriginalText;

    @BindView(R.id.translated_text)
    TextView tvTranslatedText;

    @BindView(R.id.original_text)
    TextView tvOriginalText;

    @BindView(R.id.dictionary_rv)
    RecyclerView dictionaryRv;

    @BindView(R.id.translate_from)
    TextView tvTranslateFrom;

    @BindView(R.id.translate_to)
    TextView tvTranslateTo;

    @BindView(R.id.btn_switch_lang)
    ImageButton ibSwitchLang;

    @BindView(R.id.ib_vocalize)
    ImageButton ibVocalizeOrigin;

    @BindView(R.id.ib_vocalize_translated)
    ImageButton ibVocalizeTranslated;

    @BindView(ib_favorite)
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
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
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


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
        mDisposableChangeText = RxTextView
                .textChanges(etOriginalText)
                .debounce(mPresenter.getDelayBeforeTranslate(), TimeUnit.MILLISECONDS)
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
        if (!mDisposableChangeText.isDisposed())
            mDisposableChangeText.dispose();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.dismiss();
    }

    @Override
    public void displayTranslateResult(String originalText, String translatedText) {
        tvOriginalText.setText(originalText);
        tvTranslatedText.setText(translatedText);
    }

    @Override
    public void setFinalRecognizedText(String text) {
        //edit text cursor and selection game
        int selectionStart = etOriginalText.getSelectionStart();
        int selectionEnd = etOriginalText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(etOriginalText.getText().toString());
        textBuilder.delete(selectionStart, selectionEnd);
        textBuilder.insert(selectionStart, text);
        selectionEnd = selectionStart + text.length();

        etOriginalText.setText(textBuilder.toString());
        etOriginalText.setSelection(selectionEnd);
    }

    @Override
    public void setPartialRecognizedText(String text) {
        //edit text cursor and selection game
        int selectionStart = etOriginalText.getSelectionStart();
        int selectionEnd = etOriginalText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(etOriginalText.getText().toString());
        if (selectionStart == selectionEnd) {
            textBuilder.insert(selectionStart, text);
            selectionEnd = etOriginalText.length() + text.length();
        } else {
            textBuilder.delete(selectionStart, selectionEnd);
            textBuilder.insert(selectionStart, text);
            selectionEnd = selectionStart + text.length();
        }

        etOriginalText.setText(textBuilder.toString());
        etOriginalText.setSelection(selectionStart, selectionEnd);
    }

    @Override
    public void onRecognizeStart() {
        //change icon color when recognize
        ibRecognize.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void onRecognizeDone() {
        //change icon color when recognize done
        ibRecognize.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBlack));
    }

    @Override
    public void onVocalizationStart(int textTypeId) {
        //change icon color when vocalization start
        if (textTypeId == ORIGINAL_TEXT)
            ibVocalizeOrigin.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        else
            ibVocalizeTranslated.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void onVocalizationEnd(int textTypeId) {
        //change icon color when vocalization done
        if (textTypeId == ORIGINAL_TEXT)
            ibVocalizeOrigin.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBlack));
        else
            ibVocalizeTranslated.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBlack));
    }

    private void initRv() {
        dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setListeners() {
        ibSwitchLang.setOnClickListener(view -> mPresenter.switchLang());
        ibVocalizeOrigin.setOnClickListener(view -> mPresenter.vocalize(etOriginalText.getText().toString(), ORIGINAL_TEXT));
        ibVocalizeTranslated.setOnClickListener(view -> mPresenter.vocalize(tvTranslatedText.getText().toString(), TRANSLATED_TEXT));
        ibRecognize.setOnClickListener(view -> mPresenter.startRecognizeInput());
    }

    /**
     * Clear text view for original and translated text
     */
    private void clearText() {
        tvOriginalText.setText("");
        tvTranslatedText.setText("");
    }

    public void updateActionBar() {
        tvTranslateFrom.setText(mPresenter.getTranslateFromName());
        tvTranslateTo.setText(mPresenter.getTranslateToName());
    }
}
