package com.ywl5320.wlmedia.enums;

public enum WlMediaType {

    WL_MEDIA_TYPE_AUDIO("WL_MEDIA_TYPE_AUDIO", 1), //音频
    WL_MEDIA_TYPE_VIDEO("WL_MEDIA_TYPE_VIDEO", 2), //视频
    WL_MEDIA_TYPE_SUBTITLE("WL_MEDIA_TYPE_SUBTITLE", 2); //视频

    private String type;
    private int value;

    WlMediaType(String type, int value)
    {
        this.type = type;
        this.value = value;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
