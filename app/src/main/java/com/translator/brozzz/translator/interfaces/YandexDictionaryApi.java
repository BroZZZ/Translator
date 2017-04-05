package com.translator.brozzz.translator.interfaces;

import com.translator.brozzz.translator.entity.DictionaryTest;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexDictionaryApi {
    @GET("api/v1/dicservice.json/lookup")
    Observable<DictionaryTest> getDictionary(@Query("key") String apiKey,
                                             @Query("text") String text,
                                             @Query("lang") String lang);

}
