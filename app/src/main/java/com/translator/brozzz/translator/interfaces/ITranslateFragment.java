package com.translator.brozzz.translator.interfaces;

public interface ITranslateFragment {

    /**
     * Display new translation result values
     * @param originalText original text
     * @param translatedText translated text
     */
    void displayTranslateResult(String originalText, String translatedText);

    /**
     * Update action bar view
     */
    void updateActionBar();

    /**
     * Set final recognized text in view
     * @param text
     */
    void setFinalRecognizedText(String text);

    /**
     * Set partial recognized text in view
     * @param text
     */
    void setPartialRecognizedText(String text);

    /**
     * On recognize start
     */
    void onRecognizeStart();

    /**
     * On recognize done
     */
    void onRecognizeDone();

    /**
     * On vocalization start
     */
    void onVocalizationStart(int textTypeId);

    /**
     * On vocalization end
     */
    void onVocalizationEnd(int textTypeId);

    void onDelayChanged();
}
