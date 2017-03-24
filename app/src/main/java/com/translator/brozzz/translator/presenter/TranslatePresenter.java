package com.translator.brozzz.translator.presenter;

import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;
import com.translator.brozzz.translator.model.TranslateModel;
import com.translator.brozzz.translator.network.Yandex;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TranslatePresenter {

    private YandexTranslateApi mTranslaterApi = Yandex.TranslateApi.getTranslateApi();
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
                            getTranslationLang())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(translation -> mView.setTranslatedText(translation.getTranslation().toString()));
        }
    }

    public void dispose(){
        if (mDisposableTranslater != null && !mDisposableTranslater.isDisposed())
            mDisposableTranslater.dispose();
    }

    private String getTranslationLang(){
        return "en-ru";
        //TODO использовать когда будут языки
//        return mModel.getTranslateFrom() + "-" +
//                mModel.getTranslateTo();
    }

}
