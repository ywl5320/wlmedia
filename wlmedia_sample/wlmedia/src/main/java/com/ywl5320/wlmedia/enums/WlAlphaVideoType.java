package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/18
 */
public enum WlAlphaVideoType {

    WL_ALPHA_VIDEO_DEFAULT("WL_ALPHA_VIDEO_DEFAULT", 0, "the video have no alpha channel"),
    WL_ALPHA_VIDEO_CUSTOM("WL_ALPHA_VIDEO_CUSTOM", 1, "custom"),
    WL_ALPHA_VIDEO_LEFT_ALPHA("WL_ALPHA_VIDEO_LEFT_ALPHA", 2, "the video have left alpha channel(50%)"),
    WL_ALPHA_VIDEO_RIGHT_ALPHA("WL_ALPHA_VIDEO_RIGHT_ALPHA", 3, "the video have right alpha channel(50%)"),
    WL_ALPHA_VIDEO_TOP_ALPHA("WL_ALPHA_VIDEO_TOP_ALPHA", 4, "the video have left top channel(50%)"),
    WL_ALPHA_VIDEO_BOTTOM_ALPHA("WL_ALPHA_VIDEO_BOTTOM_ALPHA", 5, "the video have bottom alpha channel(50%)");

    private String key;
    private int value;
    private String desc;

    WlAlphaVideoType(String key, int value, String desc) {
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

    public static WlAlphaVideoType find(int value) {
        for (WlAlphaVideoType alphaVideoType : WlAlphaVideoType.values()) {
            if (alphaVideoType.value == value) {
                return alphaVideoType;
            }
        }
        return null;
    }
}
