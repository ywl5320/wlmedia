package com.ywl5320.wlmedia.listener;

import android.graphics.Bitmap;

import com.ywl5320.wlmedia.bean.WlOutRenderBean;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/1/9
 */
public interface WlOnMediaInfoListener {

    /**
     * 异步准备好
     */
    void onPrepared();

    /**
     * 时间回调
     *
     * @param currentTime
     * @param bufferTime
     */
    void onTimeInfo(double currentTime, double bufferTime);

    /**
     * complete回调
     *
     * @param wlCompleteType
     * @param msg
     */
    void onComplete(WlCompleteType wlCompleteType, String msg);

    /**
     * 加载回调接口
     *
     * @param loadStatus 加载状态（开始加载、加载中、加载完成）
     * @param progress   加载进度
     * @param speed      加载速度 KB/s
     */
    void onLoad(WlLoadStatus loadStatus, int progress, long speed);


    /**
     * 首帧渲染回调
     */
    default void onFirstFrameRendered() {

    }

    /**
     * 自动播放回调
     */
    default void onAutoPlay() {
    }

    /**
     * seek 完成回调
     */
    default void onSeekFinish() {
    }

    /**
     * 加密数据解密回调
     *
     * @param encryptBuffer 加密前的数据
     * @return 解密后的数据
     */
    default byte[] decryptBuffer(byte[] encryptBuffer, long position) {
        return encryptBuffer;
    }


    /**
     * 播放byte[]类型数据入口
     *
     * @param read_size 播放器需要buffer大小单位byte
     * @return 返回待播放数据
     */
    default byte[] readBuffer(int read_size) {
        return null;
    }

    /**
     * 截图回调
     *
     * @param bitmap
     */
    default void onTakePicture(Bitmap bitmap) {
    }

    /**
     * 外部渲染信息回调
     *
     * @param outRenderBean
     */
    default void onOutRenderInfo(WlOutRenderBean outRenderBean) {

    }

}
