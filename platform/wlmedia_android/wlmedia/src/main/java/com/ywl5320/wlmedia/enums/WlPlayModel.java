package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/9/1
 */
public enum WlPlayModel {
    WL_PLAY_MODEL_AUTO("WL_PLAY_MODEL_AUTO", 0, "play audio and video, if the media have audio and video, play both, if only have audio, play audio, if only have video, play video"),

    WL_PLAY_MODEL_ONLY_AUDIO("WL_PLAY_MODEL_ONLY_AUDIO", 1, "only play audio, if the media not have audio, then play nothing"),

    WL_PLAY_MODEL_ONLY_VIDEO("WL_PLAY_MODEL_ONLY_VIDEO", 2, "only play video, if the media not have video, then play nothing");

    private String key;
    private int value;
    private String desc;

    WlPlayModel(String key, int value, String desc) {
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

    public static WlPlayModel find(int value) {
        for (WlPlayModel playModel : WlPlayModel.values()) {
            if (playModel.value == value) {
                return playModel;
            }
        }
        return WL_PLAY_MODEL_AUTO;
    }
}
