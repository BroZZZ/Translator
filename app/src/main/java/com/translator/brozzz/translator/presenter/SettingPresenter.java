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

    private void updateSettings() {
        mView.updateSettingView(mModel.isTransleOnFlyOn(),
                mModel.getDelayBeforeTranslate(),
                mModel.getVocalizeVoice());
    }

    public void updateDelaySetting(int delaySetting) {
        mRealm.beginTransaction();
        mModel.setDelayBeforeTranslate(delaySetting);
        mRealm.commitTransaction();
    }

    public void updateTranslateOnFlySetting(boolean b) {
        mRealm.beginTransaction();
        mModel.setTransleOnFlyOn(b);
        mRealm.commitTransaction();
    }

    public void dismiss() {
        mRealm.close();
    }
    public void init() {
        if (mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
            mModel = mRealm.where(SettingsModel.class).findFirst();
        }
        updateSettings();
    }

    public void updateVoiceSetting(String voice) {
        mRealm.beginTransaction();
        mModel.setVocalizeVoice(voice);
        mRealm.commitTransaction();
    }
}
