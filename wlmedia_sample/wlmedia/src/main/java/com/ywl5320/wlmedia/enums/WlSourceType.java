package com.ywl5320.wlmedia.enums;

/**
 *
 * mutex enum
 * Created by ywl5320 on 2018-3-16.
 */

public enum WlSourceType {

    NORMAL("NORMAL", 0),//常规播放
    BUFFER("BUFFER", 1),//播放byte[]
    ENCRYPT_FILE("ENCRYPT_FILE", 2);//播放加密文件

    private String type;
    private int value;

    WlSourceType(String type, int value)
    {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
