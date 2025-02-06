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

    void onPrepared();

    void onTimeInfo(double currentTime, double bufferTime);

    void onComplete(WlCompleteType completeType, String msg);

    void onLoad(WlLoadStatus loadStatus, int progress, long speed);

    void onSeekFinish();

    void onFirstFrameRendered();

    default void onAutoPlay() {
    }

    default void onTakePicture(Bitmap bitmap) {
    }

    default byte[] onDeEncryptData(WlTrackType mediaType, byte[] data) {
        return null;
    }

    default void onOutRenderTexture(int textureId, int videoWidth, int videoHeight, int videoRotate) {
    }
}
