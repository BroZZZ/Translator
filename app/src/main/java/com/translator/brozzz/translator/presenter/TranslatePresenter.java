package com.translator.brozzz.translator.presenter;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.adapters.DictionaryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.fragments.TranslateFragment;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;
import com.translator.brozzz.translator.model.TranslateModel;
import com.translator.brozzz.translator.network.Yandex;
import com.translator.brozzz.translator.utils.SpeechkitHelper;
import com.translator.brozzz.translator.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class TranslatePresenter {

    private YandexTranslateApi mTranslaterApi = Yandex.TranslateApi.getTranslateApi();
    private YandexDictionaryApi mDictionaryApi = Yandex.DictionaryApi.getDictionaryApi();
    private ITranslateFragment mView;
    private TranslateModel mModel;
    private DictionaryRvAdapter mRvDictionaryAdapter;
    private Context mContext;
    private Realm mRealm;
    private SpeechkitHelper mSpeechkitHelper;

    private Disposable mDisposableTranslater;

    public TranslatePresenter(Context context, ITranslateFragment fragmentView) {
        mView = fragmentView;
        mSpeechkitHelper = new SpeechkitHelper(mView);
        mRealm = Realm.getDefaultInstance();
        mContext = context;
        initModel(context);
        mRvDictionaryAdapter = new DictionaryRvAdapter();
    }

    private void initModel(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(Utils.SharedPreferences.TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        String translateFromSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_FROM_PREFERENCE, "");
        String translateToSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_TO_PREFERENCE, "");
        mModel = new TranslateModel(translateFromSetting, translateToSetting);
    }

    public void translate(String text) {
        mRvDictionaryAdapter.clear();

        TranslationInfo cacheTranslation = findTranslation(text);
        if (cacheTranslation == null)
            mDisposableTranslater = Observable.zip(mTranslaterApi
                            .getTranslation(Yandex.TranslateApi.TRANSLATOR_API_KEY,
                                    text,
                                    getTranslationFormat())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    mDictionaryApi
                            .getDictionary(Yandex.DictionaryApi.DICTIONARY_API_KEY,
                                    text,
                                    getTranslationFormat())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    (translation, dictionary) -> new TranslationInfo(text, translation, dictionary)
            ).subscribe(
                    this::processTranslation,
                    throwable -> Toast.makeText(mContext, R.string.translate_error, Toast.LENGTH_SHORT).show());
        else processTranslation(cacheTranslation);
    }

    private TranslationInfo findTranslation(String text) {
        return mRealm.where(TranslationInfo.class).equalTo("originalText", text).findFirst();
    }

    private void processTranslation(TranslationInfo translationInfo) {
        storeTranslation(translationInfo);
        mView.displayTranslateResult(translationInfo.getOriginalText(), translationInfo.getTranslation().getTranslatedText());
        mRvDictionaryAdapter.setDictionary(translationInfo.getDictionary());
    }

    public void vocalize(String text, int textTypeId) {
        if (textTypeId == TranslateFragment.ORIGINAL_TEXT)
            mSpeechkitHelper.Vocalize(text, mModel.getTranslateFrom(), textTypeId);
        else {
            mSpeechkitHelper.Vocalize(text, mModel.getTranslateTo(), textTypeId);
        }
    }

    private void storeTranslation(TranslationInfo translationInfo) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(translationInfo);
        mRealm.commitTransaction();
    }

    public DictionaryRvAdapter getRvDictionaryAdapter() {
        return mRvDictionaryAdapter;
    }

    public void dismiss() {
        dispose();
        storeSetting();
        mSpeechkitHelper.dismiss();
    }

    private void dispose() {
        if (mDisposableTranslater != null && !mDisposableTranslater.isDisposed())
            mDisposableTranslater.dispose();
    }

    private void storeSetting() {
        SharedPreferences mSettings = mContext.getSharedPreferences(Utils.SharedPreferences.TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
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

    public String getTranslateFromName() {
        return mModel.getTranslateFrom().getName();
    }

    public String getTranslateToName() {
        return mModel.getTranslateTo().getName();
    }

    public void startRecognizeInput() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mSpeechkitHelper.startRecognize(mModel.getTranslateFrom().getCode());
    }
}
