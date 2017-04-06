package com.translator.brozzz.translator.entity.dictionary;


import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Definition> definitionList = new ArrayList<>();

    public Definition getDefinition(int pos) {
        if (pos < definitionList.size()) {
            return definitionList.get(pos);
        }
        return null;
    }

    public void addDefinition(Definition definition) {
        definitionList.add(definition);
    }

    public int getDefinitionSize() {
        return definitionList.size();
    }
}
