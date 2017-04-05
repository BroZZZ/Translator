package com.translator.brozzz.translator.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.translator.brozzz.translator.adapters.DictionaryRvAdapter;
import com.translator.brozzz.translator.entity.DictionaryTest;
import com.translator.brozzz.translator.entity.dictionary.Dictionary;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;
import com.translator.brozzz.translator.model.TranslateModel;
import com.translator.brozzz.translator.network.Yandex;
import com.translator.brozzz.translator.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TranslatePresenter {

    private YandexTranslateApi mTranslaterApi = Yandex.TranslateApi.getTranslateApi();
    private YandexDictionaryApi mDictionaryApi = Yandex.DictionaryApi.getDictionaryApi();
    private ITranslateFragment mView;
    private TranslateModel mModel;
    private DictionaryRvAdapter mRvDictionaryAdapter;

    SharedPreferences mSettings;

    private Disposable mDisposableTranslater;
    private Disposable mDisposableDictionary;

    public TranslatePresenter(Context context, ITranslateFragment mView) {
        this.mView = mView;
        initModel(context);
        mRvDictionaryAdapter = new DictionaryRvAdapter(null);
    }

    private void initModel(Context context) {
        mSettings = context.getSharedPreferences(Utils.SharedPreferences.TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        String translateFromSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_FROM_PREFERENCE, "");
        String translateToSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_TO_PREFERENCE, "");
        mModel = new TranslateModel(translateFromSetting, translateToSetting);
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

            if (!text.trim().contains(" "))
                mDisposableDictionary = mDictionaryApi
                        .getDictionary(Yandex.DictionaryApi.DICTIONARY_API_KEY,
                                text,
                                getTranslationFormat())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processDictionary);

        }
    }

    private void processDictionary(DictionaryTest dictionary) {
        mRvDictionaryAdapter.setDictionary(dictionary);
    }

    public DictionaryRvAdapter getmRvDictionaryAdapter() {
        return mRvDictionaryAdapter;
    }

    public void dismiss() {
        dispose();
        storeSetting();
    }

    private void dispose() {
        if (mDisposableTranslater != null && !mDisposableTranslater.isDisposed())
            mDisposableTranslater.dispose();
        if (mDisposableDictionary != null && !mDisposableDictionary.isDisposed())
            mDisposableDictionary.dispose();
    }

    private void storeSetting() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(Utils.SharedPreferences.TRANSLATE_FROM_PREFERENCE, mModel.getTranslateFrom().toString());
        editor.putString(Utils.SharedPreferences.TRANSLATE_TO_PREFERENCE, mModel.getTranslateTo().toString());
        editor.apply();
    }

    private String getTranslationFormat() {
        return mModel.getTranslateFrom().getCode() + "-" +
                mModel.getTranslateTo().getCode();
    }

    public void switchLang() {
        mModel.switchLang();
        mView.updateActionBar();
    }

    public String getTranslateFromName(){
        return mModel.getTranslateFrom().getName();
    }

    public String getTranslateToName(){
        return mModel.getTranslateTo().getName();
    }

}
