package com.translator.brozzz.translator.interfaces;

import com.translator.brozzz.translator.entity.dictionary.Dictionary;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexDictionaryApi {
    @GET("api/v1/dicservice.json/lookup")
    Observable<Dictionary> getDictionary(@Query("key") String apiKey,
                                         @Query("text") String text,
                                         @Query("lang") String lang);

}
