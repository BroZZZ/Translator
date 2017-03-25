
package com.translator.brozzz.translator.entity.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dictionary {
    private static final String WORD_SEPARATOR = ", ";
    @SerializedName("def")
    @Expose
    private List<Def> def = null;

    public List<Def> getDef() {
        return def;
    }

    public String getSynonyms(int defId, int translationId) {
        if (def == null
                || defId >= def.size()
                || translationId >= def.get(defId).getTr().size())
            return "";

        Tr tr = def.get(defId).getTr().get(translationId);
        StringBuilder sbSynonym = new StringBuilder(tr.getText());
        if (tr.getSyn() != null) {
            sbSynonym.append(WORD_SEPARATOR);
            for (Syn syn : tr.getSyn()) {
                sbSynonym.append(syn.getText() + WORD_SEPARATOR);
            }
        }
        sbSynonym.delete(sbSynonym.length() - WORD_SEPARATOR.length(),sbSynonym.length());
        return sbSynonym.toString();
    }
}
