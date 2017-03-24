package com.translator.brozzz.translator.model;

public class TranslateModel {

    private String translateFrom;
    private String translateTo;

    public void changeLanguage(){
        String tmp = translateFrom;
        translateTo = translateFrom;
        translateFrom = tmp;
    }

    public String getTranslateFrom() {
        return translateFrom;
    }

    public String getTranslateTo() {
        return translateTo;
    }
}
