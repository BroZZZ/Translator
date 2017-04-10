package com.translator.brozzz.translator.entity;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

import io.realm.RealmObject;

public class TranslationInfo extends RealmObject {
    private Translation mTranslation;
    private Dictionary mDictionary;

    public TranslationInfo() {

    }

    public TranslationInfo(Translation mTranslation, Dictionary mDictionary) {
        this.mTranslation = mTranslation;
        this.mDictionary = mDictionary;
    }

    public Translation getmTranslation() {
        return mTranslation;
    }

    public Dictionary getmDictionary() {
        return mDictionary;
    }
}
