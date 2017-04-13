package com.translator.brozzz.translator.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.databinding.TranslateActionBarBinding;
import com.translator.brozzz.translator.databinding.TranslateFragmentBinding;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.presenter.TranslatePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TranslateFragment extends Fragment implements ITranslateFragment {
    TranslateFragmentBinding mBinding;
    TranslateActionBarBinding mActionBarBinding;
    private TranslatePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TranslatePresenter(getContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.translate_fragment, container, false);
        mBinding.setTranslatePresenter(mPresenter);
        mActionBarBinding = TranslateActionBarBinding.inflate(inflater, mBinding.actionBarContainer, true);
        initRv();
        updateActionBar();
        return mBinding.getRoot();
    }

    private void initRv() {
        mBinding.dictionaryRv.setAdapter(mPresenter.getRvDictionaryAdapter());
        mBinding.dictionaryRv.setItemAnimator(new DefaultItemAnimator());
        mBinding.dictionaryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        RxTextView
                .textChanges(mBinding.translateText)
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
        mPresenter.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.dismiss();
    }

    @Override
    public void displayTranslateResult(String originalText, String translatedText) {
        mBinding.originalText.setText(originalText);
        mBinding.translatedText.setText(translatedText);
    }

    @Override
    public void setFinalRecognizedText(String text) {
        int selectionStart = mBinding.translateText.getSelectionStart();
        int selectionEnd = mBinding.translateText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(mBinding.translateText.getText().toString());
        textBuilder.delete(selectionStart, selectionEnd);
        textBuilder.insert(selectionStart, text);
        selectionEnd = selectionStart + text.length();

        mBinding.translateText.setText(textBuilder.toString());
        mBinding.translateText.setSelection(selectionEnd);
    }

    @Override
    public void setPartialRecognizedText(String text) {
        int selectionStart = mBinding.translateText.getSelectionStart();
        int selectionEnd = mBinding.translateText.getSelectionEnd();
        StringBuilder textBuilder = new StringBuilder(mBinding.translateText.getText().toString());
        if (selectionStart == selectionEnd) {
            textBuilder.insert(selectionStart, text);
            selectionEnd = mBinding.translateText.length() + text.length();
        } else {
            textBuilder.delete(selectionStart, selectionEnd);
            textBuilder.insert(selectionStart, text);
            selectionEnd = selectionStart + text.length();
        }

        mBinding.translateText.setText(textBuilder.toString());
        mBinding.translateText.setSelection(selectionStart, selectionEnd);
    }

    private void clearText() {
        mBinding.originalText.setText("");
        mBinding.translatedText.setText("");
    }

    public void updateActionBar() {
        mActionBarBinding.translateFrom.setText(mPresenter.getTranslateFromName());
        mActionBarBinding.translateTo.setText(mPresenter.getTranslateToName());
    }
}
