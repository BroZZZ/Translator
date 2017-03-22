package com.translator.brozzz.translator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.model.Translation;
import com.translator.brozzz.translator.utils.Utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText mTranslateText;
    private TextView mTranslatedText;
    Disposable mDisposableChangeText;
    Disposable mDisposableTranslater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTranslateText = (EditText) findViewById(R.id.translate_text);
        mTranslatedText = (TextView) findViewById(R.id.translated_text);
    }

    private void setTranslatedText(Translation translation){
        mTranslatedText.setText(translation.getTranslation().get(0));
    }

    private void getTranslate(String text) {
        mDisposableTranslater = YaTranslator.getApi().getTranslation
                (Utils.Constant.YandexApi.TRANSLATOR_API_KEY,
                text,
                Utils.getTranslateLang(),
                Utils.Constant.YandexApi.TRANSLATOR_API_FORMAT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTranslatedText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDisposableChangeText = RxTextView
                .textChanges(mTranslateText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(text -> text.toString().trim())
                .filter(text -> text.length() != 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getTranslate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mDisposableChangeText.isDisposed()) {
            mDisposableChangeText.dispose();
        }
        if (!mDisposableTranslater.isDisposed()) {
            mDisposableTranslater.dispose();
        }
    }
}
