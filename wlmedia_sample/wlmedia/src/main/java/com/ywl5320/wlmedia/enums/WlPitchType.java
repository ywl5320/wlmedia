package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/2
 */
public enum WlPitchType {
    WL_PITCH_NORMAL("WL_PITCH_NORMAL", 0, "normal pitch range[0.25,4.0]"),
    WL_PITCH_SEMITONES("WL_PITCH_SEMITONES", 1, "semitones pitch range[-12,12]"),
    WL_PITCH_OCTAVES("WL_PITCH_OCTAVES", 2, "octaves pitch range[-1,1]");

    private String key;
    private int value;
    private String desc;

    WlPitchType(String key, int value, String desc) {
        this.key = key;
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

    public static WlPitchType find(int value) {
        for (WlPitchType pitchType : WlPitchType.values()) {
            if (pitchType.value == value) {
                return pitchType;
            }
        }
        return null;
    }
}
