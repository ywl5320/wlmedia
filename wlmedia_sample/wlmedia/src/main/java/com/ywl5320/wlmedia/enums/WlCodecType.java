package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/28
 */
public enum WlCodecType {
    WL_CODEC_AUTO("WL_CODEC_AUTO", 0, "try use hard codec first, if not work, then use soft codec"),
    WL_CODEC_SOFT("WL_CODEC_SOFT", 1, "only use soft codec");

    private String key;
    private int value;
    private String desc;

    WlCodecType(String type, int value, String desc) {
        this.key = type;
        this.value = value;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static WlCodecType find(int value) {
        for (WlCodecType codecType : WlCodecType.values()) {
            if (codecType.value == value) {
                return codecType;
            }
        }
        return WL_CODEC_AUTO;
    }
}
