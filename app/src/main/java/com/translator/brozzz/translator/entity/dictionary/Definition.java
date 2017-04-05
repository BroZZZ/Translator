package com.translator.brozzz.translator.entity.dictionary;

public class Definition {
    String defenition = "";
    String synonyms = "";
    String means = "";

    public Definition(String defenition) {
        this.defenition = defenition;
        synonyms = defenition;
    }

    public String getSynonyms() {
        return  synonyms;
    }

    public String getMeans() {
        return "(" + means + ")";
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
