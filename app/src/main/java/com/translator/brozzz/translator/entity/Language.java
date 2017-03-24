package com.translator.brozzz.translator.entity;

public class Language {
    String code;
    String name;

    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
