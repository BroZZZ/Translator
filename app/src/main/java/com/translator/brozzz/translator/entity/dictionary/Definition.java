package com.translator.brozzz.translator.entity.dictionary;

public class Definition {
    String defenition = "";
    String synonyms = "";
    String means = "";

    public Definition(String defenition) {
        this.defenition = defenition;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    public void addSynonym(String synonym) {
        if (synonyms.isEmpty()) {
            synonyms = defenition;
        }
        synonyms.concat(", ".concat(synonym));
    }

    public void addMean(String mean) {
        if (means.isEmpty()) {
            means = mean;
        } else {
            means = means.concat(", ".concat(mean));
        }
    }
}
