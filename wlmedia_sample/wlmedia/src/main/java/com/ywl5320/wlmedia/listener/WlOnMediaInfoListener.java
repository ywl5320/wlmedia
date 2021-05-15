package com.ywl5320.wlmedia.listener;

import com.ywl5320.wlmedia.enums.WlComplete;

public interface WlOnMediaInfoListener {

    /**
     * 异步准备好
     */
    void onPrepared();

    /**
     * 错误信息
     * @param code
     * @param msg
     */
    void onError(int code, String msg);

    /**
     * 播放完成
     * @param type
     * @param msg
     */
    void onComplete(WlComplete type, String msg);

    /**
     * 时间回调
     * @param currentTime
     * @param bufferTime
     */
    void onTimeInfo(double currentTime, double bufferTime);

    /**
     * seek完成
     */
    void onSeekFinish();

    /**
     * 循环播放次数
     * @param loopCount
     */
    void onLoopPlay(int loopCount);

    /**
     * 加载回调接口
     * @param load true:加载中
     */
    void onLoad(boolean load);

    /**
     * 加密数据解密回调
     * @param encryptBuffer 加密前的数据
     * @return 解密后的数据
     */
    byte[] decryptBuffer(byte[] encryptBuffer);

    /**
     * 播放byte[]类型数据入口
     * @param read_size 播放器需要buffer大小单位byte
     * @return 返回待播放数据
     */
    byte[] readBuffer(int read_size);

    /**
     * 暂停回调
     * @param pause
     */
    void onPause(boolean pause);
}
