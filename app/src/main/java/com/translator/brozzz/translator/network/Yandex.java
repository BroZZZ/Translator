package com.translator.brozzz.translator.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Yandex {
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static class TranslateApi {
        public static final String TRANSLATOR_API_KEY = "trnsl.1.1.20170321T091507Z.5d4a62eb8c8c758d.19a4223d1dc1019da69006bc80e17685d394c534";
        private static final String TRANSLATOR_API_BASE_URL = "https://translate.yandex.net/";

        private static YandexTranslateApi yandexTranslateApi = new Retrofit.Builder()
                .baseUrl(TranslateApi.TRANSLATOR_API_BASE_URL) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build().create(YandexTranslateApi.class);


        public static YandexTranslateApi getTranslateApi() {
            return yandexTranslateApi;
        }

    }

    public static class DictionaryApi{
        public static final String DICTIONARY_API_KEY = "dict.1.1.20170324T224309Z.9d5c946c31cb8396.e6ba9f50d45dcf44367faf8081bf71ef8b802a94";
        private static final String DICTIONARY_API_BASE_URL = "https://dictionary.yandex.net/";
        private static Gson gson = new GsonBuilder()
                .create();
        private static YandexDictionaryApi yandexDictionaryApi = new Retrofit.Builder()
                .baseUrl(DictionaryApi.DICTIONARY_API_BASE_URL) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build().create(YandexDictionaryApi.class);


        public static YandexDictionaryApi getDictionaryApi() {
            return yandexDictionaryApi;
        }
    }
}
