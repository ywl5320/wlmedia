package com.ywl5320.wlmedia;

import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__AUTO_PLAY_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__COMPLETE_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__FIRST_FRAME_RENDERED_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__LOADING_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__OUT_RENDER_TEXTURE_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__PREPARED_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__SEEK_FINISH_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__TAKE_PICTURE_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_PLAYER_HANDEL_MSG__TIME_INFO_CB;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;

import com.ywl5320.wlmedia.bean.WlMediaInfoBean;
import com.ywl5320.wlmedia.bean.WlOutRenderTextureBean;
import com.ywl5320.wlmedia.bean.WlTrackInfoBean;
import com.ywl5320.wlmedia.enums.WlAlphaVideoType;
import com.ywl5320.wlmedia.enums.WlAudioChannelType;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlMirrorType;
import com.ywl5320.wlmedia.enums.WlPitchType;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlRotateType;
import com.ywl5320.wlmedia.enums.WlSampleRate;
import com.ywl5320.wlmedia.enums.WlScaleType;
import com.ywl5320.wlmedia.enums.WlSeekType;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.enums.WlTrackType;
import com.ywl5320.wlmedia.listener.WlOnBufferDataListener;
import com.ywl5320.wlmedia.listener.WlOnLoadLibraryListener;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.listener.WlOnOutPcmDataListener;

import java.lang.ref.WeakReference;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/1/8
 */
public class WlPlayer {

    /**
     * 消息处理loop
     */
    private final WlPlayerHandler playerHandler;
    private WlMediaInfoBean mediaInfoBean;
    private WlOnMediaInfoListener onMediaInfoListener;
    private WlOnOutPcmDataListener onOutPcmDataListener;
    private WlOnBufferDataListener onBufferDataListener;

    /**
     * 无参构造函数
     */
    public WlPlayer() {
        this(null);
    }

    /**
     * 有参构造函数，可以自定义加载动态库
     *
     * @param onLoadLibraryListener
     */
    public WlPlayer(WlOnLoadLibraryListener onLoadLibraryListener) {
        if (onLoadLibraryListener == null || !onLoadLibraryListener.onLoadedLibrary()) {
            loadLibrary();
        }
        playerHandler = new WlPlayerHandler(this);
        n_wlPlayer_init();
    }

    /**
     * 设置回调
     *
     * @param onMediaInfoListener
     */
    public void setOnMediaInfoListener(WlOnMediaInfoListener onMediaInfoListener) {
        this.onMediaInfoListener = onMediaInfoListener;
    }

    /**
     * 设置pcm数据回调
     *
     * @param onOutPcmDataListener
     */
    public void setOnOutPcmDataListener(WlOnOutPcmDataListener onOutPcmDataListener) {
        this.onOutPcmDataListener = onOutPcmDataListener;
    }

    /**
     * byte[] 类型数据读取回调
     *
     * @param onBufferDataListener
     */
    public void setOnBufferDataListener(WlOnBufferDataListener onBufferDataListener) {
        this.onBufferDataListener = onBufferDataListener;
    }

    /**
     * 设置 surface
     *
     * @param surface   surface
     * @param rgba      清屏颜色 eg: #000000FF
     * @param uniqueNum 每个view的唯一值
     */
    public void setSurface(Surface surface, String rgba, long uniqueNum) {
        n_wlPlayer_updateSurface(surface, rgba, uniqueNum);
    }

    /**
     * 设置数据源
     *
     * @param source
     */
    public void setSource(String source) {
        n_wlPlayer_setSource(source);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public String getSource() {
        return n_wlPlayer_getSource();
    }

    /**
     * 设置数据源类型
     *
     * @param sourceType
     */
    public void setSourceType(WlSourceType sourceType) {
        n_wlPlayer_setSourceType(sourceType.getValue());
    }

    /**
     * 获取数据源类型
     *
     * @return
     */
    public WlSourceType getSourceType() {
        int type = n_wlPlayer_getSourceType();
        return WlSourceType.find(type);
    }

    /**
     * 获取所有的音频轨道信息
     *
     * @return
     */
    public WlTrackInfoBean[] getAudioTracks() {
        if (mediaInfoBean == null) {
            return null;
        }
        return mediaInfoBean.getAudioTracks();
    }

    /**
     * 获取所有的视频轨道信息
     *
     * @return
     */
    public WlTrackInfoBean[] getVideoTracks() {
        if (mediaInfoBean == null) {
            return null;
        }
        return mediaInfoBean.getVideoTracks();
    }

    /**
     * 获取所有字幕轨道信息
     *
     * @return
     */
    public WlTrackInfoBean[] getSubtitleTracks() {
        if (mediaInfoBean == null) {
            return null;
        }
        return mediaInfoBean.getSubtitleTracks();
    }

    /**
     * 设置音频轨道
     *
     * @param audioTrackIndex
     */
    public void setAudioTrackIndex(int audioTrackIndex) {
        setMediaTrackIndex(audioTrackIndex, -1, -1);
    }

    /**
     * 获取当前音频轨道
     *
     * @return
     */
    public int getAudioTrackIndex() {
        return getMediaTrackIndex(WlTrackType.WL_TRACK_AUDIO);
    }

    /**
     * 获取当前视频轨道
     *
     * @return
     */
    public int getVideoTrackIndex() {
        return getMediaTrackIndex(WlTrackType.WL_TRACK_VIDEO);
    }

    /**
     * 获取当前字幕轨道
     *
     * @return
     */
    public int getSubtitleTrackIndex() {
        return getMediaTrackIndex(WlTrackType.WL_TRACK_SUBTITLE);
    }

    /**
     * 设置视频轨道
     *
     * @param videoTrackIndex
     */
    public void setVideoTrackIndex(int videoTrackIndex) {
        setMediaTrackIndex(-1, videoTrackIndex, -1);
    }

    /**
     * 设置字幕轨道
     *
     * @param subtitleTrackIndex
     */
    public void setSubtitleTrackIndex(int subtitleTrackIndex) {
        setMediaTrackIndex(-1, -1, subtitleTrackIndex);
    }

    /**
     * 设置 media track
     *
     * @param audioTrackIndex
     * @param videoTrackIndex
     * @param subtitleTrackIndex
     */
    public void setMediaTrackIndex(int audioTrackIndex, int videoTrackIndex, int subtitleTrackIndex) {
        n_wlPlayer_setMediaTrackIndex(audioTrackIndex, videoTrackIndex, subtitleTrackIndex);
    }

    /**
     * 获取当前播体轨道索引
     *
     * @param trackType
     * @return
     */
    public int getMediaTrackIndex(WlTrackType trackType) {
        return n_wlPlayer_getMediaTrackIndex(trackType.getValue());
    }

    /**
     * 设置音频重采样采样率
     *
     * @param sampleRate
     */
    public void setSampleRate(WlSampleRate sampleRate) {
        n_wlPlayer_setAudioSampleRate(sampleRate.getValue());
    }

    /**
     * 获取音频采样率
     *
     * @return
     */
    public WlSampleRate getSampleRate() {
        int sampleRate = n_wlPlayer_getAudioSampleRate();
        return WlSampleRate.find(sampleRate);
    }

    /**
     * seek 到指定时间，默认seek模式
     *
     * @param seekTime
     */
    public void seek(double seekTime) {
        seek(seekTime, WlSeekType.WL_SEEK_NORMAL);
    }

    /**
     * seek 到指定时间
     *
     * @param seekTime seek 时间
     * @param seekType seek 模式 WlSeekType
     */
    public void seek(double seekTime, WlSeekType seekType) {
        n_wlPlayer_seek(seekTime, seekType.getValue());
    }

    /**
     * seek开始，调用后会屏蔽时间回调，避免seek中进度条异常跳转
     */
    public void seekStart() {
        seekStart(true);
    }

    /**
     * seek开始，调用后会屏蔽时间回调，避免seek中进度条异常跳转
     *
     * @param seekStart true: 屏蔽回调
     */
    public void seekStart(boolean seekStart) {
        n_wlPlayer_setSeekStart(seekStart);
        removeMessages(WL_PLAYER_HANDEL_MSG__TIME_INFO_CB);
    }

    /**
     * 是否 seek 开始了
     *
     * @return
     */
    public boolean isSeekStart() {
        return n_wlPlayer_isSeekStart();
    }

    /**
     * 获取时长
     *
     * @return
     */
    public double getDuration() {
        return n_wlPlayer_getDuration();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public double getCurrentTime() {
        return n_wlPlayer_getCurrentTime();
    }

    /**
     * 获取缓存时间
     *
     * @return
     */
    public double getBufferTime() {
        return n_wlPlayer_getBufferTime();
    }

    /**
     * 设置解码类型
     *
     * @param codecType 自动、软解
     */
    public void setCodecType(WlCodecType codecType) {
        n_wlPlayer_setCodecType(codecType.getValue());
    }

    /**
     * 获取解码类型
     *
     * @return
     */
    public WlCodecType getCodecType() {
        int type = n_wlPlayer_getCodecType();
        return WlCodecType.find(type);
    }

    /**
     * 设置时间回调间隔，默认 1s/次
     *
     * @param seconds [0.01 ~ 5]
     */
    public void setTimeInfoInterval(double seconds) {
        n_wlPlayer_setTimeInterval(seconds);
    }

    /**
     * 获取时间回调间隔
     *
     * @return
     */
    public double getTimeInfoInterval() {
        return n_wlPlayer_getTimeInterval();
    }

    /**
     * 设置播放模式
     *
     * @param playModel 音频视频同时播放，只播放音频，只播放视频
     */
    public void setPlayModel(WlPlayModel playModel) {
        n_wlPlayer_setPlayModel(playModel.getValue());
    }

    /**
     * 获取视频播放模式
     *
     * @return
     */
    public WlPlayModel getPlayModel() {
        int type = n_wlPlayer_getPlayModel();
        return WlPlayModel.find(type);
    }

    /**
     * 设置 OpenGL 渲染最大像素，大于这个像素，就不会使用OpenGL
     *
     * @param width  > 0
     * @param height > 0
     */
    public void setRenderDefaultSize(int width, int height) {
        n_wlPlayer_setRenderDefaultSize(width, height);
    }

    /**
     * 获取 OpenGL 渲染最大宽度
     *
     * @return
     */
    public int getRenderDefaultWidth() {
        return n_wlPlayer_getRenderDefaultWidth();
    }

    /**
     * 获取 OpenGL 渲染最大高度
     *
     * @return
     */
    public int getRenderDefaultHeight() {
        return n_wlPlayer_getRenderDefaultHeight();
    }

    /**
     * 设置视频缩放宽高比例
     *
     * @param uniqueNum
     * @param scaleType
     */
    public void setVideoScale(long uniqueNum, WlScaleType scaleType) {
        setVideoScale(uniqueNum, scaleType.getScaleWidth(), scaleType.getScaleHeight());
    }

    /**
     * 设置视频缩放宽高比例
     *
     * @param uniqueNum
     * @param scaleWidth  > 0
     * @param scaleHeight > 0
     */
    public void setVideoScale(long uniqueNum, int scaleWidth, int scaleHeight) {
        n_wlPlayer_setVideoScale(scaleWidth, scaleHeight, uniqueNum);
    }

    /**
     * 获取视频缩放比例 宽
     *
     * @return
     */
    public int getVideoScaleWidth(long uniqueNum) {
        return n_wlPlayer_getVideoScaleWidth(uniqueNum);
    }

    /**
     * 获取视频缩放比例 高
     *
     * @return
     */
    public int getVideoScaleHeight(long uniqueNum) {
        return n_wlPlayer_getVideoScaleHeight(uniqueNum);
    }

    /**
     * 设置旋转角度
     *
     * @param rotateType
     */
    public void setVideoRotate(long uniqueNum, WlRotateType rotateType) {
        n_wlPlayer_setVideoRotate(rotateType.getValue(), uniqueNum);
    }

    /**
     * 获取旋转角度
     *
     * @return
     */
    public int getVideoRotate(long uniqueNum) {
        return n_wlPlayer_getVideoRotate(uniqueNum);
    }

    /**
     * 设置镜像
     *
     * @param mirrorType
     */
    public void setVideoMirror(long uniqueNum, WlMirrorType mirrorType) {
        n_wlPlayer_setVideoMirror(mirrorType.getValue(), uniqueNum);
    }

    /**
     * 获取镜像
     *
     * @return
     */
    public int getVideoMirror(long uniqueNum) {
        return n_wlPlayer_getVideoMirror(uniqueNum);
    }

    /**
     * 设置解码 timeBase
     *
     * @param timeBase [250000 ~ 1000000]
     */
    public void setCodecTimeBase(long timeBase) {
        n_wlPlayer_setCodecTimeBase(timeBase);
    }

    /**
     * 获取解码 timeBase
     *
     * @return
     */
    public long getCodecTimeBase() {
        return n_wlPlayer_getCodecTimeBase();
    }

    /**
     * 设置 option
     *
     * @param key
     * @param value
     */
    public void setOptions(String key, String value) {
        n_wlPlayer_setOptions(key, value);
    }

    /**
     * 清除所有 option
     */
    public void clearOptions() {
        n_wlPlayer_clearOptions();
    }

    /**
     * 设置播放速度
     *
     * @param speed [0.25 ~ 4]
     */
    public void setSpeed(double speed) {
        n_wlPlayer_setSpeed(speed);
    }

    /**
     * 获取播放速度
     *
     * @return
     */
    public double getSpeed() {
        return n_wlPlayer_getSpeed();
    }

    /**
     * 设置音调
     *
     * @param pitch [0.25 ~ 4]
     */
    public void setPitch(double pitch) {
        setPitch(pitch, WlPitchType.WL_PITCH_NORMAL);
    }

    /**
     * 设置音调
     *
     * @param pitch
     * @param pitchType
     */
    public void setPitch(double pitch, WlPitchType pitchType) {
        n_wlPlayer_setPitch(pitch, pitchType.getValue());
    }

    /**
     * 获取音调
     *
     * @return
     */
    public double getPitch() {
        return n_wlPlayer_getPitch();
    }

    /**
     * 获取音调类型
     *
     * @return
     */
    public WlPitchType getPitchType() {
        int pitchType = n_wlPlayer_getPitchType();
        return WlPitchType.find(pitchType);
    }

    /**
     * 设置视频播放完（或停止）时是否清屏
     *
     * @param uniqueNum
     * @param clear
     */
    public void setClearLastVideoFrame(long uniqueNum, boolean clear) {
        n_wlPlayer_setClearLastVideoFrame(clear, uniqueNum);
    }

    /**
     * 是否视频播放完成（或停止）时清屏
     *
     * @param uniqueNum
     * @return
     */
    public boolean isClearLastVideoFrame(long uniqueNum) {
        return n_wlPlayer_isClearLastVideoFrame(uniqueNum);
    }

    /**
     * 设置透明视频类型
     *
     * @param alphaVideoType
     */
    public void setAlphaVideoType(WlAlphaVideoType alphaVideoType) {
        n_wlPlayer_setAlphaVideoType(alphaVideoType.getValue());
    }

    /**
     * 获取透明视频类型
     *
     * @return
     */
    public WlAlphaVideoType getAlphaVideoType() {
        int type = n_wlPlayer_getAlphaVideoType();
        return WlAlphaVideoType.find(type);
    }

    /**
     * 设置缓冲大小 单位 秒（s) maxBufferWaitToPlay >= minBufferToPlay >= 0
     *
     * @param minBufferToPlay     最小播放缓存，达到此缓存时才能播放
     * @param maxBufferWaitToPlay 最大播放缓存，达到此缓存后就会等待
     */
    public void setBufferSize(double minBufferToPlay, double maxBufferWaitToPlay) {
        n_wlPlayer_setBufferSize(minBufferToPlay, maxBufferWaitToPlay);
    }

    /**
     * 获取设置最小缓存大小
     *
     * @return
     */
    public double getMinBufferToPlay() {
        return n_wlPlayer_getMinBufferToPlay();
    }

    /**
     * 获取设最大缓存大小
     *
     * @return
     */
    public double getMaxBufferWaitToPlay() {
        return n_wlPlayer_getMaxBufferWaitToPlay();
    }

    /**
     * 设置 音视频同步 偏移
     *
     * @param syncOffset > 0, 视频快于音频；< 0, 视频慢于音频 [-1, 1]
     */
    public void setSyncOffset(double syncOffset) {
        n_wlPlayer_setSyncOffset(syncOffset);
    }

    /**
     * 获取 音视频同步 偏移
     *
     * @return
     */
    public double getSyncOffset() {
        return n_wlPlayer_getSyncOffset();
    }

    /**
     * 设置视频渲染帧率
     *
     * @param frameRate (20 ~ 60)
     */
    public void setRenderFrameRate(int frameRate) {
        n_wlPlayer_setRenderFrameRate(frameRate);
    }

    /**
     * 获取视频渲染帧率
     *
     * @return
     */
    public int getRenderFrameRate() {
        return n_wlPlayer_getRenderFrameRate();
    }

    /**
     * 设置循环播放
     *
     * @param loop
     */
    public void setLoopPlay(boolean loop) {
        n_wlPlayer_setLoopPlay(loop);
    }

    /**
     * 是否循环播放
     *
     * @return
     */
    public boolean isLoopPlay() {
        return n_wlPlayer_isLoopPlay();
    }

    /**
     * 设置超时时间
     *
     * @param timeOut 单位：秒（s)
     */
    public void setTimeOut(double timeOut) {
        n_wlPlayer_setTimeOut(timeOut);
    }

    /**
     * 获取超时时间
     *
     * @return
     */
    public double getTimeOut() {
        return n_wlPlayer_getTimeOut();
    }

    /**
     * 设置是否开启buffer帧解码模式
     *
     * @param enable
     */
    public void setBufferDeEncrypt(boolean enable) {
        n_wlPlayer_setBufferDeEncrypt(enable);
    }

    /**
     * 获取是否开启buffer解码模式
     *
     * @return
     */
    public boolean isBufferDeEncrypt() {
        return n_wlPlayer_isBufferDeEncrypt();
    }

    /**
     * 获取 audioSessionId
     *
     * @return
     */
    public int getAudioSessionId() {
        return n_wlPlayer_getAudioSessionId();
    }

    /**
     * 如果触发丢帧，表示连续丢帧次数
     *
     * @param count 0：不丢帧
     */
    public void setDropFrameCount(int count) {
        n_wlPlayer_setDropFrameCount(count);
    }

    /**
     * 获取连续丢帧次数
     *
     * @return
     */
    public int getDropFrameCount() {
        return n_wlPlayer_getDropFrameCount();
    }

    /**
     * 设置自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        n_wlPlayer_setAutoPlay(autoPlay);
    }

    /**
     * 获取是否自动播放
     *
     * @return
     */
    public boolean isAutoPlay() {
        return n_wlPlayer_isAutoPlay();
    }

    /**
     * 截图
     */
    public void takePicture() {
        n_wlPlayer_takePicture();
    }

    /**
     * 初始化外部渲染环境
     *
     * @param name
     * @param type
     * @param version
     */
    public void initOutRenderEnv(String name, int type, int version) {
        n_wlPlayer_initOutRenderEnv(name, type, version);
    }

    /**
     * 设置音量
     *
     * @param percent [0, 200]
     */
    public void setVolume(int percent) {
        setVolume(percent, false);
    }

    /**
     * 设置音量
     *
     * @param percent
     * @param changePcm 是否改变音频值改变音量
     */
    public void setVolume(int percent, boolean changePcm) {
        n_wlPlayer_setVolume(percent, changePcm);
    }

    /**
     * 获取音量值
     *
     * @return
     */
    public int getVolume() {
        return n_wlPlayer_getVolume();
    }

    /**
     * 设置声道类型
     *
     * @param audioChannelType 立体声 单左声道 单右声道 双左声道 双右声道
     */
    public void setAudioChannelType(WlAudioChannelType audioChannelType) {
        n_wlPlayer_setAudioChannelType(audioChannelType.getValue());
    }

    /**
     * 获取声道类型
     *
     * @return
     */
    public WlAudioChannelType getAudioChannelType() {
        int type = n_wlPlayer_getAudioChannelType();
        return WlAudioChannelType.find(type);
    }

    /**
     * 设置实时回调pcm数据
     * 注：数据返回是在子线程
     *
     * @param enable true: 开启 false: 关闭
     */
    public void setPcmCallbackEnable(boolean enable) {
        n_wlPlayer_setPcmCallbackEnabled(enable);
    }

    /**
     * 是否实时回调pcm数据
     *
     * @return
     */
    public boolean isPcmCallbackEnable() {
        return n_wlPlayer_isPcmCallbackEnable();
    }

    /**
     * 获取解码类型
     *
     * @return -1 未设置
     *          1 软解
     *          2 硬解
     */
    public int getVideoCodecRealType() {
        return n_wlPlayer_getVideoCodecRealType();
    }

    /**
     * 异步准备
     */
    public void prepare() {
        n_wlPlayer_prepare();
    }

    /**
     * 开始播放
     */
    public void start() {
        n_wlPlayer_start();
    }

    /**
     * 是否播放中
     *
     * @return
     */
    public boolean isPlaying() {
        return n_wlPlayer_isPlaying();
    }

    /**
     * 暂停
     */
    public void pause() {
        n_wlPlayer_pause();
    }

    /**
     * 是否暂停
     *
     * @return
     */
    public boolean isPause() {
        return n_wlPlayer_isPause();
    }

    /**
     * 继续播放
     */
    public void resume() {
        n_wlPlayer_resume();
    }

    /**
     * 停止
     */
    public void stop() {
        n_wlPlayer_stop();
    }

    /**
     * 销毁
     */
    public void release() {
        n_wlPlayer_release();
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static synchronized String getVersion() {
        if (!isLibraryLoaded) {
            loadLibrary();
        }
        return n_wlPlayer_getVersion();
    }

    /**
     * -------------------- native callback -------------------------
     */
    private void nCallPrepared(WlMediaInfoBean mediaInfoBean) {
        sendMessage(WL_PLAYER_HANDEL_MSG__PREPARED_CB, mediaInfoBean);
    }

    private void nCallTimeInfo(long currentTime, long bufferTime) {
        sendMessage(WL_PLAYER_HANDEL_MSG__TIME_INFO_CB, (int) currentTime, (int) bufferTime);
    }

    private void nCallComplete(int type, String msg) {
        sendMessage(WL_PLAYER_HANDEL_MSG__COMPLETE_CB, type, msg);
    }

    private void nCallLoadingStatus(int type, int progress, long speed) {
        sendMessage(WL_PLAYER_HANDEL_MSG__LOADING_CB, type, progress, speed);
    }

    private void nCallIOBufferInit() {
        long length = -1;
        if (onBufferDataListener != null) {
            length = onBufferDataListener.onBufferByteLength();
        }
        n_wlPlayer_sendBufferByteLength(length);
    }

    private void nCallIOBufferRead(long position, int bufferSize) {
        byte[] buffer = null;
        if (onBufferDataListener != null) {
            buffer = onBufferDataListener.onBufferByteData(position, bufferSize);
        }
        n_wlPlayer_sendBufferByteRead(buffer);
    }

    private void nCallSeekFinish() {
        sendMessage(WL_PLAYER_HANDEL_MSG__SEEK_FINISH_CB);
    }

    private void nCallFirstFrameRendered() {
        sendMessage(WL_PLAYER_HANDEL_MSG__FIRST_FRAME_RENDERED_CB);
    }

    private void nCallBufferDeEncrypt(int mediaType, byte[] data) {
        byte[] deEncryptBuffer = null;
        if (onMediaInfoListener != null) {
            deEncryptBuffer = onMediaInfoListener.onDeEncryptData(WlTrackType.find(mediaType), data);
        }
        n_wlPlayer_sendBufferDeEncrypt(mediaType, deEncryptBuffer);
    }

    private void nCallOutRenderTexture(int textureId, int videoWidth, int videoHeight, int videoRotate) {
        WlOutRenderTextureBean outRenderTextureBean = new WlOutRenderTextureBean();
        outRenderTextureBean.setTextureId(textureId);
        outRenderTextureBean.setVideoWidth(videoWidth);
        outRenderTextureBean.setVideoHeight(videoHeight);
        outRenderTextureBean.setVideoRotate(videoRotate);
        sendMessage(WL_PLAYER_HANDEL_MSG__OUT_RENDER_TEXTURE_CB, outRenderTextureBean);
    }

    private void nCallAutoPlay(WlMediaInfoBean mediaInfoBean) {
        sendMessage(WL_PLAYER_HANDEL_MSG__AUTO_PLAY_CB, mediaInfoBean);
    }

    private void nCallTakePicture(Bitmap bitmap) {
        sendMessage(WL_PLAYER_HANDEL_MSG__TAKE_PICTURE_CB, bitmap);
    }

    private void nCallOutPcmInitInfo(int bit, int channel, int sampleRate) {
        if (onOutPcmDataListener != null) {
            onOutPcmDataListener.onOutPcmInfo(bit, channel, sampleRate);
        }
    }

    private void nCallOutPcmBufferInfo(int size, byte[] buffers, double db) {
        if (onOutPcmDataListener != null) {
            onOutPcmDataListener.onOutPcmBuffer(size, buffers, db);
        }
    }

    // native
    private native int n_wlPlayer_init();

    private native int n_wlPlayer_updateSurface(Surface surface, String rgba, long uniqueNum);

    private native int n_wlPlayer_setSource(String source);

    private native String n_wlPlayer_getSource();

    private native int n_wlPlayer_setSourceType(int type);

    private native int n_wlPlayer_getSourceType();

    private native int n_wlPlayer_setMediaTrackIndex(int audioTrackIndex, int videoTrackIndex, int subtitleTrackIndex);

    private native int n_wlPlayer_getMediaTrackIndex(int mediaType);

    private native int n_wlPlayer_setAudioSampleRate(int sampleRate);

    private native int n_wlPlayer_getAudioSampleRate();

    private native int n_wlPlayer_seek(double time, int type);

    private native int n_wlPlayer_setSeekStart(boolean seekStart);

    private native boolean n_wlPlayer_isSeekStart();

    private native int n_wlPlayer_setTimeInterval(double timeInterval);

    private native double n_wlPlayer_getTimeInterval();

    private native double n_wlPlayer_getDuration();

    private native double n_wlPlayer_getCurrentTime();

    private native double n_wlPlayer_getBufferTime();

    private native int n_wlPlayer_setCodecType(int type);

    private native int n_wlPlayer_getCodecType();

    private native int n_wlPlayer_setPlayModel(int type);

    private native int n_wlPlayer_getPlayModel();

    private native int n_wlPlayer_setRenderDefaultSize(int width, int height);

    private native int n_wlPlayer_getRenderDefaultWidth();

    private native int n_wlPlayer_getRenderDefaultHeight();

    private native int n_wlPlayer_setVideoScale(int width, int height, long uniqueNum);

    private native int n_wlPlayer_getVideoScaleWidth(long uniqueNum);

    private native int n_wlPlayer_getVideoScaleHeight(long uniqueNum);

    private native int n_wlPlayer_setVideoRotate(int rotate, long uniqueNum);

    private native int n_wlPlayer_getVideoRotate(long uniqueNum);

    private native int n_wlPlayer_setVideoMirror(int mirror, long uniqueNum);

    private native int n_wlPlayer_getVideoMirror(long uniqueNum);

    private native int n_wlPlayer_setCodecTimeBase(long timeBase);

    private native long n_wlPlayer_getCodecTimeBase();

    private native int n_wlPlayer_setOptions(String key, String value);

    private native int n_wlPlayer_clearOptions();

    private native int n_wlPlayer_setSpeed(double speed);

    private native double n_wlPlayer_getSpeed();

    private native int n_wlPlayer_setPitch(double pitch, int type);

    private native double n_wlPlayer_getPitch();

    private native int n_wlPlayer_getPitchType();

    private native int n_wlPlayer_setClearLastVideoFrame(boolean clear, long uniqueNum);

    private native boolean n_wlPlayer_isClearLastVideoFrame(long uniqueNum);

    private native int n_wlPlayer_setAlphaVideoType(int type);

    private native int n_wlPlayer_getAlphaVideoType();

    public native int n_wlPlayer_setBufferSize(double minBufferToPlay, double maxBufferWaitToPlay);

    private native double n_wlPlayer_getMinBufferToPlay();

    private native double n_wlPlayer_getMaxBufferWaitToPlay();

    private native int n_wlPlayer_setSyncOffset(double syncOffset);

    private native double n_wlPlayer_getSyncOffset();

    private native int n_wlPlayer_sendBufferByteLength(long length);

    private native int n_wlPlayer_sendBufferByteRead(byte[] buffer);

    private native int n_wlPlayer_setRenderFrameRate(int frameRate);

    private native int n_wlPlayer_getRenderFrameRate();

    private native int n_wlPlayer_setLoopPlay(boolean loop);

    private native boolean n_wlPlayer_isLoopPlay();

    private native int n_wlPlayer_setTimeOut(double timeOut);

    private native double n_wlPlayer_getTimeOut();

    private native int n_wlPlayer_sendBufferDeEncrypt(int type, byte[] buffer);

    private native int n_wlPlayer_setBufferDeEncrypt(boolean enable);

    private native int n_wlPlayer_getAudioSessionId();

    private native boolean n_wlPlayer_isBufferDeEncrypt();

    private native int n_wlPlayer_setDropFrameCount(int count);

    private native int n_wlPlayer_getDropFrameCount();

    private native int n_wlPlayer_setAutoPlay(boolean autoPlay);

    private native boolean n_wlPlayer_isAutoPlay();

    private native int n_wlPlayer_takePicture();

    private native int n_wlPlayer_initOutRenderEnv(String name, int renderType, int version);

    private native int n_wlPlayer_setVolume(int percent, boolean changePcm);

    private native int n_wlPlayer_getVolume();

    private native int n_wlPlayer_setAudioChannelType(int type);

    private native int n_wlPlayer_getAudioChannelType();

    private native int n_wlPlayer_setPcmCallbackEnabled(boolean enabled);

    private native boolean n_wlPlayer_isPcmCallbackEnable();

    private native int n_wlPlayer_getVideoCodecRealType();

    private native int n_wlPlayer_prepare();

    private native int n_wlPlayer_start();

    private native boolean n_wlPlayer_isPlaying();

    private native int n_wlPlayer_pause();

    private native boolean n_wlPlayer_isPause();

    private native int n_wlPlayer_resume();

    private native int n_wlPlayer_stop();

    private native int n_wlPlayer_release();

    private native static String n_wlPlayer_getVersion();

    /**
     * ---------------------- handle messages --------------------------
     */
    private void sendMessage(int what) {
        playerHandler.sendEmptyMessage(what);
    }

    private void sendMessage(int what, Object obj) {
        sendMessage(what, 0, 0, obj);
    }

    private void sendMessage(int what, int arg1) {
        sendMessage(what, arg1, 0, null);
    }

    private void sendMessage(int what, int arg1, Object obj) {
        sendMessage(what, arg1, 0, obj);
    }

    private void sendMessage(int what, int arg1, int arg2) {
        sendMessage(what, arg1, arg2, null);
    }

    private void sendMessage(int what, int arg1, int arg2, Object obj) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.obj = obj;
        playerHandler.sendMessage(message);
    }

    private void removeMessages(int what) {
        playerHandler.removeMessages(what);
    }

    private static class WlPlayerHandler extends Handler {
        private WeakReference<WlPlayer> reference;

        public WlPlayerHandler(WlPlayer player) {
            super(Looper.getMainLooper());
            reference = new WeakReference(player);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WlPlayer player = reference.get();
            if (player == null) {
                return;
            }
            switch (msg.what) {
                case WL_PLAYER_HANDEL_MSG__PREPARED_CB:
                    player.mediaInfoBean = (WlMediaInfoBean) msg.obj;
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onPrepared();
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__TIME_INFO_CB:
                    int currentTime = msg.arg1;
                    int bufferTime = msg.arg2;
                    if (player.onMediaInfoListener != null && !player.isSeekStart()) {
                        player.onMediaInfoListener.onTimeInfo(currentTime / 1000.0, bufferTime / 1000.0);
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__COMPLETE_CB:
                    int type = msg.arg1;
                    String completeMsg = (String) msg.obj;
                    WlCompleteType wlCompleteType = WlCompleteType.find(type);
                    if (wlCompleteType != WlCompleteType.WL_COMPLETE_EOF) {
                        player.mediaInfoBean = null;
                    }
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onComplete(wlCompleteType, completeMsg);
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__LOADING_CB:
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onLoad(WlLoadStatus.find(msg.arg1), msg.arg2, (long) msg.obj);
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__SEEK_FINISH_CB:
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onSeekFinish();
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__FIRST_FRAME_RENDERED_CB:
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onFirstFrameRendered();
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__OUT_RENDER_TEXTURE_CB:
                    if (player.onMediaInfoListener != null) {
                        WlOutRenderTextureBean outRenderTextureBean = (WlOutRenderTextureBean) msg.obj;
                        if (outRenderTextureBean != null) {
                            player.onMediaInfoListener.onOutRenderTexture(outRenderTextureBean.getTextureId(), outRenderTextureBean.getVideoWidth(), outRenderTextureBean.getVideoHeight(), outRenderTextureBean.getVideoRotate());
                        }
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__AUTO_PLAY_CB:
                    player.mediaInfoBean = (WlMediaInfoBean) msg.obj;
                    if (player.onMediaInfoListener != null) {
                        player.onMediaInfoListener.onAutoPlay();
                    }
                    break;
                case WL_PLAYER_HANDEL_MSG__TAKE_PICTURE_CB:
                    if (player.onMediaInfoListener != null) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        player.onMediaInfoListener.onTakePicture(bitmap);
                    }
                    break;
            }
        }
    }

    /**
     * 加载动态库
     */
    private static boolean isLibraryLoaded = false;

    private static synchronized void loadLibrary() {
        try {
            System.loadLibrary("wlplayer");
            isLibraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            isLibraryLoaded = false;
        }
    }

}
