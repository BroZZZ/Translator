package com.translator.brozzz.translator.model;

import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.utils.Utils;

public class TranslateModel {

    private Utils.Lang translateFrom;
    private Utils.Lang translateTo;
    private TranslationInfo translationInfo;

    public TranslateModel(String translateFrom, String translateTo) {
        if (!translateFrom.isEmpty() && !translateTo.isEmpty()) {
            this.translateFrom = Utils.Lang.valueOf(translateFrom);
            this.translateTo = Utils.Lang.valueOf(translateTo);
        } else {
            this.translateFrom = Utils.Lang.EN;
            this.translateTo = Utils.Lang.RU;
        }
    }

    public void switchLang() {
        Utils.Lang tmp = translateFrom;
        translateFrom = translateTo;
        translateTo = tmp;
    }

    public Utils.Lang getTranslateFrom() {
        return translateFrom;
    }

    public Utils.Lang getTranslateTo() {
        return translateTo;
    }

    public TranslationInfo getTranslationInfo() {
        return translationInfo;
    }

    public void setTranslationInfo(TranslationInfo translationInfo) {
        this.translationInfo = translationInfo;
    }
}
