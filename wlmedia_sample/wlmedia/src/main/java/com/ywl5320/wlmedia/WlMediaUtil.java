package com.ywl5320.wlmedia;

import android.graphics.Bitmap;

import com.ywl5320.wlmedia.bean.WlTrackInfoBean;
import com.ywl5320.wlmedia.enums.WlTrackType;
import com.ywl5320.wlmedia.listener.WlOnLoadLibraryListener;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/10
 */
public class WlMediaUtil {

    public WlMediaUtil() {
        this(null);
    }

    /**
     * 创建实例，必须调用release销毁资源
     *
     * @param onLoadLibraryListener
     */
    public WlMediaUtil(WlOnLoadLibraryListener onLoadLibraryListener) {
        if (onLoadLibraryListener == null || !onLoadLibraryListener.onLoadedLibrary()) {
            loadLibrary();
        }
        n_wlMediaUtil_init();
    }

    public static String getVersion() {
        return "wlmediautil-3.0.0";
    }

    /**
     * 设置 数据源
     *
     * @param source
     */
    public void setSource(String source) {
        n_wlMediaUtil_setSource(source);
    }

    /**
     * 打开 数据源
     *
     * @return 0：success
     * -1: 超时
     * -2: 错误
     */
    public int openSource() {
        return n_wlMediaUtil_openSource();
    }

    /**
     * 设置options
     *
     * @param key
     * @param value
     */
    public void setOptions(String key, String value) {
        n_wlMediaUtil_setStringMap(key, value);
    }

    /**
     * 清楚options
     */
    public void clearOptions() {
        n_wlMediaUtil_clearStringMap();
    }

    /**
     * 设置超时时间
     *
     * @param time 单位秒（s) > 0
     */
    public void setTimeOut(double time) {
        n_wlMediaUtil_setTimeOut(time);
    }

    /**
     * 获取音频 track 信息
     *
     * @return
     */
    public WlTrackInfoBean[] getAudioTracks() {
        return getMediaTracks(WlTrackType.WL_TRACK_AUDIO);
    }

    /**
     * 获取视频 track 信息
     *
     * @return
     */
    public WlTrackInfoBean[] getVideoTracks() {
        return getMediaTracks(WlTrackType.WL_TRACK_VIDEO);
    }

    /**
     * 获取字幕 track 信息
     *
     * @return
     */
    public WlTrackInfoBean[] getSubtitleTracks() {
        return getMediaTracks(WlTrackType.WL_TRACK_SUBTITLE);
    }

    /**
     * 获取对应track信息
     *
     * @param trackType
     * @return
     */
    private WlTrackInfoBean[] getMediaTracks(WlTrackType trackType) {
        return n_wlMediaUtil_getTracks(trackType.getValue());
    }

    /**
     * 获取对应时间截图
     *
     * @param time     如果 time > 0 , 就会取time对应的时间值，如果 time = 0，会按照顺序依次读取截图
     * @param keyFrame 表示是不是只取关键帧（速度快）
     * @return
     */
    public Bitmap getVideoFrame(double time, boolean keyFrame) {
        return getVideoFrame(0, time, keyFrame, 0, 0);
    }

    /**
     * 获取对应时间截图
     *
     * @param time        如果 time > 0 , 就会取time对应的时间值，如果 time = 0，会按照顺序依次读取截图
     * @param keyFrame    表示是不是只取关键帧（速度快）
     * @param scaleWidth  缩放宽
     * @param scaleHeight 缩放高
     * @return
     */
    public Bitmap getVideoFrame(double time, boolean keyFrame, int scaleWidth, int scaleHeight) {
        return getVideoFrame(0, time, keyFrame, scaleWidth, scaleHeight);
    }

    /**
     * 获取对应时间截图
     *
     * @param trackIndex  获取截图的视频track
     * @param time        如果 time > 0 , 就会取time对应的时间值，如果 time = 0，会按照顺序依次读取截图
     * @param keyFrame    表示是不是只取关键帧（速度快）
     * @param scaleWidth  缩放宽
     * @param scaleHeight 缩放高
     * @return
     */
    public Bitmap getVideoFrame(int trackIndex, double time, boolean keyFrame, int scaleWidth, int scaleHeight) {
        return n_wlMediaUtil_getVideoFrame(trackIndex, time, keyFrame, scaleWidth, scaleHeight);
    }

    /**
     * 销毁资源 注意：只要创建了实例，就要调用release销毁
     */
    public void release() {
        n_wlMediaUtil_release();
    }


    private native int n_wlMediaUtil_init();

    private native int n_wlMediaUtil_setSource(String value);

    private native int n_wlMediaUtil_setStringMap(String key, String value);

    private native int n_wlMediaUtil_clearStringMap();

    private native int n_wlMediaUtil_setTimeOut(double timeOut);

    private native int n_wlMediaUtil_openSource();

    private native WlTrackInfoBean[] n_wlMediaUtil_getTracks(int mediaType);

    private native Bitmap n_wlMediaUtil_getVideoFrame(int trackIndex, double time, boolean keyFrame, int scaleWidth, int scaleHeight);

    private native int n_wlMediaUtil_release();

    public static synchronized void loadLibrary() {
        try {
            System.loadLibrary("wlmediautil_v3.0.0");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

}
