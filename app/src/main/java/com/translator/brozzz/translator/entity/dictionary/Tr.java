
package com.translator.brozzz.translator.entity.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.translator.brozzz.translator.utils.Utils;

import java.util.List;

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
    @SerializedName("means")
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
            sbMeans.append(mean.getText() + Utils.Constant.WORD_SEPARATOR);
        }
        sbMeans.delete(sbMeans.length() - Utils.Constant.WORD_SEPARATOR.length(), sbMeans.length());
        sbMeans.append(")");
        return sbMeans.toString();
    }
}
