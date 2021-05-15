package com.ywl5320.wlmedia.enums;

/**
 * Created by ywl5320 on 2018-3-16.
 */
public enum WlVideoRotate {

    VIDEO_ROTATE_DEFAULT("VIDEO_ROTATE_NORMAL", -1),//视频默认角度
    VIDEO_ROTATE_0("VIDEO_ROTATE_NORMAL", 0),//顺时针旋转0度
    VIDEO_ROTATE_90("VIDEO_ROTATE_90", -90),//顺时针旋转90度
    VIDEO_ROTATE_180("VIDEO_ROTATE_180", -180),//顺时针旋转180度
    VIDEO_ROTATE_270("VIDEO_ROTATE_270", -270);//顺时针旋转270度

    private String rotate;
    private int value;

    WlVideoRotate(String rotate, int value)
    {
        this.rotate = rotate;
        this.value = value;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
