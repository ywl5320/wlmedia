package com.ywl5320.wlmedia.listener;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/14
 */
public interface WlOnBufferDataListener {
    long onBufferByteLength();

    byte[] onBufferByteData(long position, long bufferSize);
}
