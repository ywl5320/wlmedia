package com.ywl5320.wlmedia.bean;

import com.ywl5320.wlmedia.enums.WlMediaType;

import java.util.Arrays;

public class WlMediaInfoBean {

    private WlMediaType mediaType;

    private int trackIndex;
    private int streamIndex;
    private double duration;
    private double startTime;
    private String language;
    private String title;
    private String name;
    private int extraDataSize;
    private byte[] extraData;

    private int width;
    private int height;
    private int scaleWidth;
    private int scaleHeight;
    private float videoRotate;
    private double fps;

    private int sampleRate;
    private int channelsNum;
    private String formatName;

    private double pts2time;

    @Override
    public String toString() {
        return "WlMediaInfoBean{" +
                "mediaType=" + mediaType +
                ", trackIndex=" + trackIndex +
                ", streamIndex=" + streamIndex +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", language='" + language + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", extraDataSize=" + extraDataSize +
                ", extraData=" + Arrays.toString(extraData) +
                ", width=" + width +
                ", height=" + height +
                ", scaleWidth=" + scaleWidth +
                ", scaleHeight=" + scaleHeight +
                ", videoRotate=" + videoRotate +
                ", fps=" + fps +
                ", sampleRate=" + sampleRate +
                ", channelsNum=" + channelsNum +
                ", formatName='" + formatName + '\'' +
                ", pts2time=" + pts2time +
                '}';
    }

    public WlMediaType getMediaType() {
        return mediaType;
    }

    public int getMediaTypeValue()
    {
        if(mediaType != null)
        {
            return mediaType.getValue();
        }
        return -1;
    }

    public void setMediaType(WlMediaType mediaType) {
        this.mediaType = mediaType;
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public void setTrackIndex(int trackIndex) {
        this.trackIndex = trackIndex;
    }

    public int getStreamIndex() {
        return streamIndex;
    }

    public void setStreamIndex(int streamIndex) {
        this.streamIndex = streamIndex;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getExtraDataSize() {
        return extraDataSize;
    }

    public void setExtraDataSize(int extraDataSize) {
        this.extraDataSize = extraDataSize;
    }

    public byte[] getExtraData() {
        return extraData;
    }

    public void setExtraData(byte[] extraData) {
        this.extraData = extraData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScaleWidth() {
        return scaleWidth;
    }

    public void setScaleWidth(int scaleWidth) {
        this.scaleWidth = scaleWidth;
    }

    public int getScaleHeight() {
        return scaleHeight;
    }

    public void setScaleHeight(int scaleHeight) {
        this.scaleHeight = scaleHeight;
    }

    public float getVideoRotate() {
        return videoRotate;
    }

    public void setVideoRotate(float videoRotate) {
        this.videoRotate = videoRotate;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getChannelsNum() {
        return channelsNum;
    }

    public void setChannelsNum(int channelsNum) {
        this.channelsNum = channelsNum;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public double getPts2time() {
        return pts2time;
    }

    public void setPts2time(double pts2time) {
        this.pts2time = pts2time;
    }
}
