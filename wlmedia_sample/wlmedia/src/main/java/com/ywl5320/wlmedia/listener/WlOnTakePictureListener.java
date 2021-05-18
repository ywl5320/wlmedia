package com.ywl5320.wlmedia.listener;

import android.graphics.Bitmap;

/**
 * Created by ywl5320 on 2019/11/8
 */
public interface WlOnTakePictureListener {

    /**
     * 截图回调
     * @param bitmap
     */
    void takePicture(Bitmap bitmap);

}
