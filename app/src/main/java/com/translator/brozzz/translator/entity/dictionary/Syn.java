
package com.translator.brozzz.translator.entity.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Syn {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("pos")
    @Expose
    private String pos;

    public String getText() {
        return text;
    }

    public String getPos() {
        return pos;
    }


}
