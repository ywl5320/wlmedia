package com.ywl5320.wlmedia.bean;

import com.ywl5320.wlmedia.enums.WlTrackType;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/7
 */
public class WlTrackInfoBean {

    private WlTrackType trackType;
    private int programIndex;
    private int trackIndex;
    private double startTime;
    private double durationTime;
    private long bitrate;
    private String bandWidth;
    private String language;
    private String title;
    private int videoWidth;
    private int videoHeight;
    private int videoScaleWidth;
    private int videoScaleHeight;
    private int videoRotate;
    private double videoFPS;
    private int audioSampleRate;
    private int audioChannelNum;

    public WlTrackType getTrackType() {
        return trackType;
    }

    public int getProgramIndex() {
        return programIndex;
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getDurationTime() {
        return durationTime;
    }

    public long getBitrate() {
        return bitrate;
    }

    public String getBandWidth() {
        return bandWidth;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
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

    public int getVideoRotate() {
        return videoRotate;
    }

    public double getVideoFPS() {
        return videoFPS;
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
                ", programIndex=" + programIndex +
                ", trackIndex=" + trackIndex +
                ", startTime=" + startTime +
                ", durationTime=" + durationTime +
                ", bitrate=" + bitrate +
                ", bandWidth='" + bandWidth + '\'' +
                ", language='" + language + '\'' +
                ", title='" + title + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                ", videoScaleWidth=" + videoScaleWidth +
                ", videoScaleHeight=" + videoScaleHeight +
                ", videoRotate=" + videoRotate +
                ", videoFPS=" + videoFPS +
                ", audioSampleRate=" + audioSampleRate +
                ", audioChannelNum=" + audioChannelNum +
                '}';
    }
}
