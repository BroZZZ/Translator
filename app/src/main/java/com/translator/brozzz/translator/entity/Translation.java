package com.translator.brozzz.translator.entity;

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

    public String getLang() {
        return lang;
    }

    public String getTranslationString(){
        String arrayString = translation.toString();
        arrayString = arrayString.replace("[","");
        arrayString = arrayString.replace("]","");
        return arrayString;
    }


}
