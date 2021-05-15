package com.ywl5320.wlmedia.enums;

/**
 * Created by ywl5320 on 2018-3-16.
 */
public enum WlPlayModel {

    PLAYMODEL_AUDIO_VIDEO("PLAYMODEL_AUDIO_VIDEO", 0),
    PLAYMODEL_ONLY_AUDIO("PLAYMODEL_ONLY_AUDIO", 1),
    PLAYMODEL_ONLY_VIDEO("PLAYMODEL_ONLY_VIDEO", 2);

    private String playModel;
    private int value;

    WlPlayModel(String playModel, int value)
    {
        this.playModel = playModel;
        this.value = value;
    }

    public String getPlayModel() {
        return playModel;
    }

    public void setPlayModel(String playModel) {
        this.playModel = playModel;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
