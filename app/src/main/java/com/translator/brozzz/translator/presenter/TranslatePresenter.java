package com.translator.brozzz.translator.presenter;

import android.util.Log;

import com.translator.brozzz.translator.adapters.DictionaryRvAdapter;
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
    private DictionaryRvAdapter mRvDictionaryAdapter;

    Disposable mDisposableTranslater;

    public TranslatePresenter(ITranslateFragment mView) {
        this.mView = mView;
        mModel = new TranslateModel();
        mRvDictionaryAdapter = new DictionaryRvAdapter(null);
    }

    public void translate(String text) {
        if (text.length() == 0) {
            mView.displayTranslateResult(text, text);
        } else {
            mDisposableTranslater = mTranslaterApi
                    .getTranslation(Yandex.TranslateApi.TRANSLATOR_API_KEY,
                            text,
                            getTranslationFormat())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(translation -> mView.displayTranslateResult(text, translation.getTranslationString()));

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
        mRvDictionaryAdapter.setDictionary(dictionary);
    }

    public DictionaryRvAdapter getmRvDictionaryAdapter() {
        return mRvDictionaryAdapter;
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
