package com.ywl5320.wlmedia.bean;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/10/20
 */
public class WlOutRenderTextureBean {

    private int textureId;
    private int videoWidth;
    private int videoHeight;
    private int videoRotate;

    public WlOutRenderTextureBean(){
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public int getVideoRotate() {
        return videoRotate;
    }

    public void setVideoRotate(int videoRotate) {
        this.videoRotate = videoRotate;
    }
}
