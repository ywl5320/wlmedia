package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/3
 */
public enum WlRotateType {
    WL_ROTATE_DEFAULT("WL_ROTATE_DEFAULT", -1, "the video not rotate any degree"),
    WL_ROTATE_0("WL_ROTATE_0", 0, "the video rotate 0 degree"),
    WL_ROTATE_90("WL_ROTATE_90", 90, "the video rotate 90 degree"),
    WL_ROTATE_180("WL_ROTATE_180", 180, "the video rotate 180 degree"),
    WL_ROTATE_270("WL_ROTATE_270", 270, "the video rotate 270 degree");

    private String key;
    private int value = 0;
    private String desc;

    WlRotateType(String key, int value, String desc) {
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

    public static WlRotateType find(int value) {
        for (WlRotateType rotateType : WlRotateType.values()) {
            if (rotateType.value == value) {
                return rotateType;
            }
        }
        return null;
    }
}
