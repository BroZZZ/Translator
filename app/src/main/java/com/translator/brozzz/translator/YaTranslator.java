package com.translator.brozzz.translator;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class YaTranslator extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
