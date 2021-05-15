package com.ywl5320.wlmedia.enums;

/**
 *
 * audio channel enum
 * Created by ywl5320 on 2018-3-16.
 */

public enum WlAudioChannel {

    CHANNEL_CENTER("CHANNEL_CENTER", 0), //立体声（左右声道）
    CHANNEL_LEFT("CHANNEL_LEFT", 1), //左声道 一个扬声器
    CHANNEL_LEFT_CENTER("CHANNEL_LEFT_CENTER", 2), //左声道 2个扬声器
    CHANNEL_RIGHT("CHANNEL_RIGHT", 3), //右声道 一个扬声器
    CHANNEL_RIGHT_CENTER("CHANNEL_RIGHT_CENTER", 4); //左声道 2个扬声器


    private String channel;
    private int value;

    WlAudioChannel(String channel, int value)
    {
        this.channel = channel;
        this.value = value;
    }

    public String getChannel() {
        return channel;
    }

    public int getValue() {
        return value;
    }
}
