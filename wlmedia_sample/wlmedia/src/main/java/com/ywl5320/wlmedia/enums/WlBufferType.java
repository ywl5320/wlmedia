package com.ywl5320.wlmedia.enums;

/**
 * Created by ywl5320 on 2018/12/31
 */
public enum WlBufferType {

    BUFFER_QUEUE_SIZE("BUFFER_QUEUE_SIZE", 0), // 设置底层buffer 队列大小（所有流）
    BUFFER_MEMORY_SIZE("BUFFER_MEMORY_SIZE", 1), // 设置底层buffer 内存大小(单位：M)
    BUFFER_TIME("BUFFER_TIME", 2); // 设置底层buffer 时间大小（单位：s）

    private String type;
    private int value;

    WlBufferType(String type, int value)
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
