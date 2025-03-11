import { WlPlayerNapi } from "libwlplayer.so";
import { WlMediaInfoBean } from "./bean/WlMediaInfoBean";
import { WlTrackInfoBean } from "./bean/WlTrackInfoBean";
import { WlAlphaVideoType } from "./enums/WlAlphaVideoType";
import { WlAudioChannelType } from "./enums/WlAudioChannelType";
import { WlCodecType } from "./enums/WlCodecType";
import { WlMirrorType } from "./enums/WlMirrorType";
import { WlPitchType } from "./enums/WlPitchType";
import { WlPlayModel } from "./enums/WlPlayModel";
import { WlRotateType } from "./enums/WlRotateType";
import { WlSampleRate } from "./enums/WlSampleRate";
import { WlScaleType, WlScaleTypeInfoMap } from "./enums/WlScaleType";
import { WlSeekType } from "./enums/WlSeekType";
import { WlSourceType } from "./enums/WlSourceType";
import { WlOnBufferDataListener } from "./listener/WlOnBufferDataListener";
import { WlOnMediaInfoListener } from "./listener/WlOnMediaInfoListener";
import { WlOnOutPcmDataListener } from "./listener/WlOnOutPcmDataListener";
import { image } from "@kit.ImageKit";
import { WlLog } from "./log/WlLog";
import { WlTrackType } from "./enums/WlTrackType";

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/11/11
 */
export class WlPlayer {
  private wlPlayerNapi: WlPlayerNapi;
  private mediaInfoBean: WlMediaInfoBean | null = null;
  private onMediaInfoListener: WlOnMediaInfoListener | null = null;
  private onBufferDataListener: WlOnBufferDataListener | null = null;
  private onOutPcmDataListener: WlOnOutPcmDataListener | null = null;

  constructor() {
    /**
     * napi实例对象
     */
    this.wlPlayerNapi = new WlPlayerNapi();
    this.initListenersInner();
  }

  /**
   * 播放基本信息回调
   * @param onMediaInfoListener
   */
  public setOnMediaInfoListener(onMediaInfoListener: WlOnMediaInfoListener): void {
    this.onMediaInfoListener = onMediaInfoListener;
  }

  /**
   *  buffer 数据读取回调
   * @param onBufferDataListener
   */
  public setOnBufferDataListener(onBufferDataListener: WlOnBufferDataListener): void {
    this.onBufferDataListener = onBufferDataListener;
  }

  /**
   * pcm 信息回调
   * @param onOutPcmDataListener
   */
  public setOnOutPcmDataListener(onOutPcmDataListener: WlOnOutPcmDataListener): void {
    this.onOutPcmDataListener = onOutPcmDataListener;
  }

  /**
   *  设置数据源类型
   * @param source
   */
  public setSource(source: String) {
    this.wlPlayerNapi.n_wlPlayer_setSource(source);
  }

  /**
   * 获取数据源
   * @returns
   */
  public getSource(): string {
    return this.wlPlayerNapi.n_wlPlayer_getSource();
  }

  /**
   * 设置数据源类型
   * @param sourceType
   */
  public setSourceType(sourceType: WlSourceType) {
    this.wlPlayerNapi.n_wlPlayer_setSourceType(sourceType);
  }

  /**
   * 获取数据源类型
   * @returns
   */
  public getSourceType(): WlSourceType {
    return this.wlPlayerNapi.n_wlPlayer_getSourceType();
  }

  /**
   *  设置音频重采样采样率
   * @param sampleRateType
   */
  public setSampleRate(sampleRateType: WlSampleRate) {
    this.wlPlayerNapi.n_wlPlayer_setAudioSampleRate(sampleRateType);
  }

  /**
   * 获取音频采样率
   * @returns
   */
  public getSampleRate(): WlSampleRate {
    return this.wlPlayerNapi.n_wlPlayer_getAudioSampleRate();
  }

  /**
   * 设置视频缩放宽高比例
   * @param uniqueNum xComponent 唯一值
   * @param scaleType 泛型类型
   */
  public setVideoScale(uniqueNum: number, scaleType: WlScaleType) {
    const type = WlScaleTypeInfoMap[scaleType]
    this.wlPlayerNapi.n_wlPlayer_setVideoScale(type.scaleWidth, type.scaleHeight, uniqueNum);
  }

  /**
   * 设置视频缩放宽高比例
   * @param uniqueNum xComponent 唯一值
   * @param scaleWidth 宽比值
   * @param scaleHeight 高比值
   */
  public setVideoScaleValue(uniqueNum: number, scaleWidth: number, scaleHeight: number) {
    this.wlPlayerNapi.n_wlPlayer_setVideoScale(scaleWidth, scaleHeight, uniqueNum);
  }

  /**
   * 获取视频缩放比例 宽
   * @param uniqueNum
   * @returns
   */
  public getVideoScaleWidth(uniqueNum: number): number {
    return this.wlPlayerNapi.n_wlPlayer_getVideoScaleWidth(uniqueNum);
  }

  /**
   * 获取视频缩放比例 高
   * @param uniqueNum
   * @returns
   */
  public getVideoScaleHeight(uniqueNum: number): number {
    return this.wlPlayerNapi.n_wlPlayer_getVideoScaleHeight(uniqueNum);
  }

  /**
   * 设置旋转角度
   * @param uniqueNum
   * @param rotateType
   */
  public setVideoRotate(uniqueNum: number, rotateType: WlRotateType) {
    this.wlPlayerNapi.n_wlPlayer_setVideoRotate(rotateType, uniqueNum);
  }

  /**
   * 获取旋转角度
   * @param uniqueNum
   * @returns
   */
  public getVideoRotate(uniqueNum: number): WlRotateType {
    return this.wlPlayerNapi.n_wlPlayer_getVideoRotate(uniqueNum);
  }

  /**
   * 设置镜像
   * @param uniqueNum
   * @param mirrorType
   */
  public setVideoMirror(uniqueNum: number, mirrorType: WlMirrorType) {
    this.wlPlayerNapi.n_wlPlayer_setVideoMirror(mirrorType, uniqueNum);
  }

  /**
   * 获取镜像
   * @param uniqueNum
   * @returns
   */
  public getVideoMirror(uniqueNum: number): WlMirrorType {
    return this.wlPlayerNapi.n_wlPlayer_getVideoMirror(uniqueNum);
  }

  /**
   *  设置音调(默认)
   * @param pitch [0.25 ~ 4]
   */
  public setPitch(pitch: number) {
    this.setPitchType(pitch, WlPitchType.WL_PITCH_NORMAL)
  }

  /**
   * 设置音调
   * @param pitch
   * @param pitchType
   *        WL_PITCH_NORMAL[0.25,4.0]
   *        WL_PITCH_SEMITONES[-12,12]
   *        WL_PITCH_OCTAVES[-1,1]
   */
  public setPitchType(pitch: number, pitchType: WlPitchType) {
    this.wlPlayerNapi.n_wlPlayer_setPitch(pitch, pitchType);
  }

  /**
   * 获取音调
   * @returns
   */
  public getPitch(): number {
    return this.wlPlayerNapi.n_wlPlayer_getPitch();
  }

  /**
   * 获取音调类型
   * @returns
   */
  public getPitchType(): WlPitchType {
    return this.wlPlayerNapi.n_wlPlayer_getPitchType();
  }

  /**
   * 设置播放速度
   * @param speed [0.25 ~ 4]
   */
  public setSpeed(speed: number) {
    this.wlPlayerNapi.n_wlPlayer_setSpeed(speed);
  }

  /**
   * 获取播放速度
   * @returns
   */
  public getSpeed(): number {
    return this.wlPlayerNapi.n_wlPlayer_getSpeed();
  }

  /**
   * 设置解码类型
   * @param codecType
   */
  public setCodecType(codecType: WlCodecType) {
    this.wlPlayerNapi.n_wlPlayer_setCodecType(codecType);
  }

  /**
   * 获取解码类型
   * @returns
   */
  public getCodecType(): WlCodecType {
    return this.wlPlayerNapi.n_wlPlayer_getCodecType();
  }

  /**
   * 设置播放模式
   * @param playModel
   */
  public setPlayModel(playModel: WlPlayModel) {
    this.wlPlayerNapi.n_wlPlayer_setPlayModel(playModel);
  }

  /**
   * 获取视频播放模式
   * @returns
   */
  public getPlayModel(): WlPlayModel {
    return this.wlPlayerNapi.n_wlPlayer_getPlayModel();
  }

  /**
   * 设置视频播放完（或停止）时是否清屏
   * @param uniqueNum
   * @param clear
   */
  public setClearLastVideoFrame(uniqueNum: number, clear: boolean) {
    this.wlPlayerNapi.n_wlPlayer_setClearLastVideoFrame(clear, uniqueNum);
  }

  /**
   * 是否视频播放完成（或停止）时清屏
   * @param uniqueNum
   * @returns
   */
  public isClearLastVideoFrame(uniqueNum: number): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isClearLastVideoFrame(uniqueNum);
  }

  /**
   * 设置缓冲大小 单位 秒（s) maxBufferWaitToPlay >= minBufferToPlay >= 0
   * @param minBufferToPlay
   * @param maxBufferWaitToPlay
   */
  public setBufferSize(minBufferToPlay: number, maxBufferWaitToPlay: number) {
    this.wlPlayerNapi.n_wlPlayer_setBufferSize(minBufferToPlay, maxBufferWaitToPlay);
  }

  /**
   * 获取设置最小缓存大小
   * @returns
   */
  public getMinBufferToPlay(): number {
    return this.wlPlayerNapi.n_wlPlayer_getMinBufferToPlay();
  }

  /**
   * 获取设置最大缓存大小
   * @returns
   */
  public getMaxBufferWaitToPlay(): number {
    return this.wlPlayerNapi.n_wlPlayer_getMaxBufferWaitToPlay();
  }


  /**
   * 设置 音视频同步 偏移
   *
   * @param syncOffset > 0, 视频快于音频；< 0, 视频慢于音频 [-1, 1]
   */
  public setSyncOffset(syncOffset: number) {
    this.wlPlayerNapi.n_wlPlayer_setSyncOffset(syncOffset);
  }

  /**
   * 获取 音视频同步 偏移
   * @returns
   */
  public getSyncOffset(): number {
    return this.wlPlayerNapi.n_wlPlayer_getSyncOffset();
  }

  /**
   * 获取所有的音频轨道信息
   * @returns
   */
  public getAudioTracks(): WlTrackInfoBean[] | undefined {
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.audioTracks
  }

  /**
   * 获取所有的视频轨道信息
   * @returns
   */
  public getVideoTracks(): WlTrackInfoBean[] | undefined {
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.videoTracks
  }

  /**
   * 获取所有字幕轨道信息
   * @returns
   */
  public getSubtitleTracks(): WlTrackInfoBean[] | undefined {
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.subtitleTracks
  }

  public setMediaTrackIndex(audioTrackIndex: number, videoTrackIndex: number, subtitleTrackIndex: number) {
    this.wlPlayerNapi.n_wlPlayer_setMediaTrackIndex(audioTrackIndex, videoTrackIndex, subtitleTrackIndex);
  }

  /**
   * 设置音频轨道
   * @param audioTrackIndex
   */
  public setAudioTrackIndex(audioTrackIndex: number) {
    this.setMediaTrackIndex(audioTrackIndex, -1, -1);
  }

  /**
   * 获取当前音频轨道
   * @param mediaType
   * @returns
   */
  public getAudioTrackIndex(mediaType: WlTrackType): number {
    return this.wlPlayerNapi.n_wlPlayer_getMediaTrackIndex(mediaType);
  }

  /**
   * 设置字幕轨道
   * @param subtitleTrackIndex
   */
  public setSubtitleTrackIndex(subtitleTrackIndex: number) {
    this.setMediaTrackIndex(-1, -1, subtitleTrackIndex);
  }

  /**
   * 获取当前字幕轨道
   * @param mediaType
   * @returns
   */
  public getSubtitleTrackIndex(mediaType: WlTrackType): number {
    return this.wlPlayerNapi.n_wlPlayer_getMediaTrackIndex(mediaType);
  }

  /**
   * 设置视频轨道
   * @param videoTrackIndex
   */
  public setVideoTrackIndex(videoTrackIndex: number) {
    this.setMediaTrackIndex(-1, videoTrackIndex, -1);
  }

  /**
   * 获取当前视频轨道
   * @param mediaType
   * @returns
   */
  public getVideoTrackIndex(mediaType: WlTrackType): number {
    return this.wlPlayerNapi.n_wlPlayer_getMediaTrackIndex(mediaType);
  }

  /**
   * 设置解码 timeBase
   *
   * @param timeBase [250000 ~ 1000000]
   */
  public setCodecTimeBase(timeBase: number) {
    this.wlPlayerNapi.n_wlPlayer_setCodecTimeBase(timeBase);
  }

  /**
   * 获取解码 timeBase
   * @returns
   */
  public getCodecTimeBase(): number {
    return this.wlPlayerNapi.n_wlPlayer_getCodecTimeBase();
  }

  /**
   * 设置 option
   * @param key
   * @param value
   */
  public setOptions(key: string, value: string) {
    this.wlPlayerNapi.n_wlPlayer_setOptions(key, value);
  }

  /**
   * 清除所有 option
   */
  public clearOptions() {
    this.wlPlayerNapi.n_wlPlayer_clearOptions();
  }

  /**
   * 设置透明视频类型
   * @param alphaVideoType
   */
  public setAlphaVideoType(alphaVideoType: WlAlphaVideoType) {
    this.wlPlayerNapi.n_wlPlayer_setAlphaVideoType(alphaVideoType);
  }

  /**
   * 获取透明视频类型
   * @returns
   */
  public getAlphaVideoType(): WlAlphaVideoType {
    return this.wlPlayerNapi.n_wlPlayer_getAlphaVideoType();
  }

  /**
   * 设置视频渲染帧率
   *
   * @param frameRate (20 ~ 60)
   */
  public setRenderFrameRate(frameRate: number) {
    this.wlPlayerNapi.n_wlPlayer_setRenderFrameRate(frameRate);
  }

  /**
   * 获取视频渲染帧率
   * @returns
   */
  public getRenderFrameRate(): number {
    return this.wlPlayerNapi.n_wlPlayer_getRenderFrameRate();
  }

  /**
   * 设置循环播放
   * @param loop
   */
  public setLoopPlay(loop: boolean) {
    this.wlPlayerNapi.n_wlPlayer_setLoopPlay(loop);
  }

  /**
   * 是否循环播放
   * @returns
   */
  public isLoopPlay(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isLoopPlay();
  }

  /**
   * 设置超时时间
   *
   * @param timeOut 单位：秒（s)
   */
  public setTimeOut(timeOut: number) {
    this.wlPlayerNapi.n_wlPlayer_setTimeOut(timeOut);
  }

  /**
   * 获取超时时间
   * @returns
   */
  public getTimeOut(): number {
    return this.wlPlayerNapi.n_wlPlayer_getTimeOut();
  }

  /**
   * 设置是否开启buffer帧解码模式
   * @param enable
   */
  public setBufferDeEncrypt(enable: boolean) {
    this.wlPlayerNapi.n_wlPlayer_setBufferDeEncrypt(enable);
  }

  /**
   * 获取是否开启buffer解码模式
   * @returns
   */
  public isBufferDeEncrypt(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isBufferDeEncrypt();
  }

  /**
   * 如果触发丢帧，表示连续丢帧次数
   *
   * @param count 0：不丢帧
   */
  public setDropFrameCount(count: number) {
    this.wlPlayerNapi.n_wlPlayer_setDropFrameCount(count);
  }

  /**
   * 获取连续丢帧次数
   * @returns
   */
  public getDropFrameCount(): number {
    return this.wlPlayerNapi.n_wlPlayer_getDropFrameCount();
  }

  /**
   * 设置自动播放
   * @param autoPlay
   */
  public setAutoPlay(autoPlay: boolean) {
    this.wlPlayerNapi.n_wlPlayer_setAutoPlay(autoPlay);
  }

  /**
   * 获取是否自动播放
   * @returns
   */
  public isAutoPlay(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isAutoPlay();
  }

  /**
   * 截图
   */
  public takePicture() {
    this.wlPlayerNapi.n_wlPlayer_takePicture();
  }

  /**
   * 设置音量
   *
   * @param percent [0, 200]
   */
  public setVolume(percent: number) {
    this.wlPlayerNapi.n_wlPlayer_setVolume(percent, true);
  }

  /**
   * 获取音量值
   * @returns
   */
  public getVolume(): number {
    return this.wlPlayerNapi.n_wlPlayer_getVolume();
  }

  /**
   * 设置声道类型
   *
   * @param audioChannelType 立体声 单左声道 单右声道 双左声道 双右声道
   */
  public setAudioChannelType(audioChannelType: WlAudioChannelType) {
    this.wlPlayerNapi.n_wlPlayer_setAudioChannelType(audioChannelType);
  }

  /**
   * 获取声道类型
   * @returns
   */
  public getAudioChannelType(): WlAudioChannelType {
    return this.wlPlayerNapi.n_wlPlayer_getAudioChannelType();
  }

  /**
   * 设置实时回调pcm数据
   *
   * @param enable true: 开启 false: 关闭
   */
  public setPcmCallbackEnable(enable: boolean) {
    this.wlPlayerNapi.n_wlPlayer_setPcmCallbackEnabled(enable);
  }

  /**
   * 是否实时回调pcm数据
   *
   * @return
   */
  public isPcmCallbackEnable(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isPcmCallbackEnable();
  }

  /**
   * 异步准备
   */
  public prepare() {
    this.wlPlayerNapi.n_wlPlayer_prepare();
  }

  /**
   * 开始播放
   */
  public start() {
    this.wlPlayerNapi.n_wlPlayer_start();
  }

  /**
   * 停止
   */
  public stop() {
    this.wlPlayerNapi.n_wlPlayer_stop();
  }

  /**
   * 是否播放中
   *
   * @return
   */
  public isPlaying(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isPlaying();
  }

  /**
   * 销毁
   */
  public release() {
    this.wlPlayerNapi.n_wlPlayer_release();
  }

  /**
   * 设置 surface
   *
   * @param surface   surface
   * @param rgba      清屏颜色 eg: #000000FF
   * @param uniqueNum 每个view的唯一值
   */
  public setSurface(surfaceId: string, rgba: string, uniqueNum: number) {
    this.wlPlayerNapi.n_wlPlayer_updateSurface(surfaceId, rgba, uniqueNum);
  }

  /**
   * seek 到指定时间
   * @param seekTime
   * @param type seek 类型
   */
  public seekByType(seekTime: number, type: WlSeekType) {
    this.wlPlayerNapi.n_wlPlayer_seek(seekTime, type);
  }

  /**
   * seek 到指定时间，默认seek模式
   *
   * @param seekTime
   */
  public seek(seekTime: number) {
    this.wlPlayerNapi.n_wlPlayer_seek(seekTime, WlSeekType.WL_SEEK_FAST);
  }

  /**
   * seek开始，调用后会屏蔽时间回调，避免seek中进度条异常跳转
   *
   * @param seekStart true: 屏蔽回调
   */
  public seekStart(seekStart: boolean = true) {
    this.wlPlayerNapi.n_wlPlayer_setSeekStart(seekStart);
  }

  /**
   * 是否 seek 开始了
   *
   * @return
   */
  public isSeekStart(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isSeekStart();
  }

  /**
   * 暂停
   */
  public pause() {
    this.wlPlayerNapi.n_wlPlayer_pause();
  }

  /**
   * 是否暂停
   * @returns
   */
  public isPause(): boolean {
    return this.wlPlayerNapi.n_wlPlayer_isPause();
  }

  /**
   * 继续播放
   */
  public resume() {
    this.wlPlayerNapi.n_wlPlayer_resume();
  }

  /**
   * 获取时长
   *
   * @return
   */
  public getDuration(): number {
    return this.wlPlayerNapi.n_wlPlayer_getDuration();
  }

  /**
   * 设置时间回调间隔，默认 1s/次
   *
   * @param seconds [0.01 ~ 5]
   */
  public setTimeInfoInterval(seconds: number) {
    this.wlPlayerNapi.n_wlPlayer_setTimeInterval(seconds);
  }

  /**
   * 获取时间回调间隔
   * @returns
   */
  public getTimeInfoInterval(): number {
    return this.wlPlayerNapi.n_wlPlayer_getTimeInterval();
  }

  /**
   * 获取当前时间
   * @returns
   */
  public getCurrentTime(): number {
    return this.wlPlayerNapi.n_wlPlayer_getCurrentTime();
  }

  /**
   * 获取缓存时间
   * @returns
   */
  public getBufferTime(): number {
    return this.wlPlayerNapi.n_wlPlayer_getBufferTime();
  }

  /**
   * 获取解码类型
   *
   * @return -1 未设置
   *          1 软解
   *          2 硬解
   */
  public getVideoCodecRealType(): number {
    return this.wlPlayerNapi.n_wlPlayer_getVideoCodecRealType();
  }

  private initListenersInner() {

    this.wlPlayerNapi.n_wlPlayer_setOnPreparedListener((mediaInfo: object) => {
      this.mediaInfoBean = mediaInfo as WlMediaInfoBean;
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onPrepared();
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnCompleteListener((type: number, msg: string) => {
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onComplete(type, msg);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnLoadStatusListener((type: number, progress: number, speed: number) => {
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onLoadInfo(type, progress, speed);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnTimeInfoListener((currentTime: number, bufferTime: number) => {
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onTimeInfo(currentTime, bufferTime);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnFirstFrameRenderedListener(() => {
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onFirstFrameRendered();
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnSeekFinishListener(() => {
      if (this.onMediaInfoListener != null) {
        this.onMediaInfoListener.onSeekFinish();
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnAutoPlayListener((mediaInfo: object) => {
      this.mediaInfoBean = mediaInfo as WlMediaInfoBean;
      if (this.onMediaInfoListener != null && this.onMediaInfoListener.onAutoPlay != null) {
        this.onMediaInfoListener.onAutoPlay();
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnIOBufferInitListener(() => {
      if (this.onBufferDataListener != null) {
        let length = this.onBufferDataListener.onBufferByteLength();
        this.wlPlayerNapi.n_wlPlayer_sendBufferByteLength(length);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnIOBufferReadListener((position: number, bufferSize: number) => {
      if (this.onBufferDataListener != null) {
        let buffer = this.onBufferDataListener.onBufferByteData(position, bufferSize);
        this.wlPlayerNapi.n_wlPlayer_sendBufferByteRead(buffer);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnOutPcmInitInfoListener((bit: number, channel: number, sampleRate: number) => {
      if (this.onOutPcmDataListener != null) {
        this.onOutPcmDataListener.onOutPcmInfo(bit, channel, sampleRate);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnOutPcmBufferInfoListener((size: number, buffers: Uint8Array, db: number) => {
      if (this.onOutPcmDataListener != null) {
        this.onOutPcmDataListener.onOutPcmBuffer(size, buffers, db);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnBufferDeEncryptListener((mediaType: number, buffer: Uint8Array) => {
      if (this.onMediaInfoListener != null && this.onMediaInfoListener.onDeEncryptData != null) {
        let bu = this.onMediaInfoListener.onDeEncryptData(mediaType, buffer);
        this.wlPlayerNapi.n_wlPlayer_sendBufferDeEncrypt(mediaType, bu);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnOutRenderTextureListener((textureId: number, videoWidth: number,
      videoHeight: number, videoRotate: number) => {
      if (this.onMediaInfoListener != null && this.onMediaInfoListener.onOutRenderTexture != null) {
        this.onMediaInfoListener.onOutRenderTexture(textureId, videoWidth, videoHeight, videoRotate);
      }
    });

    this.wlPlayerNapi.n_wlPlayer_setOnTakePictureListener((pixelMap: object) => {
      WlLog.d("setOnTakePictureListener call 5");
      if (this.onMediaInfoListener != null && this.onMediaInfoListener.onTakePicture != null) {
        WlLog.d("setOnTakePictureListener call 6");
        this.onMediaInfoListener.onTakePicture(pixelMap as image.PixelMap);
      }
    });
  }
}