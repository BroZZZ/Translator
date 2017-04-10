package com.translator.brozzz.translator.entity;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TranslationInfo extends RealmObject {
    @PrimaryKey
    private String originalText;
    private Translation translation;
    private Dictionary dictionary;

    public TranslationInfo() {

    }

    public TranslationInfo(String originalText, Translation translation, Dictionary dictionary) {
        this.translation = translation;
        this.dictionary = dictionary;
        this.originalText = originalText;
    }

    public Translation getTranslation() {
        return translation;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public String getOriginalText() {
        return originalText;
    }
}
