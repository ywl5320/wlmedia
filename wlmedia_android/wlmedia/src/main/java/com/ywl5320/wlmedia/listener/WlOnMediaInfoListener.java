package com.ywl5320.wlmedia.listener;

import android.graphics.Bitmap;

import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlTrackType;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public interface WlOnMediaInfoListener {

    /**
     * 异步准备好
     * 当不是自动播放 setAutoPlay(false) 时，调用 prepare 异步准备好后会回调此函数，在此函数中可以调用 start() 方法即可开始播放。
     */
    void onPrepared();

    /**
     * 时间回调
     *
     * @param currentTime 当前播放时间
     * @param bufferTime 缓存时长
     */
    void onTimeInfo(double currentTime, double bufferTime);

    /**
     * complete回调
     *
     * @param completeType 表示 Complete 类型，具体值，参考 WlCompleteType 枚举类
     * @param msg 对应 Complete 类型的原因
     */
    void onComplete(WlCompleteType completeType, String msg);

    /**
     * 加载回调接口
     *
     * @param loadStatus 加载状态（开始加载、加载中、加载完成）
     * @param progress   加载进度 (0~100)
     * @param speed      加载速度 KB/s
     */
    void onLoad(WlLoadStatus loadStatus, int progress, long speed);

    /**
     * seek 完成回调
     */
    void onSeekFinish();

    /**
     * 首帧渲染回调
     */
    void onFirstFrameRendered();

    /**
     * 自动播放回调，当设置 setAutoPlay(true) 时，异步准备好后会回调此函数，并自动播放，不会再回调 onPrepared()。
     */
    default void onAutoPlay() {
    }

    /**
     * 截图回调
     *
     * @param bitmap
     */
    default void onTakePicture(Bitmap bitmap) {
    }

    /**
     * 音视频帧解密回调
     * @param mediaType 数据类型： 音频 或 视频
     * @param data 对应的解码前帧数据
     * @return
     */
    default byte[] onDeEncryptData(WlTrackType mediaType, byte[] data) {
        return null;
    }

    /**
     * 外部渲染信息回调（OpenGL）
     *
     * @param textureId OpenGL纹理，可用于Unity，cocos等显示视频
     * @param videoWidth 视频宽
     * @param videoHeight 视频高
     * @param videoRotate 视频旋转角度
     */
    default void onOutRenderTexture(int textureId, int videoWidth, int videoHeight, int videoRotate) {
    }
}
