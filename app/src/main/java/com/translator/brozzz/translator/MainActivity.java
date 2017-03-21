package com.translator.brozzz.translator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    private void getTranslate(String text) {
        mTranslatedText.setText(text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mDisposableChangeText.isDisposed()){
            mDisposableChangeText.dispose();
        }
    }
}
