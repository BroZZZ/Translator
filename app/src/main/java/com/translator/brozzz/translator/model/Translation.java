package com.translator.brozzz.translator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Translation {

    @SerializedName("text")
    @Expose
    private ArrayList<String> translation;

    @SerializedName("lang")
    @Expose
    private String lang;

    public ArrayList<String> getTranslation() {
        return translation;
    }

    public void setTranslation(ArrayList<String> translation) {
        this.translation = translation;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
