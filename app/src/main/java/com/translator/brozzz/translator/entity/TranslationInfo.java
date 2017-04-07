package com.translator.brozzz.translator.entity;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

public class TranslationInfo {
    private Translation mTranslation;
    private Dictionary mDictionary;

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
