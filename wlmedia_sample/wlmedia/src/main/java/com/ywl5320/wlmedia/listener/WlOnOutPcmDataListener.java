package com.ywl5320.wlmedia.listener;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/14
 */
public interface WlOnOutPcmDataListener {

    void onOutPcmInfo(int bit, int channel, int sampleRate);

    void onOutPcmBuffer(int size, byte[] buffers, double db);

}
