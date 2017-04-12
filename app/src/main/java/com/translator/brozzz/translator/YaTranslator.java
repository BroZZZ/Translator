package com.translator.brozzz.translator;

import android.app.Application;
import android.content.Context;

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
        SpeechKit.getInstance().configure(getApplicationContext(), "846dd4ec-a55b-4868-9640-42c72a278a19");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
