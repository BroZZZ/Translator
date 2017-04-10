package com.translator.brozzz.translator.entity;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TranslationInfo extends RealmObject {
    @PrimaryKey
    private String mOriginalText;
    private Translation mTranslation;
    private Dictionary mDictionary;

    public TranslationInfo() {

    }

    public TranslationInfo(String originalText, Translation translation, Dictionary dictionary) {
        mTranslation = translation;
        mDictionary = dictionary;
        mOriginalText = originalText;
    }

    public Translation getmTranslation() {
        return mTranslation;
    }

    public Dictionary getmDictionary() {
        return mDictionary;
    }

    public String getmOriginalText() {
        return mOriginalText;
    }
}
