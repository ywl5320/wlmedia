package com.ywl5320.wlmedia.enums;

public enum  WlAlphaVideoType {

    WL_ALPHA_VIDEO_NO("WL_ALPHA_VIDEO_NO", 0), //常规视频
    WL_ALPHA_VIDEO_LEFT("WL_ALPHA_VIDEO_LEFT", 1), //透明通道值在左边 （占视频宽度一半）
    WL_ALPHA_VIDEO_RIGHT("WL_ALPHA_VIDEO_RIGHT", 2); //透明通道值在右边（占视频宽度一半）

    private String type;
    private int value;

    WlAlphaVideoType(String type, int value)
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
