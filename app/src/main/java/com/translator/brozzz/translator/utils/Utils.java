package com.translator.brozzz.translator.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    public static class SharedPreferences {
        public static final String TRANSLATE_PREFERENCES = "com.translator.brozzz.preferences.translator";
        public static final String TRANSLATE_FROM_PREFERENCE = "com.translator.brozzz.preferences.pref.translate_from";
        public static final String TRANSLATE_TO_PREFERENCE = "com.translator.brozzz.preferences.pref.translate_to";
    }

    public static class IntentExtras{
        public static final String FAVORITE_ONLY = "com.translator.brozz.extra.FAVORITE_ONLY";
    }

    public static class Broadcast{
        public static final String ACTION_DELAY_CHANGED = "com.translator.brozz.action.ACTION_DELAY_CHANGED";
        public static final String ACTION_TRANSLATE_ON_FLY_CHANGED = "com.translator.brozz.action.TRANSLATE_ON_FLY_CHANGED";
    }

    /**
     * Standard suitable languages
     */
    public enum Lang {
        RU("Русский", "ru"),
        EN("Английский", "en");

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

    /**
     * hide keyboard
     * @param activity activity for get input service
     * @param focusableView view with focus
     */
    public static void hideKeyboard(Activity activity, View focusableView) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusableView.getWindowToken(), 0);
    }

}
