package com.translator.brozzz.translator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText mTranslateText;
    private TextView mTranslatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTranslateText = (EditText) findViewById(R.id.translate_text);
        mTranslatedText = (TextView) findViewById(R.id.translated_text);
        RxTextView
                .textChanges(mTranslateText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(text -> text.toString().trim())
                .filter(text -> text.length() != 0)
                .subscribe((text) -> getTranslate(text));


    }

    private void getTranslate(String text){
        mTranslatedText.setText(text);
    }
}
