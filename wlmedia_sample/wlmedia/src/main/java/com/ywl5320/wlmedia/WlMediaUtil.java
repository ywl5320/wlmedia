package com.ywl5320.wlmedia;

import android.graphics.Bitmap;

import com.ywl5320.wlmedia.bean.WlMediaInfoBean;
import com.ywl5320.wlmedia.log.WlLog;

import java.util.HashMap;

public class WlMediaUtil {

    static {
        System.loadLibrary("wlmediautil-2.0.0");
    }

    /**
     * 数据源
     */
    private String source;

    private long hashCode = -1; //hashcode值

    /**
     * 设置FFmpeg参数
     */
    private HashMap<String, String> ffOptions;

    public WlMediaUtil()
    {
        hashCode = -1;
    }

    /**
     * 清除ff参数信息
     */
    public void clearFFOptions()
    {
        if(ffOptions != null)
        {
            ffOptions.clear();
        }
    }

    /**
     * 设置ff参数信息
     * @param key
     * @param value
     */
    public void setFFOptions(String key, String value)
    {
        if(ffOptions == null)
        {
            ffOptions = new HashMap<String, String>();
        }
        ffOptions.put(key, value);
    }


    /**
     * 设置数据源
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 打开数据源
     * @return
     */
    public int openSource()
    {
        return n_demuxer(source);
    }

    /**
     * 得到音视频基础信息
     * @return
     */
    public WlMediaInfoBean[] getMediaInfo()
    {
        return n_getMediaInfo();
    }

    /**
     * 打开解码器
     * @return
     */
    public int openCodec()
    {
        return n_openCodec();
    }

    /**
     * 获取视频图片
     * @param time 获取指定时间的视频图片
     * @param keyFrame 是否只获取关键帧（true只获取关键帧 速度快）
     * @return
     */
    public Bitmap getVideoImg(double time, boolean keyFrame)
    {
        return n_getvideoimg(time, keyFrame);
    }

    /**
     * 回收资源
     */
    public void release()
    {
        n_release();
    }

    //native
    private native int n_demuxer(String source);
    private native WlMediaInfoBean[] n_getMediaInfo();
    private native int n_openCodec();
    private native Bitmap n_getvideoimg(double time, boolean keyframe);
    private native int n_release();
}
