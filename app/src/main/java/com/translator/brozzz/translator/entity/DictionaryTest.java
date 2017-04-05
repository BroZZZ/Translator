package com.translator.brozzz.translator.entity;


import com.translator.brozzz.translator.entity.dictionary.Definition;

import java.util.ArrayList;
import java.util.List;

public class DictionaryTest {
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
