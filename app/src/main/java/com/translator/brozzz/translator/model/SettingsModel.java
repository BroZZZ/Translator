package com.translator.brozzz.translator.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.yandex.speechkit.Vocalizer;

public class SettingsModel extends RealmObject {
    @PrimaryKey
    private int id = 2000; //yandex company foundation year, default value
    private boolean isTransleOnFlyOn;
    private int delayBeforeTranslate = 500;
    private String vocalizeVoice = Vocalizer.Voice.ALYSS;

    public SettingsModel() {
    }

    public boolean isTransleOnFlyOn() {
        return isTransleOnFlyOn;
    }

    public void setTransleOnFlyOn(boolean transleOnFlyOn) {
        isTransleOnFlyOn = transleOnFlyOn;
    }

    public int getDelayBeforeTranslate() {
        return delayBeforeTranslate;
    }

    public void setDelayBeforeTranslate(int delayBeforeTranslate) {
        this.delayBeforeTranslate = delayBeforeTranslate;
    }

    public String getVocalizeVoice() {
        return vocalizeVoice;
    }

    public void setVocalizeVoice(String vocalizeVoice) {
        this.vocalizeVoice = vocalizeVoice;
    }
}
