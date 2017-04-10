package com.translator.brozzz.translator.entity.dictionary;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Dictionary  extends RealmObject{
    private RealmList<Definition> definitionsList = new RealmList<>();


    public Dictionary() {
    }

    public Definition getDefinition(int pos) {
        if (pos < definitionsList.size()) {
            return definitionsList.get(pos);
        }
        return null;
    }

    public void addDefinition(Definition definition) {
        definitionsList.add(definition);
    }

    public int getDefinitionSize() {
        return definitionsList.size();
    }
}
