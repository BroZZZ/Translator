package com.translator.brozzz.translator.presenter;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.adapters.DictionaryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.fragments.TranslateFragment;
import com.translator.brozzz.translator.interfaces.ITranslateFragment;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;
import com.translator.brozzz.translator.model.SettingsModel;
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
    private BroadcastReceiver mSettingReceiver;

    public TranslatePresenter(Context context, ITranslateFragment fragmentView) {
        mView = fragmentView;
        mRealm = Realm.getDefaultInstance();
        mContext = context;
        initModel(context);
        mRvDictionaryAdapter = new DictionaryRvAdapter();
    }

    /**
     * get last translation languages from shared preferences
     *
     * @param context
     */
    private void initModel(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(Utils.SharedPreferences.TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        String translateFromSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_FROM_PREFERENCE, "");
        String translateToSetting = mSettings.getString(Utils.SharedPreferences.TRANSLATE_TO_PREFERENCE, "");
        mModel = new TranslateModel(translateFromSetting, translateToSetting);
    }

    /**
     * translate text from cache or yandex api
     *
     * @param text
     */
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

    /**
     * search text translation in realm db
     *
     * @param text original text
     * @return translation info
     */
    private TranslationInfo findTranslation(String text) {
        return mRealm.where(TranslationInfo.class).equalTo("originalText", text).findFirst();
    }

    /**
     * process translation and display on view
     *
     * @param translationInfo processing object
     */
    private void processTranslation(TranslationInfo translationInfo) {
        storeTranslation(translationInfo);
        mView.displayTranslateResult(translationInfo.getOriginalText(), translationInfo.getTranslation().getTranslatedText());
        mRvDictionaryAdapter.setDictionary(translationInfo.getDictionary());
    }

    /**
     * Vocalize text
     *
     * @param text       vocalization text
     * @param textTypeId TranslateFragment const
     */
    public void vocalize(String text, int textTypeId) {
        if (textTypeId == TranslateFragment.ORIGINAL_TEXT)
            mSpeechkitHelper.Vocalize(text, mModel.getTranslateFrom(), textTypeId, mModel.getSettings().getVocalizeVoice());
        else {
            mSpeechkitHelper.Vocalize(text, mModel.getTranslateTo(), textTypeId, mModel.getSettings().getVocalizeVoice());
        }
    }

    /**
     * Store translation info in db
     *
     * @param translationInfo Saved object
     */
    private void storeTranslation(TranslationInfo translationInfo) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(translationInfo);
        mRealm.commitTransaction();
    }

    public DictionaryRvAdapter getRvDictionaryAdapter() {
        return mRvDictionaryAdapter;
    }

    /**
     * Freeing memory, store model state
     */
    public void dismiss() {
        dispose();
        storeSetting();
        mSpeechkitHelper.dismiss();
    }

    /**
     * Initialization of required fields
     */
    public void init() {
        if (mRealm.isClosed()) mRealm = Realm.getDefaultInstance();
        mSpeechkitHelper = new SpeechkitHelper(mView);
        registerReceiver();
        updateSettings();
    }

    /**
     * Update setting from db
     */
    private void updateSettings() {
        mModel.setSettings(mRealm.where(SettingsModel.class).findFirst());
    }

    /**
     * Rx dispose
     */
    private void dispose() {
        if (mDisposableTranslater != null && !mDisposableTranslater.isDisposed())
            mDisposableTranslater.dispose();
        unregisterReceiver();
    }

    private void registerReceiver() {
        if (mSettingReceiver == null) {
            mSettingReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getAction()) {
                        case Utils.Broadcast.ACTION_DELAY_CHANGED:
                            mView.onDelayChanged();
                            break;
                        case Utils.Broadcast.ACTION_TRANSLATE_ON_FLY_CHANGED:
                            mView.onTranslateOnFlyChanged();
                            break;
                        default:break;
                    }
                }
            };
        }
        IntentFilter filter = new IntentFilter(Utils.Broadcast.ACTION_DELAY_CHANGED);
        filter.addAction(Utils.Broadcast.ACTION_TRANSLATE_ON_FLY_CHANGED);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mSettingReceiver, filter);
    }

    /**
     * Unregister setting broadcast receiver
     */
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mSettingReceiver);
    }

    /**
     * Store model state
     */
    private void storeSetting() {
        SharedPreferences mSettings = mContext.getSharedPreferences(Utils.SharedPreferences.TRANSLATE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(Utils.SharedPreferences.TRANSLATE_FROM_PREFERENCE, mModel.getTranslateFrom().toString());
        editor.putString(Utils.SharedPreferences.TRANSLATE_TO_PREFERENCE, mModel.getTranslateTo().toString());
        editor.apply();
    }

    /**
     * Set language format for yandex format
     *
     * @return yandex api laguage param
     */
    private String getTranslationFormat() {
        return mModel.getTranslateFrom().getCode() + "-" +
                mModel.getTranslateTo().getCode();
    }

    /**
     * Switch original and tranlate language
     */
    public void switchLang() {
        mModel.switchLang();
        mView.updateActionBar();
    }

    /**
     * Get original language
     *
     * @return string representation name
     */
    public String getTranslateFromName() {
        return mModel.getTranslateFrom().getName();
    }

    /**
     * Get translate language
     *
     * @return string representation name
     */
    public String getTranslateToName() {
        return mModel.getTranslateTo().getName();
    }

    /**
     * Start voice recognize with original language
     */
    public void startRecognizeInput() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext,
                    R.string.audio_record_error,
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        mSpeechkitHelper.startRecognize(mModel.getTranslateFrom().getCode());
    }

    /**
     * return delay before auto-translate
     *
     * @return delay before auto-translate
     */
    public int getDelayBeforeTranslate() {
        return mModel.getSettings().getDelayBeforeTranslate();
    }
}
