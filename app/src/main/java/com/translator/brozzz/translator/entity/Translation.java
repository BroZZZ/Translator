package com.translator.brozzz.translator.entity;

import io.realm.RealmObject;

public class Translation extends RealmObject{

    private String translatedText;

    private String lang;

    public Translation() {
    }

    public Translation(String translatedText, String lang) {
        this.translatedText = translatedText;
        this.lang = lang;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getLang() {
        return lang;
    }

}
