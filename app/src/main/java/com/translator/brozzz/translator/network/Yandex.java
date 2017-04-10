package com.translator.brozzz.translator.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.translator.brozzz.translator.entity.Translation;
import com.translator.brozzz.translator.entity.dictionary.Definition;
import com.translator.brozzz.translator.entity.dictionary.Dictionary;
import com.translator.brozzz.translator.interfaces.YandexDictionaryApi;
import com.translator.brozzz.translator.interfaces.YandexTranslateApi;

import java.lang.reflect.Type;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Yandex {

    public static class TranslateApi {
        public static final String TRANSLATOR_API_KEY = "trnsl.1.1.20170321T091507Z.5d4a62eb8c8c758d.19a4223d1dc1019da69006bc80e17685d394c534";
        private static final String TRANSLATOR_API_BASE_URL = "https://translate.yandex.net/";
        private static final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Translation.class, new TranslationDeserializer())
                .create();
        private static YandexTranslateApi yandexTranslateApi = new Retrofit.Builder()
                .baseUrl(TranslateApi.TRANSLATOR_API_BASE_URL) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build().create(YandexTranslateApi.class);


        public static YandexTranslateApi getTranslateApi() {
            return yandexTranslateApi;
        }

    }

    private static class TranslationDeserializer implements JsonDeserializer<Translation> {
        @Override
        public Translation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String lang = json.getAsJsonObject().get("lang").getAsString();
            String text = json.getAsJsonObject().get("text").getAsString();

            return new Translation(text, lang);
        }
    }

    public static class DictionaryApi {
        public static final String DICTIONARY_API_KEY = "dict.1.1.20170324T224309Z.9d5c946c31cb8396.e6ba9f50d45dcf44367faf8081bf71ef8b802a94";
        private static final String DICTIONARY_API_BASE_URL = "https://dictionary.yandex.net/";

        private static final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Dictionary.class, new DictionaryDeserializer())
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

    private static class DictionaryDeserializer implements JsonDeserializer<Dictionary> {
        @Override
        public Dictionary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            json.getAsJsonObject().getAsJsonArray("def");
            Dictionary dictionary = new Dictionary();
            for (JsonElement jsonObject : json.getAsJsonObject().getAsJsonArray("def")) {
                JsonArray translateArray = jsonObject.getAsJsonObject().getAsJsonArray("tr");
                for (JsonElement trElement : translateArray) {
                    Definition definition = new Definition(trElement.getAsJsonObject().get("text").getAsString());
                    JsonArray synonymsArray = trElement.getAsJsonObject().getAsJsonArray("syn");
                    if (synonymsArray != null) {
                        for (JsonElement synElement : synonymsArray) {
                            definition.addSynonym(synElement.getAsJsonObject().get("text").getAsString());
                        }
                    }
                    JsonArray meansArray = trElement.getAsJsonObject().getAsJsonArray("mean");
                    if (meansArray != null) {
                        for (JsonElement meanElement : meansArray) {
                            definition.addMean(meanElement.getAsJsonObject().get("text").getAsString());
                        }
                    }
                    dictionary.addDefinition(definition);
                }
            }


            return dictionary;
        }
    }
}
