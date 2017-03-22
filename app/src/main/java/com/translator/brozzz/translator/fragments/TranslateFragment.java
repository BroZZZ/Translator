package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.YaTranslator;
import com.translator.brozzz.translator.model.Translation;
import com.translator.brozzz.translator.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TranslateFragment extends Fragment {
    Disposable mDisposableChangeText;
    Disposable mDisposableTranslater;

    @BindView(R.id.translate_text)
    EditText mTranslateText;

    @BindView(R.id.translated_text)
    TextView mTranslatedText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translate_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onResume() {
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
    public void onPause() {
        super.onPause();
        if (!mDisposableChangeText.isDisposed()) {
            mDisposableChangeText.dispose();
        }
        if (!mDisposableTranslater.isDisposed()) {
            mDisposableTranslater.dispose();
        }

    }

    private void setTranslatedText(Translation translation) {
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
}
