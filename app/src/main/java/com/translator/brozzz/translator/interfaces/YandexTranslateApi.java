package com.translator.brozzz.translator.interfaces;

import com.translator.brozzz.translator.model.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexTranslateApi {
    @GET("api/v1.5/tr.json/translate")
    Observable<Translation> getTranslation(@Query("key") String apiKey,
                                           @Query("text") String text,
                                           @Query("lang") String lang,
                                           @Query("format") String yaFormat);

}
