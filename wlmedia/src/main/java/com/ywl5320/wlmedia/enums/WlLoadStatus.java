package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/17
 */
public enum WlLoadStatus {
    WL_LOADING_STATUS_START("WL_LOADING_STATUS_START", 0, "load start"),
    WL_LOADING_STATUS_PROGRESS("WL_LOADING_STATUS_PROGRESS", 1, "load progress"),
    WL_LOADING_STATUS_FINISH("WL_LOADING_STATUS_FINISH", 2, "load finish");

    private String key;
    private int value;
    private String desc;

    WlLoadStatus(String key, int value, String desc) {
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

    public static WlLoadStatus find(int value) {
        for (WlLoadStatus loadStatus : WlLoadStatus.values()) {
            if (loadStatus.getValue() == value) {
                return loadStatus;
            }
        }
        return WL_LOADING_STATUS_FINISH;
    }
}
