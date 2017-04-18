package com.translator.brozzz.translator;

import android.app.Application;
import android.content.Context;

import com.translator.brozzz.translator.model.SettingsModel;
import com.translator.brozzz.translator.network.Yandex;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.yandex.speechkit.SpeechKit;

public class YaTranslator extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());
        SpeechKit.getInstance().configure(getApplicationContext(), Yandex.SPEECHKIT_KEY);

        initDefaultSetting();
    }

    /**
     * Initialization default setting
     */
    private void initDefaultSetting() {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(SettingsModel.class).findFirst() == null){
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new SettingsModel());
            realm.commitTransaction();
        }
        realm.close();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
