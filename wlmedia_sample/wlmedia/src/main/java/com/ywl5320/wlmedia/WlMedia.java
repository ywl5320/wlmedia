package com.ywl5320.wlmedia;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;


import com.ywl5320.wlmedia.bean.WlMediaInfoBean;
import com.ywl5320.wlmedia.constant.WlHandleMessage;
import com.ywl5320.wlmedia.enums.WlAlphaVideoType;
import com.ywl5320.wlmedia.enums.WlAudioChannel;
import com.ywl5320.wlmedia.enums.WlBufferType;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlSampleRate;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.enums.WlVideoRotate;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.listener.WlOnPcmDataListener;
import com.ywl5320.wlmedia.listener.WlOnTakePictureListener;
import com.ywl5320.wlmedia.log.WlLog;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class WlMedia {

    static {
        System.loadLibrary("wlmedia-2.0.0");
    }

    /** 1、  fields ================================================= */
    /**
     * hashcode 内部使用
     */
    private long hashCode = -1;
    private long hashCode2 = -1;
    /**
     * 消息分发
     */
    private WlMediaHandler handler;
    /**
     * 数据源
     */
    private String source; //数据源
    /**
     * 播放器是否play
     */
    private boolean playing = false;
    /**
     * 下一曲次数
     */
    private int playNextCount = 0;
    /**
     * seek 时间
     */
    private double seekTime = 0;
    /**
     * 总时长
     */
    private double duration = -1;
    /**
     * 当前播放时间
     */
    private double playTime = 0;
    /**
     * 当前缓冲时间
     */
    private double bufferTime = 0;
    /**
     * 是否处于seek中
     */
    private boolean isSeeking = false;
    /**
     * 音频播放声道
     */
    private int audioChannel = WlAudioChannel.CHANNEL_CENTER.getValue();
    /**
     * 音频音频（可大于100）
     */
    private double volume = 100;
    /**
     * 播放速度
     */
    private double speed = 1.0f;
    /**
     * 音频音调
     */
    private double pitch = 1.0f;
    /**
     * 制定音频采样率
     */
    private int sampleRate = WlSampleRate.SAMPLE_RATE_NONE.getValue();
    /**
     * 音轨所有轨道
     */
    private WlMediaInfoBean[] audioTracks = null;
    /**
     * 视频所以轨道
     */
    private WlMediaInfoBean[] videoTracks = null;
    /**
     * 字幕所以轨道
     */
    private WlMediaInfoBean[] subtitleTracks = null;
    /**
     * 当前选中的音频轨道
     */
    private int audioTrackIndex = 0;
    /**
     * 当前选中的视频轨道
     */
    private int videoTrackIndex = 0;
    /**
     * 当前选中的字幕轨道
     */
    private int subtitleTrackIndex = 0;
    /**
     * 播放模式
     */
    private int playModel = WlPlayModel.PLAYMODEL_AUDIO_VIDEO.getValue();
    /**
     * 视频surface
     */
    private Surface surface;
    /**
     * surface宽
     */
    private int surfaceWidth;
    /**
     * surface高
     */
    private int surfaceHeight;
    /**
     * 标记是否释放surface资源 用于退出播放页面
     */
    private boolean needReleaseSurface = false;
    /**
     * 播放的数据源
     */
    private int sourceType = WlSourceType.NORMAL.getValue();
    /**
     * 平滑时间回调（增加回调频率）
     */
    private boolean smoothTime = false;
    /**
     * 解码器模式
     * 默认硬解码
     */
    private int codecType = WlCodecType.CODEC_MEDIACODEC.getValue();
    /**
     * 当前版本是否大于等于21
     */
    private boolean isVersion21 = false;
    /**
     * 预留opengl着色器shader
     */
    private String fShader;
    /**
     * 视频渲染背景清屏颜色（rgba）
     */
    private float rgba_r = 0f, rgba_g = 0f, rgba_b = 0f, rgba_a = 1f;
    /**
     * 播放完后是否清屏（默认黑色）
     */
    private boolean clearLastPicture = false;
    /**
     * 缩放宽高比和旋转角度
     */
    private int scale_w = 0;
    private int scale_h = 0;
    private int rotate = -1;
    /**
     * 是否使用soundtouch 启用将会增加cpu使用率 默认否
     */
    private boolean useSoundTouch = false;
    /**
     * 是否暂停
     */
    private boolean isPause = false;
    /**
     * 音频相对视频同步时间偏移（？>0音频比视频快  ？<音频比视频慢）
     */
    private double audioOffsetTime = 0;
    /**
     * 设置FFmpeg参数
     */
    private HashMap<String, String> ffOptions;
    /**
     * 音频基础信息（用于手动设置，提高起播速度）
     */
    private WlMediaInfoBean[] mediaInfos;
    /**
     * 是否循环播放
     */
    private boolean loopPlay = false;
    /**
     * 超时时间（单位秒）
     */
    private int timeOut = 15;
    /**
     * 视频宽
     */
    private int video_width = 0;
    /**
     * 视频高
     */
    private int video_height = 0;
    /**
     * 视频播放类型（可设置透明视频播放）
     */
    private int alphaVideoType = WlAlphaVideoType.WL_ALPHA_VIDEO_NO.getValue();
    /**
     * buffer播放时 数据块内存最大值 单位 byte
     */
    private long ioBufferSize = 1024 * 256;
    /**
     * buffer 大小限制类型 -- 默认：队列大小
     */
    private int bufferType = WlBufferType.BUFFER_QUEUE_SIZE.getValue();
    private double bufferValue = 100;

    /**
     * pcm数据回调
     */
    private boolean callBackPcmData = false;

    private SurfaceTexture.OnFrameAvailableListener frameAvailableListener;

    /** 2、  methods ================================================= */
    public WlMedia(){
        hashCode = -1;
        handler = new WlMediaHandler(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            isVersion21 = true;
        }
        else
        {
            isVersion21 = false;
        }
    }

    private void setFrameAvailableListener(SurfaceTexture.OnFrameAvailableListener frameAvailableListener)
    {
        this.frameAvailableListener = frameAvailableListener;
    }

    /**
     * 设置数据源
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 得到数据源
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     * 异步准备
     */
    public void prepared()
    {
        if(playing)
        {
            onError(0, "the player is in playing!, you can use '.next()' func to change play");
            return;
        }
        if(TextUtils.isEmpty(source))
        {
            if(sourceType == WlSourceType.NORMAL.getValue() || sourceType == WlSourceType.ENCRYPT_FILE.getValue())
            {
                onError(0, "the source is empty");
            }
            return;
        }
        if(playModel == WlPlayModel.PLAYMODEL_ONLY_VIDEO.getValue() || playModel == WlPlayModel.PLAYMODEL_AUDIO_VIDEO.getValue())
        {
            if(surface == null)
            {
                onError(0, "playmodel is show video, but the surface is null, please use 'wlsurfaceview.setWlMedia() or wltextureview.setWlmedia()' to set render view ");
                return;
            }
        }
        if(frameAvailableListener == null && (playModel == WlPlayModel.PLAYMODEL_AUDIO_VIDEO.getValue() || playModel == WlPlayModel.PLAYMODEL_ONLY_VIDEO.getValue()))
        {
            setFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                @Override
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    n_notifyRender(surfaceTexture.getTimestamp());
                }
            });
        }
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_PREPARED);
    }

    /**
     * 停止 如果是有视频播放 不回收surface资源
     */
    public void stop()
    {
        needReleaseSurface = false;
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_STOP);
    }
    /**
     * 停止 如果是有视频播放 回收surface资源
     */
    private void stop(boolean releaseSurface)
    {
        needReleaseSurface = releaseSurface;
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_STOP);
    }

    /**
     * 停止 退出回收surface资源
     */
    public void release()
    {
        stop(true);
    }

    /**
     * 开始播放（在prepared准备好后的回调里调用）
     */
    public void start()
    {
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_START);
    }

    /**
     * 播放下一曲 （类似切割）
     */
    public void next(){
        if(!playing)
        {
            prepared();
        }
        else{
            stop();
            playNextCount ++;
        }
    }

    /**
     * seek
     * @param seekTime
     */
    public void seek(double seekTime)
    {
        this.seekTime = seekTime;
        if(getDuration() > 0)
        {
            n_seek(this.seekTime);
        }
        else{
            onError(0, "the media can not seek");
        }
    }

    /**
     * 得到seekTime
     * @return
     */
    public double getSeekTime()
    {
        return seekTime;
    }

    /**
     * 获取时长
     * @return
     */
    public double getDuration()
    {
        if(duration == -1)
        {
            duration = n_getDuration();
        }
        return duration;
    }

    /**
     * 标记seekstart 会暂停时间回调
     */
    public void seekStart()
    {
        isSeeking = true;
        handler.removeMessages(WlHandleMessage.WLMSG_PLAY_TIME_INFO);
    }

    /**
     * 标记seekstart 手动回复时间回调
     */
    public void seekEnd()
    {
        isSeeking = false;
    }

    /**
     * 是否在seeking中
     * @return
     */
    public boolean isSeeking()
    {
        return isSeeking;
    }

    /**
     * 设置音频声道
     * @param audioChannel
     */
    public void setAudioChannel(WlAudioChannel audioChannel)
    {
        this.audioChannel = audioChannel.getValue();
        n_setAudioChannel(this.audioChannel);
    }

    /**
     * 获取视频声道
     * @return
     */
    public int getAudioChannel() {
        return audioChannel;
    }

    /**
     * 设置音量（0~200 100为原始大小）
     * @param volume
     */
    public void setVolume(double volume)
    {
        if(volume >= 0 || volume <= 200)
        {
            this.volume = volume;
            n_setAudioVolume(volume);
        }
        else{
            onError(0, "the volume is range [0,200]");
        }
    }

    /**
     * 获取音量值
     * @return
     */
    public double getVolume() {
        return volume;
    }

    /**
     * 设置播放速度
     * @param speed
     */
    public void setSpeed(double speed)
    {
        if(speed >= 0.5f && speed < 4.0f)
        {
            this.speed = speed;
            n_setSpeed(this.speed);
        }
    }

    /**
     * 获取播放速度
     * @return
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * 设置播放音调
     * @param pitch
     */
    public void setPitch(double pitch)
    {
        if(pitch >= 0.5f && pitch < 4.0f)
        {
            this.pitch = pitch;
            n_setPitch(this.pitch);
        }
    }

    /**
     * 得到播放音调
     * @return
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * 设置音频采样率
     * @param sampleRate
     */
    public void setSampleRate(WlSampleRate sampleRate)
    {
        this.sampleRate = sampleRate.getValue();
    }

    /**
     * 得到设置的采样率
     * @return
     */
    public int getSampleRate()
    {
        return sampleRate;
    }

    /**
     * 获取音频轨道信息
     * @return
     */
    public WlMediaInfoBean[] getAudioTracks()
    {
        if(audioTracks == null)
        {
            audioTracks = n_getAudioTracks();
        }
        return audioTracks;
    }

    /**
     * 获取视频轨道信息
     * @return
     */
    public WlMediaInfoBean[] getVideoTracks()
    {
        if(videoTracks == null)
        {
            videoTracks = n_getVideotracks();
        }
        return videoTracks;
    }

    /**
     * 获取字幕轨道信息
     * @return
     */
    public WlMediaInfoBean[] getSubtitleTracks()
    {
        if(subtitleTracks == null)
        {
            subtitleTracks = n_getSubtitletracks();
        }
        return subtitleTracks;
    }
    /**
     * 设置音频音轨
     * 注：如果要开始播放前设置指定音轨，需要再onPrepared回调里面设置才能生效
     * @param trackIndex
     */
    public void setAudioTrack(int trackIndex)
    {
        if(n_setAudioTrack(trackIndex) == 0)
        {
            audioTrackIndex = trackIndex;
        }
    }

    /**
     * 得到当前选中的音频轨道索引
     * @return
     */
    public int getAudioTrackIndex()
    {
        return audioTrackIndex;
    }

    /**
     * 设置视频轨道
     * @param trackIndex
     */
    public void setVideoTrack(int trackIndex)
    {
//        if(n_setVideoTrack(trackIndex) == 0)
//        {
//            videoTrackIndex = trackIndex;
//        }
    }

    /**
     * 得到当前选中的视频轨道索引
     * @return
     */
    public int getVideoTrackIndex()
    {
        return videoTrackIndex;
    }

    /**
     * 设置字幕轨道
     * @param trackIndex
     */
    public void setSubtitleTrack(int trackIndex)
    {
        if(n_setSubtitleTrack(trackIndex) == 0)
        {
            subtitleTrackIndex = trackIndex;
        }
    }

    /**
     * 得到当前字幕轨道索引
     * @return
     */
    public int getSubtitleTrackIndex()
    {
        return subtitleTrackIndex;
    }

    /**
     * 设置播放模式 （音视频同时播放-只播放音频-只播放视频）
     * @param playModel
     */
    public void setPlayModel(WlPlayModel playModel) {
        this.playModel = playModel.getValue();
    }

    /**
     * 得到播放model
     * @return
     */
    public int getPlayModel()
    {
        return playModel;
    }

    /**
     * 设置视频surface （可用于自定义视频播放view）
     * @param surface
     */
    public void setSurface(Surface surface) {
        this.surface = surface;
        n_surfaceCreate();
    }

    public Surface getSurface()
    {
        return surface;
    }

    /**
     * surface 大小改变
     * @param width
     * @param height
     * @param surface
     */
    public void onSurfaceChange(int width, int height, Surface surface)
    {
        this.surfaceWidth = width;
        this.surfaceHeight = height;
        this.surface = surface;
        n_surfaceChange();
    }

    public int getSurfaceWidth()
    {
        return surfaceWidth;
    }

    public int getSurfaceHeight()
    {
        return surfaceHeight;
    }

    /**
     * surface销毁
     */
    public void onSurfaceDestroy()
    {
        n_surfaceDestroy();
    }

    /**
     * 设置缩放视频比例
     * @param w
     * @param h
     */
    public void scaleVideo(int w, int h)
    {
        scaleVideo(w, h, getRotateType());
    }

    /**
     * 设置视频旋转角度
     * @param videoRotate
     */
    public void rotateVideo(WlVideoRotate videoRotate)
    {
        this.rotate = videoRotate.getValue();
        n_scaleVideo(getScaleWidth(), getScaleHeight(), this.rotate);
    }

    /**
     * 设置缩放视频比例和旋转角度
     * @param w
     * @param h
     */
    public void scaleVideo(int w, int h, WlVideoRotate videoRotate)
    {
        this.rotate = videoRotate.getValue();
        this.scale_w = w;
        this.scale_h = h;
        n_scaleVideo(this.scale_w, this.scale_h, this.rotate);
    }

    public int getScaleWidth()
    {
        return scale_w;
    }

    public int getScaleHeight()
    {
        return scale_h;
    }

    public int getRotate()
    {
        return rotate;
    }

    public WlVideoRotate getRotateType()
    {
        WlVideoRotate videoRotate;
        switch (rotate)
        {
            case 0:
                videoRotate = WlVideoRotate.VIDEO_ROTATE_0;
                break;
            case -90:
                videoRotate = WlVideoRotate.VIDEO_ROTATE_90;
                break;
            case -180:
                videoRotate = WlVideoRotate.VIDEO_ROTATE_180;
                break;
            case -270:
                videoRotate = WlVideoRotate.VIDEO_ROTATE_270;
                break;
            default:
                videoRotate = WlVideoRotate.VIDEO_ROTATE_DEFAULT;
        }
        return videoRotate;
    }

    /**
     * 自定义着色器
     * @param fShader
     */
    public void setFshader(String fShader) {
        this.fShader = fShader;
    }

    /**
     * 使自定义着色器生效
     */
    public void changeFilter()
    {
        n_changeFilter();
    }

    /**
     * 设置背景颜色 r、g、b、r范围：[0`1]
     * @param rgba_r
     * @param rgba_g
     * @param rgba_b
     * @param rgba_a
     */
    public void setVideoClearColor(float rgba_r, float rgba_g, float rgba_b, float rgba_a)
    {
        if(rgba_r < 0f)
        {
            rgba_r = 0f;
        }
        else if(rgba_r > 1f)
        {
            rgba_r = 1f;
        }

        if(rgba_g < 0f)
        {
            rgba_g = 0f;
        }
        else if(rgba_g > 1f)
        {
            rgba_g = 1f;
        }

        if(rgba_b < 0f)
        {
            rgba_b = 0f;
        }
        else if(rgba_b > 1f)
        {
            rgba_b = 1f;
        }

        if(rgba_a < 0f)
        {
            rgba_a = 0f;
        }
        else if(rgba_a > 1f)
        {
            rgba_a = 1f;
        }

        this.rgba_r = rgba_r;
        this.rgba_g = rgba_g;
        this.rgba_b = rgba_b;
        this.rgba_a = rgba_a;
        n_setVideoClearColor(rgba_r, rgba_g, rgba_b, rgba_a);
    }

    public float getRgba_r() {
        return rgba_r;
    }

    public float getRgba_g() {
        return rgba_g;
    }

    public float getRgba_b() {
        return rgba_b;
    }

    public float getRgba_a() {
        return rgba_a;
    }

    /**
     * 播放完后是否清屏（默认是黑色清屏）
     */
    public void setClearLastPicture(boolean clearLastPicture) {
        this.clearLastPicture = clearLastPicture;
    }

    /**
     * 是否使用soundtouch
     * @param useSoundTouch
     */
    public void setUseSoundTouch(boolean useSoundTouch) {
        this.useSoundTouch = useSoundTouch;
    }

    public boolean isUseSoundTouch() {
        return useSoundTouch;
    }

    /**
     * 得到当前播放时间
     * @return
     */
    public double getNowTime()
    {
        return playTime;
    }
    /**
     * 得到当前缓冲时间
     * @return
     */
    public double getNowBufferTime()
    {
        return bufferTime;
    }

    /**
     * 暂停
     */
    public void pause()
    {
        isPause = true;
        n_pause();
        if(onMediaInfoListener != null)
        {
            onMediaInfoListener.onPause(true);
        }
    }

    public boolean isPause() {
        return isPause;
    }

    /**
     * 继续播放 （对应暂停）
     */
    public void resume()
    {
        isPause = false;
        n_resume();
        if(onMediaInfoListener != null)
        {
            onMediaInfoListener.onPause(false);
        }
    }

    /**
     * 视频截图
     */
    public void takePicture()
    {
        n_takePicture();
    }

    /**
     * 设置音视频同步差值
     * @param offsetTime
     */
    public void setAudioOffsetTime(double offsetTime)
    {
        this.audioOffsetTime = offsetTime;
        n_setAudioOffsetTime(offsetTime);
    }

    public double getAudioOffsetTime() {
        return audioOffsetTime;
    }

    /**
     * 清空ffmpeg的参数设置
     */
    public void clearFFOptions()
    {
        if(ffOptions != null)
        {
            ffOptions.clear();
        }
    }

    /**
     * 设置ffmpeg参数 （比如：udp tcp 等）
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
     * 设置音视频基础信息
     * @param mediaInfos
     */
    public void setMediaInfos(WlMediaInfoBean[] mediaInfos) {
        this.mediaInfos = mediaInfos;
    }

    /**
     * 获取音视频基础信息
     * @param streamIndex
     * @return
     */
    private WlMediaInfoBean getMediaInfo(int streamIndex)
    {
        if(mediaInfos == null)
        {
            return null;
        }
        for(int i = 0; i < mediaInfos.length; i++)
        {
            if(mediaInfos[i].getStreamIndex() == streamIndex)
            {
                return mediaInfos[i];
            }
        }
        return null;
    }

    /**
     * 设置是否循环播放
     * @param loopPlay
     */
    public void setLoopPlay(boolean loopPlay) {
        if(this.loopPlay == loopPlay)
        {
            return;
        }
        this.loopPlay = loopPlay;
        n_setLoopPlay(this.loopPlay);
    }

    public boolean isLoopPlay() {
        return loopPlay;
    }

    /**
     * 设置数据源模式（url 加密file buffer)
     * @param sourceType
     */
    public void setSourceType(WlSourceType sourceType) {
        this.sourceType = sourceType.getValue();
    }

    public int getSourceType() {
        return sourceType;
    }

    /**
     * 设置超时时间
     * @param timeOut
     */
    public void setTimeOut(int timeOut)
    {
        if(timeOut > 0)
        {
            this.timeOut = timeOut;
        }
    }

    /**
     * 获取超时时间
     * @return
     */
    public int getTimeOut()
    {
        return timeOut;
    }

    /**
     * 设置解码模式
     * @param codecType
     */
    public void setCodecType(WlCodecType codecType) {
        this.codecType = codecType.getValue();
    }

    public int getCodecType() {
        return codecType;
    }

    /**
     * 获取视频宽度
     * @return
     */
    public int getVideoWidth()
    {
        if(video_width == 0)
        {
            video_width = n_getVideoWidth();
        }
        return video_width;
    }

    /**
     * 获取视频高度
     * @return
     */
    public int getVideoHeight()
    {
        if(video_height == 0)
        {
            video_height = n_getVideoHeight();
        }
        return video_height;
    }

    /**
     * 是否在播放中
     * @return
     */
    public boolean isPlaying()
    {
        return playing;
    }

    /**
     * 设置播放透明视频模式
     * @param alphaVideoType
     */
    public void setAlphaVideoType(WlAlphaVideoType alphaVideoType) {
        this.alphaVideoType = alphaVideoType.getValue();
    }

    public int getAlphaVideoType() {
        return alphaVideoType;
    }

    /**
     * 设置时间回调方式
     * @param smooth true：每一次的时间都回调 false 一段时间（约一秒）回调一次
     */
    public void setSmoothTime(boolean smooth)
    {
        this.smoothTime = smooth;
        n_smoothtime(smooth);
    }

    public boolean isSmoothTime() {
        return smoothTime;
    }

    /**
     * 设置内存播放时 最大内存大小
     * @param size
     */
    public void setIoBufferSize(long size)
    {
        this.ioBufferSize = size;
    }

    public long getIoBufferSize() {
        return ioBufferSize;
    }

    /**
     * 设置底层buffer类型和大小
     * @param bufferType
     * @param bufferValue
     */
    public void setBufferSize(WlBufferType bufferType, double bufferValue)
    {
        this.bufferType = bufferType.getValue();
        this.bufferValue = bufferValue;
    }

    public int getBufferType() {
        return bufferType;
    }

    public double getBufferValue() {
        return bufferValue;
    }

    /**
     * 错误回调（用于提示性目的和排查错误，实际播放出错为 complete的error类型）
     * @param code
     * @param msg
     */
    private void onError(int code, String msg)
    {
        if(onMediaInfoListener != null)
        {
            onMediaInfoListener.onError(code, msg);
        }
    }

    public void setCallBackPcmData(boolean callBackPcmData) {
        this.callBackPcmData = callBackPcmData;
    }

    /** 3、  listeners ================================================= */
    private WlOnMediaInfoListener onMediaInfoListener;
    private WlOnTakePictureListener onTakePictureListener;
    private WlOnPcmDataListener onPcmDataListener;

    /**
     * 设置必须的回调
     * @param onMediaInfoListener
     */
    public void setOnMediaInfoListener(WlOnMediaInfoListener onMediaInfoListener) {
        this.onMediaInfoListener = onMediaInfoListener;
    }

    /**
     * 设置视频截图回调
     * @param onTakePictureListener
     */
    public void setOnTakePictureListener(WlOnTakePictureListener onTakePictureListener) {
        this.onTakePictureListener = onTakePictureListener;
    }

    public void setOnPcmDataListener(WlOnPcmDataListener onPcmDataListener) {
        this.onPcmDataListener = onPcmDataListener;
    }

    /** 4、 jni call methods ================================================= */
    private void nCallPrepared()
    {
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_START_CALLBACK);
    }
    private void nCallStop()
    {
        stop();
    }
    private void nCallRelease()
    {
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_RELEASE);
    }
    private void nCallComplete(int type, String msg) {
        Message message = Message.obtain();
        message.what = WlHandleMessage.WLMSG_COMPLETE;
        message.obj = msg;
        message.arg1 = type;
        handler.sendMessage(message);
    }
    private void nCallTimeInfo(double playTime, double bufferTime){
        this.playTime = playTime;
        this.bufferTime = bufferTime;
        if(!isSeeking)
        {
            Message message = Message.obtain();
            message.what = WlHandleMessage.WLMSG_PLAY_TIME_INFO;
            message.obj = this.playTime;
            handler.sendMessage(message);
        }
        else{
            handler.removeMessages(WlHandleMessage.WLMSG_PLAY_TIME_INFO);
        }
    }
    private void nCallSeekFinished() {
        isSeeking = false;
        handler.sendEmptyMessage(WlHandleMessage.WLMSG_SEEK_FINISH);
    }
    private void nCallTakePicture(byte[] pixels, int w, int h) {
        Bitmap stitchBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(pixels));
        Matrix m = new Matrix();
        m.setScale(1, -1);
        Bitmap newBitmap = Bitmap.createBitmap(stitchBmp, 0, 0, w, h, m, true);
        Message message = Message.obtain();
        message.obj = newBitmap;
        message.what = WlHandleMessage.WLMSG_TAKE_PITTURE;
        handler.sendMessage(message);
    }
    private void nCallLoopPlayCount(int loopCount) {
        Message message = Message.obtain();
        message.what = WlHandleMessage.WLMSG_LOOPPLAY_COUNT;
        message.obj = loopCount;
        handler.sendMessage(message);
    }
    private void nCallLoad(boolean load){
        Message message = Message.obtain();
        message.what = WlHandleMessage.WLMSG_LOAD;
        message.obj = load;
        handler.sendMessage(message);
    }
    private byte[] nCallDecrypt(byte[] encrypt_data) {
        if(onMediaInfoListener != null)
        {
            return onMediaInfoListener.decryptBuffer(encrypt_data);
        }
        return null;
    }
    private byte[] nCallBuffer(int read_size) {
        if(onMediaInfoListener != null)
        {
            byte[] bufs = onMediaInfoListener.readBuffer(read_size);
            if(bufs != null && bufs.length <= read_size)
            {
                return bufs;
            }
            else{
                WlLog.d("the buffer is null or length bigger than needlength");
            }
        }
        return null;
    }

    /**
     * pcm 属性回调
     * @param bit
     * @param channel
     * @param samplerate
     */
    private void nCallPcmInfo(int bit, int channel, int samplerate)
    {
        if(onPcmDataListener != null)
        {
            onPcmDataListener.onPcmInfo(bit, channel, samplerate);
        }
    }

    /**
     * pcm 音频数据回调
     * （注：如果在此回调中是比较耗时的操作，可使用队列临时缓存数据，以避免播放线程阻塞！）
     * @param size
     * @param data
     */
    private void nCallPcmData(int size, byte[] data, double db)
    {
        if(onPcmDataListener != null)
        {
            onPcmDataListener.onPcmData(size, data, db);
        }
    }

    /** 5、 native methods ================================================= */
    private native int n_prepared(String source);
    private native int n_stop();
    private native int n_release();
    private native int n_start();
    private native int n_seek(double seekTime);
    private native double n_getDuration();
    private native int n_setAudioChannel(int channel);
    private native int n_setAudioVolume(double volume);
    private native int n_setSpeed(double speed);
    private native int n_setPitch(double pitch);
    private native WlMediaInfoBean[] n_getAudioTracks();
    private native WlMediaInfoBean[] n_getVideotracks();
    private native WlMediaInfoBean[] n_getSubtitletracks();
    private native int n_setAudioTrack(int trackIndex);
    private native int n_setVideoTrack(int trackIndex);
    private native int n_setSubtitleTrack(int trackIndex);
    private native int n_surfaceCreate();
    private native int n_surfaceChange();
    private native int n_surfaceDestroy();
    private native int n_scaleVideo(int sw, int sh, int r);
    private native int n_changeFilter();
    private native int n_setVideoClearColor(float r, float g, float b, float a);
    private native int n_pause();
    private native int n_resume();
    private native int n_takePicture();
    private native int n_setAudioOffsetTime(double offsetTime);
    private native int n_setLoopPlay(boolean loopPlay);
    private native int n_getVideoWidth();
    private native int n_getVideoHeight();
    private native int n_smoothtime(boolean smooth);
    private native int n_notifyRender(long timestamp);

    private static class WlMediaHandler extends Handler {
        private WeakReference<WlMedia> reference;
        public WlMediaHandler(WlMedia media) {
            reference = new WeakReference<WlMedia>(media);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WlMedia wlMedia = reference.get();
            int ret = 0;
            if(wlMedia != null)
            {
                switch (msg.what)
                {
                    case WlHandleMessage.WLMSG_PREPARED:
                        ret = wlMedia.n_prepared(wlMedia.source);
                        if(ret == 0)
                        {
                            wlMedia.playing = true;
                        }
                        break;
                    case WlHandleMessage.WLMSG_START_CALLBACK:
                        if(wlMedia.onMediaInfoListener != null)
                        {
                            wlMedia.onMediaInfoListener.onPrepared();
                        }
                        break;
                    case WlHandleMessage.WLMSG_STOP:
                        wlMedia.n_stop();
                        break;
                    case WlHandleMessage.WLMSG_RELEASE:
                        wlMedia.n_release();
                        break;
                    case WlHandleMessage.WLMSG_COMPLETE:

                        wlMedia.isSeeking = false;
                        wlMedia.duration = -1;
                        wlMedia.playTime = 0;
                        wlMedia.bufferTime = 0;
                        wlMedia.seekTime = 0;
                        wlMedia.playing = false;
                        wlMedia.videoTracks = null;
                        wlMedia.audioTracks = null;
                        wlMedia.subtitleTracks = null;
                        wlMedia.videoTrackIndex = 0;
                        wlMedia.audioTrackIndex = 0;
                        wlMedia.subtitleTrackIndex = 0;
                        wlMedia.video_width = 0;
                        wlMedia.video_height = 0;
                        wlMedia.isPause = false;

                        WlComplete wlComplete = WlComplete.WL_COMPLETE_EOF;
                        String failMsg = null;
                        if(wlMedia.playNextCount > 0) //需要播放下一首
                        {
                            wlComplete = WlComplete.WL_COMPLETE_NEXT;
                            wlMedia.playNextCount = 0;
                            wlMedia.prepared();
                        }
                        else{
                            wlComplete = WlComplete.getWlCompleteByValue(msg.arg1);
                            if(msg.obj != null)
                            {
                                failMsg = (String) msg.obj;
                            }
                        }
                        if(wlMedia.onMediaInfoListener != null)
                        {
                            wlMedia.onMediaInfoListener.onComplete(wlComplete, failMsg);
                        }
                        break;
                    case WlHandleMessage.WLMSG_START:
                        wlMedia.n_start();
                        break;
                    case WlHandleMessage.WLMSG_PLAY_TIME_INFO:
                        if(wlMedia.onMediaInfoListener != null && !wlMedia.isSeeking)
                        {
                            wlMedia.onMediaInfoListener.onTimeInfo((Double) msg.obj, wlMedia.bufferTime);
                        }
                        break;
                    case WlHandleMessage.WLMSG_SEEK_FINISH:
                        if(wlMedia.onMediaInfoListener != null)
                        {
                            wlMedia.onMediaInfoListener.onSeekFinish();
                        }
                        break;
                    case WlHandleMessage.WLMSG_TAKE_PITTURE:
                        if(wlMedia.onTakePictureListener != null)
                        {
                            Bitmap bitmap = (Bitmap) msg.obj;
                            wlMedia.onTakePictureListener.takePicture(bitmap);
                        }
                        break;
                    case WlHandleMessage.WLMSG_LOOPPLAY_COUNT:
                        if(wlMedia.onMediaInfoListener != null)
                        {
                            wlMedia.onMediaInfoListener.onLoopPlay((Integer) msg.obj);
                        }
                        break;

                    case WlHandleMessage.WLMSG_LOAD:
                        if(wlMedia.onMediaInfoListener != null)
                        {
                            wlMedia.onMediaInfoListener.onLoad((Boolean) msg.obj);
                        }
                        break;
                }
            }
        }
    }


}
