package com.translator.brozzz.translator.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.translator.brozzz.translator.interfaces.ISettingFragment;
import com.translator.brozzz.translator.model.SettingsModel;
import com.translator.brozzz.translator.utils.Utils;

import io.realm.Realm;


public class SettingPresenter {
    private SettingsModel mModel;
    private ISettingFragment mView;
    private Context mContext;
    private Realm mRealm;

    public SettingPresenter(ISettingFragment view, Context context) {
        mView = view;
        mContext = context;
        mRealm = Realm.getDefaultInstance();
        mModel = mRealm.where(SettingsModel.class).findFirst();
    }

    /**
     * Display updated setting
     */
    private void updateSettings() {
        mView.updateSettingView(mModel.isTransleOnFlyOn(),
                mModel.getDelayBeforeTranslate(),
                mModel.getVocalizeVoice());
    }

    /**
     * Update delay value in bd
     *
     * @param delaySetting new value
     */
    public void updateDelaySetting(int delaySetting) {
        mRealm.beginTransaction();
        mModel.setDelayBeforeTranslate(delaySetting);
        mRealm.commitTransaction();
        //send local broadcast for translate presenter, recreate rx listener for origin text
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Utils.Broadcast.ACTION_DELAY_CHANGED));
    }

    /**
     * Update translate on fly value in bd
     *
     * @param b new value
     */
    public void updateTranslateOnFlySetting(boolean b) {
        mRealm.beginTransaction();
        mModel.setTransleOnFlyOn(b);
        mRealm.commitTransaction();
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Utils.Broadcast.ACTION_TRANSLATE_ON_FLY_CHANGED));
    }

    /**
     * Update vocalization voice value in bd
     *
     * @param voice new value
     */
    public void updateVoiceSetting(String voice) {
        mRealm.beginTransaction();
        mModel.setVocalizeVoice(voice);
        mRealm.commitTransaction();
    }

    /**
     * Initialization of required fields
     */
    public void init() {
        if (mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
            mModel = mRealm.where(SettingsModel.class).findFirst();
        }
        updateSettings();
    }

}
