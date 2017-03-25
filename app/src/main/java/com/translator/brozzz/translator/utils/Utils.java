package com.translator.brozzz.translator.utils;

public class Utils {

    public static class Constant {
        public static final String WORD_SEPARATOR = ", ";
    }

    public static class SharedPreferences{
        public static final String TRANSLATE_PREFERENCES = "com.translator.brozzz.preferences.translator";
        public static final String TRANSLATE_FROM_PREFERENCE = "com.translator.brozzz.preferences.pref.translate_from";
        public static final String TRANSLATE_TO_PREFERENCE = "com.translator.brozzz.preferences.pref.translate_to";
    }


    public enum Lang{
        RU("Русский","ru"),
        EN("Английский","en");

        private final String mCode;
        private final String mName;

        Lang(String name, String code) {
            this.mCode = code;
            this.mName = name;
        }

        public String getCode() {
            return mCode;
        }

        public String getName() {
            return mName;
        }
    }

}
