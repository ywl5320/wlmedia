package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/9/5
 */
public enum WlMirrorType {
    WL_MIRROR_NONE("WL_MIRROR_NONE", 0, "not mirror video"),
    WL_MIRROR_TOP_BOTTOM("WL_MIRROR_TOP_BOTTOM", 1, "mirror video top and bottom"),
    WL_MIRROR_LEFT_RIGHT("WL_MIRROR_LEFT_RIGHT", 2, "mirror video left and right"),
    WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT("WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT", 3, "mirror video top and bottom, left and right");


    private String key;
    private int value = 0;
    private String desc;

    WlMirrorType(String key, int value, String desc) {
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

    public static WlMirrorType find(int value) {
        for (WlMirrorType mirrorType : WlMirrorType.values()) {
            if (mirrorType.value == value) {
                return mirrorType;
            }
        }
        return null;
    }
}
