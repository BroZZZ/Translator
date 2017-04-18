package com.translator.brozzz.translator.presenter;

import com.translator.brozzz.translator.interfaces.ISettingFragment;
import com.translator.brozzz.translator.model.SettingsModel;

import io.realm.Realm;


public class SettingPresenter {
    private SettingsModel mModel;
    private ISettingFragment mView;
    private Realm mRealm;

    public SettingPresenter(ISettingFragment view) {
        mView = view;
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
     * Update delay before translate value in bd
     * @param delaySetting new value
     */
    public void updateDelaySetting(int delaySetting) {
        mRealm.beginTransaction();
        mModel.setDelayBeforeTranslate(delaySetting);
        mRealm.commitTransaction();
    }

    /**
     * Update translate on fly value in bd
     * @param b new value
     */
    public void updateTranslateOnFlySetting(boolean b) {
        mRealm.beginTransaction();
        mModel.setTransleOnFlyOn(b);
        mRealm.commitTransaction();
    }

    /**
     * Update vocalization voice value in bd
     * @param voice new value
     */
    public void updateVoiceSetting(String voice) {
        mRealm.beginTransaction();
        mModel.setVocalizeVoice(voice);
        mRealm.commitTransaction();
    }

    /**
     * Freeing memory
     */
    public void dismiss() {
        mRealm.close();
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
