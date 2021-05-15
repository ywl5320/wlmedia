package com.ywl5320.wlmedia.enums;

/**
 * Created by ywl5320 on 2018/12/31
 */
public enum WlCodecType {

    CODEC_SOFT("SOFT", 0), // 只是软解码
    CODEC_MEDIACODEC("MEDIACODEC", 1); // 硬解码优先

    private String type;
    private int value;

    WlCodecType(String type, int value)
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
