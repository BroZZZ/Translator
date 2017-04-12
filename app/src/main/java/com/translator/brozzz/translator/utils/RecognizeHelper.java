package com.translator.brozzz.translator.utils;

import com.translator.brozzz.translator.interfaces.ITranslateFragment;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;

public class RecognizeHelper implements RecognizerListener {

    private ITranslateFragment mDestinationView;

    public RecognizeHelper(ITranslateFragment destinationView) {
        mDestinationView = destinationView;
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
