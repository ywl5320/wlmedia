package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/4/27
 */
public enum WlSourceType {
    WL_SOURCE_NORMAL("WL_SOURCE_NORMAL", 0, "normal source type eg: file or net address"),//常规播放
    WL_SOURCE_BUFFER("WL_SOURCE_BUFFER", 1, "play buffer with byte[]"),//播放byte[]
    WL_SOURCE_ENCRYPT_FILE("WL_SOURCE_ENCRYPT_FILE", 2, "play encrypted files with byte[]");//播放加密文件

    private String key;
    private int value;
    private String desc;

    WlSourceType(String key, int value, String desc) {
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

    public static WlSourceType find(int value) {
        for (WlSourceType sourceType : WlSourceType.values()) {
            if (sourceType.value == value) {
                return sourceType;
            }
        }
        return null;
    }

}
