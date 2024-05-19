package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/5
 */
public enum WlSeekType {

    WL_SEEK_FAST("WL_SEEK_FAST", 0, "not sync audio and video pts, use default"),
    WL_SEEK_NORMAL("WL_SEEK_NORMAL", 1, "sync audio and video pts, base on max pts"),
    WL_SEEK_ACCURATE("WL_SEEK_ACCURATE", 2, "sync audio and video pts, base on seek pts or max pts");

    private String key;
    private int value;
    private String desc;

    WlSeekType(String key, int value, String desc) {
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

    public static WlSeekType find(int value) {
        for (WlSeekType seekType : WlSeekType.values()) {
            if (seekType.value == value) {
                return seekType;
            }
        }
        return null;
    }
}
