package com.translator.brozzz.translator.utils;

import com.translator.brozzz.translator.interfaces.ITranslateFragment;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.Vocalizer;

public class SpeechkitHelper implements RecognizerListener {

    private Vocalizer vocalizer;
    private Recognizer recognizer;
    private ITranslateFragment mDestinationView;

    public SpeechkitHelper(ITranslateFragment destinationView) {
        mDestinationView = destinationView;
    }

    public void Vocalize(String text, Utils.Lang language) {
        vocalizer = Vocalizer.createVocalizer(language.getCode(), text, true);
        vocalizer.start();
    }

    public void startRecognize(String language) throws SecurityException {
        recognizer = Recognizer.create(language, Recognizer.Model.NOTES, this, false);
        recognizer.start();
    }

    public void dismiss() {
        if (vocalizer != null)
            vocalizer.cancel();
        if (recognizer != null) {
            recognizer.cancel();
        }
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {

    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {

    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {

    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
        mDestinationView.setPartialRecognizedText(recognition.getBestResultText().trim());
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        mDestinationView.setFinalRecognizedText(recognition.getBestResultText().trim());
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {

    }
}
