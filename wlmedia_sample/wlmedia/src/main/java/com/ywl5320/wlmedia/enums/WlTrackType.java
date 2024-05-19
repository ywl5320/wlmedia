package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/7
 */
public enum WlTrackType {
    WL_TRACK_AUDIO("WL_TRACK_AUDIO", 1, "audio track"), //音频
    WL_TRACK_VIDEO("WL_TRACK_VIDEO", 2, "video track"), //视频
    WL_TRACK_SUBTITLE("WL_TRACK_SUBTITLE", 3, "subtitle track"); //字幕

    private String key;
    private int value;
    private String desc;

    WlTrackType(String key, int value, String desc) {
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
}
