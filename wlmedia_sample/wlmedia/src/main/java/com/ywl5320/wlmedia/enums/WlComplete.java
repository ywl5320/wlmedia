package com.ywl5320.wlmedia.enums;

/**
 *
 * mutex enum
 * Created by ywl5320 on 2018-3-16.
 */

public enum WlComplete {

    WL_COMPLETE_EOF("WL_COMPLETE_EOF", 1), //文件播放完成回调
    WL_COMPLETE_ERROR("WL_COMPLETE_ERROR", 2), //播放出错
    WL_COMPLETE_TIMEOUT("WL_COMPLETE_TIMEOUT", 3),//超时时完成
    WL_COMPLETE_HANDLE("WL_COMPLETE_HANDLE", 4), //手动触发停止
    WL_COMPLETE_NEXT("WL_COMPLETE_NEXT", 5), //切歌时完成
    WL_COMPLETE_LOOP("WL_COMPLETE_LOOP", 6); //循环时完成

    private String type;
    private int value;

    WlComplete(String type, int value)
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

    public static WlComplete getWlCompleteByValue(int value)
    {
        if(value == WlComplete.WL_COMPLETE_ERROR.value)
        {
            return WlComplete.WL_COMPLETE_ERROR;
        }
        if(value == WlComplete.WL_COMPLETE_TIMEOUT.value)
        {
            return WlComplete.WL_COMPLETE_TIMEOUT;
        }
        if(value == WlComplete.WL_COMPLETE_HANDLE.value)
        {
            return WlComplete.WL_COMPLETE_HANDLE;
        }
        return WlComplete.WL_COMPLETE_EOF;
    }
}
