package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/18
 */
public enum WlSampleRate {
    WL_SAMPLE_RATE_NONE("WL_SAMPLE_RATE_NONE", 0, "use default audio sample rate"),
    WL_SAMPLE_RATE_8000("WL_SAMPLE_RATE_8000", 8000, "convert audio sample rate to 8000HZ"),
    WL_SAMPLE_RATE_11025("WL_SAMPLE_RATE_11025", 11025, "convert audio sample rate to 11025HZ"),
    WL_SAMPLE_RATE_12000("WL_SAMPLE_RATE_12000", 12000, "convert audio sample rate to 12000HZ"),
    WL_SAMPLE_RATE_16000("WL_SAMPLE_RATE_16000", 16000, "convert audio sample rate to 16000HZ"),
    WL_SAMPLE_RATE_22050("WL_SAMPLE_RATE_22050", 22050, "convert audio sample rate to 22050HZ"),
    WL_SAMPLE_RATE_24000("WL_SAMPLE_RATE_24000", 24000, "convert audio sample rate to 24000HZ"),
    WL_SAMPLE_RATE_32000("WL_SAMPLE_RATE_32000", 32000, "convert audio sample rate to 32000HZ"),
    WL_SAMPLE_RATE_44100("WL_SAMPLE_RATE_44100", 44100, "convert audio sample rate to 44100HZ"),
    WL_SAMPLE_RATE_48000("WL_SAMPLE_RATE_48000", 48000, "convert audio sample rate to 48000HZ");

    private String key;
    private int value;
    private String desc;

    WlSampleRate(String key, int value, String desc) {
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

    public static WlSampleRate find(int value) {
        for (WlSampleRate sampleRate : WlSampleRate.values()) {
            if (sampleRate.value == value) {
                return sampleRate;
            }
        }
        return WL_SAMPLE_RATE_NONE;
    }
}
