package com.translator.brozzz.translator.presenter;

import android.util.Log;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;
import com.translator.brozzz.translator.model.TranslateModel;
import com.translator.brozzz.translator.network.Yandex;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TranslatePresenter {

    private YandexTranslateApi mTranslaterApi = Yandex.TranslateApi.getTranslateApi();
    private YandexDictionaryApi mDictionaryApi = Yandex.DictionaryApi.getDictionaryApi();
    private ITranslateFragment mView;
    private TranslateModel mModel;

    Disposable mDisposableTranslater;

    public TranslatePresenter(ITranslateFragment mView) {
        this.mView = mView;
        mModel = new TranslateModel();
    }

    public void translate(String text) {
        if (text.length() == 0) {
            mView.setTranslatedText(text);
        } else {
            mDisposableTranslater = mTranslaterApi
                    .getTranslation(Yandex.TranslateApi.TRANSLATOR_API_KEY,
                            text,
                            getTranslationFormat())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(translation -> mView.setTranslatedText(translation.getTranslation().toString()));

            mDisposableTranslater = mDictionaryApi
                    .getDictionary(Yandex.DictionaryApi.DICTIONARY_API_KEY,
                            text,
                            getTranslationFormat())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::processDictionary, this::processDictionary);
        }
    }

    private void processDictionary(Dictionary dictionary) {
        Log.d("DICTIONARY TEST", dictionary.getSynonyms(0,0));
    }

    private void processDictionary(Throwable ex) {
        Log.d("DICTIONARY TEST", "");
    }

    public void dispose() {
        if (mDisposableTranslater != null && !mDisposableTranslater.isDisposed())
            mDisposableTranslater.dispose();
    }

    private String getTranslationFormat() {
        return mModel.getTranslateFrom().getCode() + "-" +
                mModel.getTranslateTo().getCode();
    }

    public void switchLang() {
        mModel.switchLang();
        mView.updateActionBar(mModel.getTranslateFrom().getName(),
                mModel.getTranslateTo().getName());
    }

}
