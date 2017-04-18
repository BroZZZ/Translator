package com.translator.brozzz.translator.model;

import com.translator.brozzz.translator.utils.Utils;

public class TranslateModel {

    private Utils.Lang mTranslateFrom;
    private Utils.Lang mTranslateTo;
    private SettingsModel mSettings;

    public TranslateModel(String translateFrom, String translateTo) {
        if (!translateFrom.isEmpty() && !translateTo.isEmpty()) {
            mTranslateFrom = Utils.Lang.valueOf(translateFrom);
            mTranslateTo = Utils.Lang.valueOf(translateTo);
        } else {
            mTranslateFrom = Utils.Lang.EN;
            mTranslateTo = Utils.Lang.RU;
        }
    }

    public SettingsModel getSettings(){
        return mSettings;
    }

    public void setSettings(SettingsModel mSettings) {
        this.mSettings = mSettings;
    }

    public void switchLang() {
        Utils.Lang tmp = mTranslateFrom;
        mTranslateFrom = mTranslateTo;
        mTranslateTo = tmp;
    }

    public Utils.Lang getTranslateFrom() {
        return mTranslateFrom;
    }

    public Utils.Lang getTranslateTo() {
        return mTranslateTo;
    }

}
