package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/17
 */
public enum WlAudioChannelType {
    WL_AUDIO_CHANNEL_CENTER("WL_AUDIO_CHANNEL_CENTER", 0, "both left and right channel together play"), //立体声（左右声道）
    WL_AUDIO_CHANNEL_LEFT("WL_AUDIO_CHANNEL_LEFT", 1, "only left channel play"), //左声道 一个扬声器
    WL_AUDIO_CHANNEL_LEFT_CENTER("WL_AUDIO_CHANNEL_LEFT_CENTER", 2, "left channel play from left and right together channel"), //左声道 2个扬声器
    WL_AUDIO_CHANNEL_RIGHT("WL_AUDIO_CHANNEL_RIGHT", 3, "only right channel play"), //右声道 一个扬声器
    WL_AUDIO_CHANNEL_RIGHT_CENTER("WL_AUDIO_CHANNEL_RIGHT_CENTER", 4, "right channel play from left and right together channel"); //左声道 2个扬声器


    private String key;
    private int value;
    private String desc;

    WlAudioChannelType(String key, int value, String desc) {
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

    public static WlAudioChannelType find(int value) {
        for (WlAudioChannelType audioChannelType : WlAudioChannelType.values()) {
            if (audioChannelType.value == value) {
                return audioChannelType;
            }
        }
        return null;
    }
}
