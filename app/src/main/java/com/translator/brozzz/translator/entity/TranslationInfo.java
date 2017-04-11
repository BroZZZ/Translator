package com.translator.brozzz.translator.entity;

import android.support.annotation.NonNull;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TranslationInfo extends RealmObject {
    @PrimaryKey
    @NonNull
    private String originalText;
    private Translation translation;
    private Dictionary dictionary;
    private boolean isFavourite;

    public TranslationInfo() {

    }

    public TranslationInfo(@NonNull String originalText, Translation translation, Dictionary dictionary) {
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

    @NonNull
    public String getOriginalText() {
        return originalText;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    private void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void changeIsFavorite(){
        setFavourite(!isFavourite);
    }
}
