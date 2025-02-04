package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/24
 */
public enum WlCompleteType {
    WL_COMPLETE_NONE("WL_COMPLETE_NONE", -1, "complete with un know reason"), //未知
    WL_COMPLETE_EOF("WL_COMPLETE_EOF", 1, "complete with end of file"), //文件播放完成回调
    WL_COMPLETE_ERROR("WL_COMPLETE_ERROR", 2, "complete with an error"), //播放出错
    WL_COMPLETE_TIMEOUT("WL_COMPLETE_TIMEOUT", 3, "complete with timeout"),//超时完成
    WL_COMPLETE_HANDLE("WL_COMPLETE_HANDLE", 4, "complete with handel(active stop)）"), //手动触发停止
    WL_COMPLETE_NEXT("WL_COMPLETE_NEXT", 5, "complete with play the next source"), //切歌时完成
    WL_COMPLETE_LOOP("WL_COMPLETE_LOOP", 6, "complete with play loop"), //循环播放完成回调
    WL_COMPLETE_RELEASE("WL_COMPLETE_RELEASE", 7, "complete with release player, then the player can not use"); //release完成

    private String key;
    private int value;
    private String desc;

    WlCompleteType(String type, int value, String desc) {
        this.key = type;
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

    public static WlCompleteType find(int value) {
        for (WlCompleteType completeType : WlCompleteType.values()) {
            if (completeType.getValue() == value) {
                return completeType;
            }
        }
        return WL_COMPLETE_EOF;
    }
}
