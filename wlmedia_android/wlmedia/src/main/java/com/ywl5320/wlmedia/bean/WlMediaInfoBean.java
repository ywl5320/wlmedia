package com.ywl5320.wlmedia.bean;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public class WlMediaInfoBean {
    private double duration;
    private double startTime;
    private long bitRate;

    private WlTrackInfoBean[] audioTracks;
    private WlTrackInfoBean[] videoTracks;
    private WlTrackInfoBean[] subtitleTracks;

    public WlMediaInfoBean() {
    }

    public double getDuration() {
        return duration;
    }

    public double getStartTime() {
        return startTime;
    }

    public long getBitRate() {
        return bitRate;
    }

    public WlTrackInfoBean[] getAudioTracks() {
        return audioTracks;
    }

    public WlTrackInfoBean[] getVideoTracks() {
        return videoTracks;
    }

    public WlTrackInfoBean[] getSubtitleTracks() {
        return subtitleTracks;
    }
}
