package com.translator.brozzz.translator.utils;

public class Utils {

    public static class Constant{
        public static class YandexApi{
            public static final String TRANSLATOR_API_KEY = "trnsl.1.1.20170321T091507Z.5d4a62eb8c8c758d.19a4223d1dc1019da69006bc80e17685d394c534";
            public static final String TRANSLATOR_API_FORMAT = "plain";
        }
    }

    //TODO добавить получение языка
    public static String getTranslateLang(){
        return "en-ru";
    }
}
