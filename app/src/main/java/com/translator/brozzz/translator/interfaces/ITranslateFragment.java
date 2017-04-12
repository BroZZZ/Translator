package com.translator.brozzz.translator.interfaces;

public interface ITranslateFragment {

    void displayTranslateResult(String originalText, String translatedText);

    void updateActionBar();

    void setFinalRecognizedText(String text);

    void setPartialRecognizedText(String text);

}
