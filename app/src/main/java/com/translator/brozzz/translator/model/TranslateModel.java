package com.translator.brozzz.translator.model;

import com.translator.brozzz.translator.entity.Language;

public class TranslateModel {

    private Language translateFrom;
    private Language translateTo;

    public TranslateModel() {
        this.translateFrom = new Language("en", "Английский");
        this.translateTo = new Language("ru", "Русский");
    }

    public void switchLang(){
        Language tmp = translateFrom;
        translateFrom = translateTo;
        translateTo = tmp;
    }

    public Language getTranslateFrom() {
        return translateFrom;
    }

    public Language getTranslateTo() {
        return translateTo;
    }

    public void setTranslateFrom(Language translateFrom) {
        this.translateFrom = translateFrom;
    }

    public void setTranslateTo(Language translateTo) {
        this.translateTo = translateTo;
    }
}
