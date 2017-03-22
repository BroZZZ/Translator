package com.translator.brozzz.translator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.model.Translation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mTranslateText;
    private TextView mTranslatedText;
    Disposable mDisposableChangeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTranslateText = (EditText) findViewById(R.id.translate_text);
        mTranslatedText = (TextView) findViewById(R.id.translated_text);
        mDisposableChangeText = RxTextView
                .textChanges(mTranslateText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(text -> text.toString().trim())
                .filter(text -> text.length() != 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getTranslate);
    }



    private String getTranslate(String text) {
        try {
        Response<Translation> translation =
            YaTranslator.getApi().getTranslation(
                    "trnsl.1.1.20170321T091507Z.5d4a62eb8c8c758d.19a4223d1dc1019da69006bc80e17685d394c534",
                    text,
                    "ru-en",
                    "plain")
                    .execute();
            return translation.body().getTranslation().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mDisposableChangeText.isDisposed()) {
            mDisposableChangeText.dispose();
        }
    }
}
