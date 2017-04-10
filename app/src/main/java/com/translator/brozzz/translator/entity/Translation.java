package com.translator.brozzz.translator.entity;

import io.realm.RealmObject;

public class Translation extends RealmObject{

    private String mTranslatedText;

    private String mLang;

    public Translation() {
    }

    public Translation(String translatedText, String lang) {
        this.mTranslatedText = translatedText;
        this.mLang = lang;
    }

    public String getTranslatedText() {
        return mTranslatedText;
    }

    public String getLang() {
        return mLang;
    }

}
