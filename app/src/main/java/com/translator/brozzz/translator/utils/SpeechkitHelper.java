package com.translator.brozzz.translator.utils;

import com.translator.brozzz.translator.interfaces.ITranslateFragment;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;

public class SpeechkitHelper implements RecognizerListener, VocalizerListener {

    private Vocalizer vocalizer;
    private Recognizer recognizer;
    private ITranslateFragment mDestinationView;
    private int textTypeId;
    private boolean mIsVocalizeNow;

    public SpeechkitHelper(ITranslateFragment destinationView) {
        mDestinationView = destinationView;
    }

    public void Vocalize(String text, Utils.Lang language, int textTypeId) {

        dismiss();

        this.textTypeId = textTypeId;
        vocalizer = Vocalizer.createVocalizer(language.getCode(), text, true);
        vocalizer.setListener(this);
        vocalizer.start();
    }

    public void startRecognize(String language) throws SecurityException {
        dismiss();
        recognizer = Recognizer.create(language, Recognizer.Model.NOTES, this, false);
        recognizer.start();
    }

    public void dismiss() {
        if (vocalizer != null) {
            if (mIsVocalizeNow) {
                onPlayingDone(vocalizer);
            }
            vocalizer.cancel();
        }
        if (recognizer != null) {
            recognizer.cancel();
        }
    }

    /*
    VocalizerListener implementation
    */
    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {
    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {
        mIsVocalizeNow = true;
        mDestinationView.onVocalizeStart(textTypeId);
    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        mIsVocalizeNow = false;
        mDestinationView.onVocalizeEnd(textTypeId);
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {

    }


    /*
    RecognizerListener implementation
     */

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        mDestinationView.onRecognizeStart();
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        mDestinationView.onRecognizeDone();
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
