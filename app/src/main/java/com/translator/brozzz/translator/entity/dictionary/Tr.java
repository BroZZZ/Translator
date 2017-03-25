
package com.translator.brozzz.translator.entity.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.translator.brozzz.translator.entity.dictionary.Dictionary.WORD_SEPARATOR;

public class Tr {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("syn")
    @Expose
    private List<Syn> syn = null;
    @SerializedName("mean")
    @Expose
    private List<Mean> means = null;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public List<Syn> getSyn() {
        return syn;
    }

    public List<Mean> getMeans() {
        return means;
    }

    public String getMeansString() {
        if (means == null)
            return "";

        StringBuilder sbMeans = new StringBuilder("(");
        for (Mean mean : means) {
            sbMeans.append(mean.getText() + Dictionary.WORD_SEPARATOR);
        }
        sbMeans.delete(sbMeans.length() - WORD_SEPARATOR.length(), sbMeans.length());
        sbMeans.append(")");
        return sbMeans.toString();
    }
}
