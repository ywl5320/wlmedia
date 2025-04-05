package com.ywl5320.wlmedia.bean;

import com.ywl5320.wlmedia.enums.WlTrackType;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public class WlTrackInfoBean {
    private int trackType;
    private int trackIndex;
    private int programIndex;
    private double duration;
    private double startTime;
    private long bitrate;
    private long variantBitrate;
    private String language;
    private String codecName;
    private int videoWidth;
    private int videoHeight;
    private int videoScaleWidth;
    private int videoScaleHeight;
    private double videoRotate;
    private double videoFrameRate;
    private int audioSampleRate;
    private int audioChannelNum;

    public WlTrackInfoBean() {
    }

    public WlTrackType getTrackType() {
        return WlTrackType.find(trackType);
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public int getProgramIndex() {
        return programIndex;
    }

    public double getDuration() {
        return duration;
    }

    public double getStartTime() {
        return startTime;
    }

    public long getBitrate() {
        return bitrate;
    }

    public long getVariantBitrate() {
        return variantBitrate;
    }

    public String getLanguage() {
        return language;
    }

    public String getCodecName() {
        return codecName;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public int getVideoScaleWidth() {
        return videoScaleWidth;
    }

    public int getVideoScaleHeight() {
        return videoScaleHeight;
    }

    public double getVideoRotate() {
        return videoRotate;
    }

    public double getVideoFrameRate() {
        return videoFrameRate;
    }

    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    public int getAudioChannelNum() {
        return audioChannelNum;
    }

    @Override
    public String toString() {
        return "WlTrackInfoBean{" +
                "trackType=" + trackType +
                ", trackIndex=" + trackIndex +
                ", programIndex=" + programIndex +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", bitrate=" + bitrate +
                ", variantBitrate=" + variantBitrate +
                ", language='" + language + '\'' +
                ", codecName='" + codecName + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                ", videoScaleWidth=" + videoScaleWidth +
                ", videoScaleHeight=" + videoScaleHeight +
                ", videoRotate=" + videoRotate +
                ", videoFrameRate=" + videoFrameRate +
                ", audioSampleRate=" + audioSampleRate +
                ", audioChannelNum=" + audioChannelNum +
                '}';
    }
}
