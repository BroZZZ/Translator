
package com.translator.brozzz.translator.entity.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("mean")
    @Expose
    private List<Mean> mean = null;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }

    public List<Syn> getSyn() {
        return syn;
    }

    public List<Mean> getMean() {
        return mean;
    }

}
