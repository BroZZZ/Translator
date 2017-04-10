package com.translator.brozzz.translator.entity.dictionary;

import io.realm.RealmObject;

public class Definition extends RealmObject {

    private String defenition = "";
    private String synonyms = "";
    private String means = "";

    public Definition() {
    }

    public Definition(String defenition) {
        this.defenition = defenition;
        synonyms = defenition;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public String getMeans() {
        if (!means.isEmpty()) return "(" + means + ")";
        else return means;
    }

    public void addSynonym(String synonym) {
        if (synonyms.isEmpty()) {
            synonyms = synonym;
        }
        synonyms = synonyms.concat(", ".concat(synonym));
    }

    public void addMean(String mean) {
        if (means.isEmpty()) {
            means = mean;
        } else {
            means = means.concat(", ".concat(mean));
        }
    }
}
