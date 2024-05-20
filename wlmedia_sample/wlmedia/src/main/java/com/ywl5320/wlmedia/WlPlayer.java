package com.ywl5320.wlmedia;

import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_AUTO_PLAY_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_COMPLETE_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_FIRST_FRAME_RENDERED_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_LOADING_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_OUT_RENDER_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_PREPARED_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_SEEK_FINISH_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_TAKE_PICTURE_CB;
import static com.ywl5320.wlmedia.message.WlHandleMessage.WL_MSG_TIME_INFO_CB;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;

import com.ywl5320.wlmedia.bean.WlOutRenderBean;
import com.ywl5320.wlmedia.bean.WlTrackInfoBean;
import com.ywl5320.wlmedia.enums.WlAlphaVideoType;
import com.ywl5320.wlmedia.enums.WlAudioChannelType;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlMirrorType;
import com.ywl5320.wlmedia.enums.WlParamType;
import com.ywl5320.wlmedia.enums.WlPitchType;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlRotateType;
import com.ywl5320.wlmedia.enums.WlSampleRate;
import com.ywl5320.wlmedia.enums.WlScaleType;
import com.ywl5320.wlmedia.enums.WlSeekType;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.enums.WlTrackType;
import com.ywl5320.wlmedia.listener.WlOnLoadLibraryListener;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;

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
    private WlPlayerHandler playerHandler;

    /**
     * 播放回调接口
     */
    private WlOnMediaInfoListener onMediaInfoListener;

    /**
     * 无参构造函数
     */
    public WlPlayer() {
        this(null);
    }

    /**
     * 带参构造函数，支持外部动态加载库
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

    /** -------------------- listeners -------------------------*/
    /**
     * 设置回调函数
     *
     * @param onMediaInfoListener
     */
    public void setOnMediaInfoListener(WlOnMediaInfoListener onMediaInfoListener) {
        this.onMediaInfoListener = onMediaInfoListener;
    }


    /**
     * -------------------- methods -------------------------
     */

    /**
     * 获取当前版本号
     * @return
     */
    public static String getVersion() {
        return "wlmedia-3.0.0";
    }

    /**
     * 设置数据源
     *
     * @param source
     */
    public void setSource(String source) {
        n_wlPlayer_setString(WlParamType.WL_PARAM_STRING_SOURCE.getValue(), source);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public String getSource() {
        return n_wlPlayer_getString(WlParamType.WL_PARAM_STRING_SOURCE.getValue());
    }

    /**
     * 设置数据源类型
     *
     * @param sourceType
     */
    public void setSourceType(WlSourceType sourceType) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_SOURCE_TYPE.getValue(), sourceType.getValue());
    }

    /**
     * 获取当前的数据类型
     *
     * @return
     */
    public WlSourceType getSourceType() {
        int type = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_SOURCE_TYPE.getValue());
        return WlSourceType.find(type);
    }

    /**
     * 设置时间回调间隔
     *
     * @param seconds
     */
    public void setTimeInfoInterval(double seconds) {
        n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_TIME_INTERVAL.getValue(), seconds);
    }

    /**
     * 设置时间回调间隔
     */
    public double getTimeInfoInterval() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_TIME_INTERVAL.getValue());
    }

    /**
     * 异步准备
     */
    public void prepare() {
        n_wlPlayer_prepare();
    }

    /**
     * 停止播放
     */
    public void stop() {
        n_wlPlayer_stop();
    }

    /**
     * 销毁资源
     */
    public void release() {
        n_wlPlayer_release();
    }

    /**
     * 在异步准备完成后，正式开始播放
     */
    public void start() {
        n_wlPlayer_start();
    }

    /**
     * 获取时长
     *
     * @return 返回时长
     */
    public double getDuration() {
        return n_wlPlayer_getDuration();
    }

    /**
     * 暂停
     */
    public void pause() {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_PAUSE_RESUME.getValue(), true);
    }

    /**
     * 获取是否暂停
     *
     * @return
     */
    public boolean isPause() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_PAUSE_RESUME.getValue());
    }

    /**
     * 播放（相对暂停）
     */
    public void resume() {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_PAUSE_RESUME.getValue(), false);
    }

    /**
     * seek 开始
     */
    public void seekStart() {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_SEEK_START.getValue(), true);
    }

    /**
     * 是否开始seek了
     *
     * @return
     */
    private boolean isSeekStart() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_SEEK_START.getValue());
    }

    /**
     * seek
     *
     * @param seekTime 跳转时间，默认快速模式
     */
    public void seek(double seekTime) {
        seek(seekTime, WlSeekType.WL_SEEK_FAST);
    }

    /**
     * seek
     *
     * @param seekTime 跳转时间
     * @param seekType seek模式
     */
    public void seek(double seekTime, WlSeekType seekType) {
        n_wlPlayer_seek(seekTime, seekType.getValue());
    }

    /**
     * 设置渲染surface
     *
     * @param surface      Surface画布（SurfaceView、TextureView）
     * @param rgba         清屏颜色如：#FF0000 or #FF000000
     * @param serialNumber surface唯一序列号
     */
    public void setSurface(Surface surface, String rgba, int serialNumber) {
        n_wlPlayer_updateSurface(surface, rgba, serialNumber);
    }

    /**
     * 设置 播放模式
     *
     * @param playModel 只播放音频
     *                  只播放视频
     *                  同时播放音视频
     */
    public void setPlayModel(WlPlayModel playModel) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_PLAY_MODEL.getValue(), playModel.getValue());
    }

    /**
     * 获取当前播放模式
     *
     * @return
     */
    public WlPlayModel getPlayModel() {
        int playModel = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_PLAY_MODEL.getValue());
        return WlPlayModel.find(playModel);
    }

    /**
     * 设置解码模式（硬解码 软解码）
     *
     * @param codecType
     */
    public void setCodecType(WlCodecType codecType) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_CODEC_TYPE.getValue(), codecType.getValue());
    }

    /**
     * 获取解码模式
     *
     * @return
     */
    public WlCodecType getCodecType() {
        int codecType = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_CODEC_TYPE.getValue());
        return WlCodecType.find(codecType);
    }

    /**
     * 设置音频采样率
     *
     * @param sampleRate
     */
    public void setSampleRate(WlSampleRate sampleRate) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_SAMPLE_RATE.getValue(), sampleRate.getValue());
    }

    /**
     * 获取音频采样率
     *
     * @return
     */
    public WlSampleRate getSampleRate() {
        int sampleRate = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_SAMPLE_RATE.getValue());
        return WlSampleRate.find(sampleRate);
    }

    /**
     * 设置画面渲染帧率
     *
     * @param fps
     */
    public void setRenderFPS(int fps) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_RENDER_FPS.getValue(), fps);
    }

    /**
     * 获取最大渲染fps
     *
     * @return
     */
    public int getRenderFPS() {
        return n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_RENDER_FPS.getValue());
    }

    /**
     * 设置播放速度
     *
     * @param speed [0.5, 4]
     */
    public void setSpeed(double speed) {
        n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_SPEED.getValue(), speed);
    }

    /**
     * 获取播放速度
     *
     * @return
     */
    public double getSpeed() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_SPEED.getValue());
    }

    /**
     * 设置音调，默认模式
     *
     * @param pitch
     */
    public void setPitch(double pitch) {
        setPitch(WlPitchType.WL_PITCH_NORMAL, pitch);
    }

    /**
     * 设置音调
     *
     * @param pitchType 音调模式
     * @param pitch
     */
    public void setPitch(WlPitchType pitchType, double pitch) {
        if (pitchType == WlPitchType.WL_PITCH_OCTAVES) {
            n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_PITCH_OCTAVES.getValue(), pitch);
        } else if (pitchType == WlPitchType.WL_PITCH_SEMITONES) {
            n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_PITCH_SEMITONES.getValue(), pitch);
        } else {
            n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_PITCH_NORMAL.getValue(), pitch);
        }
    }

    /**
     * 获取 音调类型
     *
     * @return
     */
    public WlPitchType getPitchType() {
        int pitchType = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_PITCH_TYPE.getValue());
        return WlPitchType.find(pitchType);
    }

    /**
     * 获取 音调值
     *
     * @return
     */
    public double getPitch() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_PITCH.getValue());
    }

    /**
     * 设置视频比例
     *
     * @param scaleType
     */
    public void scaleVideo(WlScaleType scaleType) {
        scaleVideo(scaleType.getScaleWidth(), scaleType.getScaleHeight());
    }

    /**
     * 设置视频显示比例
     *
     * @param scaleWidth
     * @param scaleHeight
     */
    public void scaleVideo(int scaleWidth, int scaleHeight) {
        n_wlPlayer_scaleVideo(scaleWidth, scaleHeight);
    }

    /**
     * 获取缩放比例：宽
     *
     * @return
     */
    public int getScaleWidth() {
        return n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_SCALE_WIDTH.getValue());
    }

    /**
     * 获取缩放比例：高
     *
     * @return
     */
    public int getScaleHeight() {
        return n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_SCALE_HEIGHT.getValue());
    }

    /**
     * 设置视频旋转角度
     *
     * @param rotateType
     */
    public void rotateVideo(WlRotateType rotateType) {
        n_wlPlayer_rotateVideo(rotateType.getValue());
    }

    /**
     * 获取视频旋转角度
     *
     * @return
     */
    public WlRotateType getVideoRotate() {
        int rotate = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_VIDEO_ROTATE.getValue());
        return WlRotateType.find(rotate);
    }

    /**
     * 设置视频镜像
     *
     * @param wlMirrorType
     */
    public void mirrorVideo(WlMirrorType wlMirrorType) {
        n_wlPlayer_mirrorVideo(wlMirrorType.getValue());
    }

    /**
     * 获取镜像类型
     *
     * @return
     */
    public WlMirrorType getVideoMirror() {
        int mirror = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_VIDEO_MIRROR.getValue());
        return WlMirrorType.find(mirror);
    }

    /**
     * 设置视频是否清除视频最后一帧
     *
     * @param clear
     */
    public void setClearLastVideoFrame(boolean clear) {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_CLEAR_LAST_FRAME.getValue(), clear);
    }

    /**
     * 是否清除最后一帧
     *
     * @return
     */
    public boolean isClearLastVideoFrame() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_CLEAR_LAST_FRAME.getValue());
    }

    /**
     * 设置超时时间，单位秒（s）
     *
     * @param timeOut
     */
    public void setTimeOut(double timeOut) {
        n_wlPlayer_setDouble(WlParamType.WL_PARAM_DOUBLE_TIME_OUT.getValue(), timeOut);
    }

    /**
     * 获取超时时间
     *
     * @return
     */
    public double getTimeOut() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_TIME_OUT.getValue());
    }

    /**
     * 设置循环播放
     *
     * @param loopPlay true:循环播放 且 正常oef的情况才会开始循环播放
     */
    public void setLoopPlay(boolean loopPlay) {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_LOOP_PLAY.getValue(), loopPlay);
    }

    /**
     * 是否循环播放
     *
     * @return
     */
    public boolean isLoopPlay() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_LOOP_PLAY.getValue());
    }

    /**
     * 准备好后自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        n_wlPlayer_setBool(WlParamType.WL_PARAM_BOOL_AUTO_PLAY.getValue(), autoPlay);
    }

    /**
     * 是否自动播放
     *
     * @return
     */
    public boolean isAutoPlay() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_AUTO_PLAY.getValue());
    }

    /**
     * 获取对应的 track 信息
     *
     * @param trackType
     * @return
     */
    private WlTrackInfoBean[] getMediaTracks(WlTrackType trackType) {
        return n_wlPlayer_getMediaTracks(trackType.getValue());
    }

    /**
     * 获取当前播放的 track 信息
     *
     * @param trackType
     * @return
     */
    private WlTrackInfoBean getCurrentMediaTrack(WlTrackType trackType) {
        int trackIndex = n_wlPlayer_getCurrentMediaTrackIndex(trackType.getValue());
        WlTrackInfoBean[] tracks = getMediaTracks(trackType);
        if (tracks != null) {
            if (trackIndex >= 0 && trackIndex < tracks.length) {
                return tracks[trackIndex];
            }
        }
        return null;
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
     * 获取当前音频 track 信息
     *
     * @return
     */
    public WlTrackInfoBean getCurrentAudioTrack() {
        return getCurrentMediaTrack(WlTrackType.WL_TRACK_AUDIO);
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
     * 获取当前视频 track 信息
     *
     * @return
     */
    public WlTrackInfoBean getCurrentVideoTrack() {
        return getCurrentMediaTrack(WlTrackType.WL_TRACK_VIDEO);
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
     * 获取当前字幕 track 信息
     *
     * @return
     */
    public WlTrackInfoBean getCurrentSubtitleTrack() {
        return getCurrentMediaTrack(WlTrackType.WL_TRACK_SUBTITLE);
    }

    /**
     * 设置音频track
     *
     * @param audioTrackIndex
     */
    public void setAudioPlayTrack(int audioTrackIndex) {
        setMediaPlayTrack(audioTrackIndex, -1, -1);
    }

    /**
     * 设置视频track
     *
     * @param videoTrackIndex
     */
    public void setVideoPlayTrack(int videoTrackIndex) {
        setMediaPlayTrack(-1, videoTrackIndex, -1);
    }

    /**
     * 设置字幕track
     *
     * @param subtitleTrackIndex
     */
    public void setSubtitlePlayTrack(int subtitleTrackIndex) {
        setMediaPlayTrack(-1, -1, subtitleTrackIndex);
    }

    /**
     * 设置播放（音频、视频、字幕）track
     *
     * @param audioTrackIndex
     * @param videoTrackIndex
     * @param subtitleTrackIndex
     */
    public void setMediaPlayTrack(int audioTrackIndex, int videoTrackIndex, int subtitleTrackIndex) {
        n_wlPlayer_setMediaPlayTrack(audioTrackIndex, videoTrackIndex, subtitleTrackIndex);
    }

    /**
     * 设置音量
     *
     * @param percent （0 ~ 200）
     */
    public void setVolume(int percent) {
        setVolume(percent, false);
    }

    /**
     * 设置音量
     *
     * @param percent   （0 ~ 200）
     * @param changePcm 是否改变pcm数据
     */
    public void setVolume(int percent, boolean changePcm) {
        n_wlPlayer_setVolume(percent, changePcm);
    }

    /**
     * 获取音量
     *
     * @return
     */
    public int getVolume() {
        return n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_VOLUME.getValue());
    }

    /**
     * 是否播放中
     *
     * @return
     */
    public boolean isPlaying() {
        return n_wlPlayer_getBool(WlParamType.WL_PARAM_BOOL_IS_PLAYING.getValue());
    }

    /**
     * 获取当前播放时间
     *
     * @return
     */
    public double getCurrentTime() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_GET_CURRENT_TIME.getValue());
    }

    /**
     * 获取当前缓存时间
     *
     * @return
     */
    public double getBufferTime() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_GET_BUFFER_TIME.getValue());
    }

    /**
     * 设置渲染默认大小
     *
     * @param width
     * @param height
     */
    public void setRenderDefaultSize(int width, int height) {
        n_wlPlayer_setRenderDefaultSize(width, height);
    }

    /**
     * 设置缓存大小 (参数单位：s)
     *
     * @param minBufferToPlay     开始播放时，最小缓存
     * @param maxBufferWaitToPlay 最大缓存
     */
    public void setBufferSize(double minBufferToPlay, double maxBufferWaitToPlay) {
        n_wlPlayer_setBufferSize(minBufferToPlay, maxBufferWaitToPlay);
    }

    /**
     * 获取最小缓冲达到播放阈值（单位秒）
     *
     * @return
     */
    public double getMinBufferToPlay() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_GET_MIN_BUFFER_TO_PLAY.getValue());
    }

    /**
     * 获取缓冲时到达播放阈值的最大值（单位秒）
     *
     * @return
     */
    public double getMaxBufferWaitToPlay() {
        return n_wlPlayer_getDouble(WlParamType.WL_PARAM_DOUBLE_GET_MAX_BUFFER_WAIT_TO_PLAY.getValue());
    }

    /**
     * 设置 options (key - value)
     *
     * @param key
     * @param value
     */
    public void setOptions(String key, String value) {
        n_wlPlayer_setStringKV(WlParamType.WL_PARAM_STRING_KV_OPTION.getValue(), key, value);
    }

    /**
     * 清空 optings
     */
    public void clearOptions() {
        n_wlPlayer_clearStringKV(WlParamType.WL_PARAM_STRING_KV_OPTION.getValue());
    }

    /**
     * 设置渲染方式
     *
     * @param alphaVideoType
     */
    public void setAlphaVideoType(WlAlphaVideoType alphaVideoType) {
        n_wlPlayer_setAlphaVideoType(alphaVideoType.getValue());
    }

    /**
     * 视频截图
     */
    public void takePicture() {
        n_wlPlayer_takePicture();
    }

    /**
     * 设置音频播放声道类型
     *
     * @param audioChannelType
     */
    public void setAudioChannelType(WlAudioChannelType audioChannelType) {
        n_wlPlayer_setInt(WlParamType.WL_PARAM_INT_AUDIO_CHANNEL.getValue(), audioChannelType.getValue());
    }

    /**
     * 获取声道类型
     *
     * @return
     */
    public WlAudioChannelType getAudioChannelType() {
        int type = n_wlPlayer_getInt(WlParamType.WL_PARAM_INT_AUDIO_CHANNEL.getValue());
        return WlAudioChannelType.find(type);
    }

    /**
     * 外部渲染初始化
     *
     * @param name
     * @param version
     */
    public void initOutRenderEnv(String name, int type, int version) {
        n_wlPlayer_initOutRenderEnv(name, type, version);
    }

    /**
     * 取消外部渲染初始化
     *
     * @param name
     */
    public void unInitOutRenderEnv(String name) {
        n_wlPlayer_unInitOutRenderEnv(name);
    }

    /**
     * -------------------- native callback -------------------------
     */
    private void nCallComplete(int type, String msg) {
        sendMessage(WL_MSG_COMPLETE_CB, type, msg);
    }

    private void nCallPrepared(double costTime) {
        sendMessage(WL_MSG_PREPARED_CB);
    }

    private void nCallTimeInfo(int currentTime, int bufferTime) {
        sendMessage(WL_MSG_TIME_INFO_CB, currentTime, bufferTime);
    }

    private void nCallLoadingStatus(int type, int progress, long speed) {
        sendMessage(WL_MSG_LOADING_CB, type, progress, speed);
    }

    private void nCallDealBuffer(int type, int size, byte[] data, long position) {
        byte[] buffer = null;
        if (type == WlSourceType.WL_SOURCE_ENCRYPT_FILE.getValue()) {
            buffer = onMediaInfoListener.decryptBuffer(data, position);
        } else {
            buffer = onMediaInfoListener.readBuffer(size);
        }
        n_wlPlayer_pushBufferData(buffer);
    }

    private void nCallAutoPlay() {
        sendMessage(WL_MSG_AUTO_PLAY_CB);
    }

    private void nCallFirstFrameRendered() {
        sendMessage(WL_MSG_FIRST_FRAME_RENDERED_CB);
    }

    private void nCallSeekFinish() {
        sendMessage(WL_MSG_SEEK_FINISH_CB);
    }

    private void nCallTakePicture(Bitmap bitmap) {
        sendMessage(WL_MSG_TAKE_PICTURE_CB, bitmap);
    }

    private void nCallOutRender(int id, int w, int h, int r) {
        WlOutRenderBean outRenderBean = new WlOutRenderBean(id, w, h, r);
        sendMessage(WL_MSG_OUT_RENDER_CB, outRenderBean);
    }

    /**
     * ---------------------- native methods --------------------------
     */
    private native int n_wlPlayer_init();

    private native int n_wlPlayer_setString(int type, String value);

    private native String n_wlPlayer_getString(int type);

    private native int n_wlPlayer_setBool(int type, boolean value);

    private native boolean n_wlPlayer_getBool(int type);

    private native int n_wlPlayer_setDouble(int type, double value);

    private native double n_wlPlayer_getDouble(int type);

    private native int n_wlPlayer_setInt(int type, int value);

    private native int n_wlPlayer_getInt(int type);

    private native int n_wlPlayer_prepare();

    private native int n_wlPlayer_stop();

    private native int n_wlPlayer_release();

    private native int n_wlPlayer_start();

    private native double n_wlPlayer_getDuration();

    private native int n_wlPlayer_seek(double seekTime, int seekType);

    private native int n_wlPlayer_updateSurface(Surface surface, String rgba, int serialNumber);

    private native int n_wlPlayer_scaleVideo(int scaleWidth, int scaleHeight);

    private native int n_wlPlayer_rotateVideo(int rotate);

    private native int n_wlPlayer_mirrorVideo(int mirror);

    private native WlTrackInfoBean[] n_wlPlayer_getMediaTracks(int trackType);

    private native int n_wlPlayer_getCurrentMediaTrackIndex(int trackType);

    private native int n_wlPlayer_setMediaPlayTrack(int audioTrackIndex, int videoTrackIndex, int subtitleTrackIndex);

    public native int n_wlPlayer_setVolume(int percent, boolean changePcm);

    public native int n_wlPlayer_setRenderDefaultSize(int width, int height);

    public native int n_wlPlayer_setBufferSize(double minBufferToPlay, double maxBufferWaitToPlay);

    private native int n_wlPlayer_setStringKV(int type, String key, String value);

    private native int n_wlPlayer_clearStringKV(int type);

    private native int n_wlPlayer_setAlphaVideoType(int type);

    private native int n_wlPlayer_pushBufferData(byte[] buffer);

    private native int n_wlPlayer_takePicture();

    private native int n_wlPlayer_initOutRenderEnv(String name, int type, int version);

    private native int n_wlPlayer_unInitOutRenderEnv(String name);

    private static class WlPlayerHandler extends Handler {
        private WeakReference<WlPlayer> reference;

        public WlPlayerHandler(WlPlayer player) {
            super(Looper.getMainLooper());
            reference = new WeakReference(player);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WlPlayer wlPlayer = reference.get();
            if (wlPlayer == null) {
                return;
            }
            switch (msg.what) {
                case WL_MSG_COMPLETE_CB:
                    int type = msg.arg1;
                    String completeMsg = (String) msg.obj;
                    WlCompleteType wlCompleteType = WlCompleteType.find(type);
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onComplete(wlCompleteType, completeMsg);
                    }
                    break;
                case WL_MSG_PREPARED_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onPrepared();
                    }
                    break;
                case WL_MSG_TIME_INFO_CB:
                    if (wlPlayer.onMediaInfoListener != null && !wlPlayer.isSeekStart()) {
                        wlPlayer.onMediaInfoListener.onTimeInfo(msg.arg1 / 1000.0, msg.arg2 / 1000.0);
                    }
                    break;
                case WL_MSG_LOADING_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onLoad(WlLoadStatus.find(msg.arg1), msg.arg2, (long) msg.obj);
                    }
                    break;
                case WL_MSG_AUTO_PLAY_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onAutoPlay();
                    }
                    break;
                case WL_MSG_FIRST_FRAME_RENDERED_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onFirstFrameRendered();
                    }
                    break;
                case WL_MSG_SEEK_FINISH_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        wlPlayer.onMediaInfoListener.onSeekFinish();
                    }
                    break;
                case WL_MSG_TAKE_PICTURE_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        wlPlayer.onMediaInfoListener.onTakePicture(bitmap);
                    }
                    break;
                case WL_MSG_OUT_RENDER_CB:
                    if (wlPlayer.onMediaInfoListener != null) {
                        WlOutRenderBean outRenderBean = (WlOutRenderBean) msg.obj;
                        wlPlayer.onMediaInfoListener.onOutRenderInfo(outRenderBean);
                    }
                    break;
            }
        }
    }

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

    private static synchronized void loadLibrary() {
        try {
            System.loadLibrary("wlplayer_v3.0.0");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

}
