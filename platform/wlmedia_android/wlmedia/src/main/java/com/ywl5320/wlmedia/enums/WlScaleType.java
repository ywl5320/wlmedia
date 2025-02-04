package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/9/5
 */
public enum WlScaleType {

    WL_SCALE_16_9("WL_SCALE_16_9", 16, 9, "scale video width and height use 16:9"),
    WL_SCALE_4_3("WL_SCALE_4_3", 4, 3, "scale video width and height use 4:3"),
    WL_SCALE_FIT("WL_SCALE_FIT", 0, 0, "scale video width and height fit"),
    WL_SCALE_MATCH("WL_SCALE_MATCH", -1, -1, "scale video width and height match"),
    WL_SCALE_FILL("WL_SCALE_MATCH", -2, -2, "scale video width and height fill");

    private String key;
    private int scaleWidth = 0;
    private int scaleHeight = 0;
    private String desc;

    WlScaleType(String key, int scaleWidth, int scaleHeight, String desc) {
        this.key = key;
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public int getScaleWidth() {
        return scaleWidth;
    }

    public int getScaleHeight() {
        return scaleHeight;
    }

    public String getDesc() {
        return desc;
    }
}
