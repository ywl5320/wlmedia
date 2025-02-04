## WlPlayer API
WlPlayer 是一款HarmonyOS平台的功能丰富的播放器SDK，可以非常方便快捷的实现音视频播放，其用法如下：
### 一 构造函数
`WlPlayer` 构造函数不依赖上下文，很方便创建使用，当创建了 `WlPlayer` 实例后，都需要调用 `release` 释放资源。
#### 1.1 无参构造函数
```typescript
public WlPlayer()
```

### 二 设置XComponent
播放器自带 `WlComponentController` 控制器，用于绑定播放器和`XComponent`组件
#### 2.1 使用`XComponent`渲染
##### 2.1.1 创建控制器
```typescript
let wlPlayer: WlPlayer = new WlPlayer();
let wlComponentController: WlComponentController = new WlComponentController(wlPlayer);
// or
let wlComponentController: WlComponentController = new WlComponentController(wlPlayer, "#000000FF");//设置背景颜色，默认黑色
```
##### 2.1.2 关联`XComponent`组件
```typescript
XComponent({
    type: XComponentType.SURFACE, // 透明视频使用: TEXTURE
    controller: this.wlComponentController
})
```

### 三 设置回调
#### 3.1 播放信息回调
```typescript
public setOnMediaInfoListener(onMediaInfoListener: WlOnMediaInfoListener): void
```
##### 3.1.1 回调函数方法解释
```typescript
export interface WlOnMediaInfoListener {

    /**
     * 异步准备好
     * 当不是自动播放 setAutoPlay(false) 时，调用 prepare 异步准备好后会回调此函数，在此函数中可以调用 start() 方法即可开始播放。
     */
    void onPrepared();

    /**
     * 时间回调
     *
     * @param currentTime 当前播放时间
     * @param bufferTime 缓存时长
     */
    void onTimeInfo(double currentTime, double bufferTime);

    /**
     * complete回调
     *
     * @param wlCompleteType 表示 Complete 类型，具体值，参考 WlCompleteType 枚举类
     * @param msg 对应 Complete 类型的原因
     */
    void onComplete(WlCompleteType wlCompleteType, String msg);

    /**
     * 加载回调接口
     *
     * @param loadStatus 加载状态（开始加载、加载中、加载完成）
     * @param progress   加载进度 (0~100)
     * @param speed      加载速度 KB/s
     */
    void onLoad(WlLoadStatus loadStatus, int progress, long speed);

    /**
     * 首帧渲染回调
     */ 
    default void onFirstFrameRendered() {
    }

    /**
     * 自动播放回调，当设置 setAutoPlay(true) 时，异步准备好后会回调此函数，并自动播放，不会再回调 onPrepared()。
     */
    default void onAutoPlay() {
    }

    /**
     * seek 完成回调
     */
    default void onSeekFinish() {
    }

    /**
     * 加密数据解密回调 （子线程）
     *
     * @param encryptBuffer 加密前的数据
     * @param position 当前 encryptBuffer 对应文件的起始位置
     * @return 解密后的数据
     */
    default byte[] decryptBuffer(byte[] encryptBuffer, long position) {
        return encryptBuffer;
    }

    /**
     * 截图回调
     *
     * @param bitmap
     */
    default void onTakePicture(Bitmap bitmap) {
    }

    /**
     * 外部渲染信息回调（OpenGL）
     * 
     * @param textureId OpenGL纹理，可用于Unity，cocos等显示视频
     * @param videoWidth 视频宽
     * @param videoHeight 视频高
     * @param videoRotate 视频旋转角度
     */
    default void onOutRenderInfo(int textureId, int videoWidth, int videoHeight, int videoRotate) {
    }
}
```
#### 3.2 byte[]类型播放回调
```typescript
public setOnBufferDataListener(onBufferDataListener: WlOnBufferDataListener): void
```
##### 3.2.1 回调函数方法解释
```typescript
/**
 * 返回buffer总长度（子线程）
 * 
 * @return 
 *        如果是实时场景，可以返回0
 *        如果是视频文件，返回文件长度
*/
onBufferByteLength(): number;

/**
 * 根据position返回buffersize长度的数据 （子线程）
 * 
 * @param position 当前读取数据起始位置
 * @param bufferSize 从position位置开始，需要读取的数据大小，如果不足，则返回实际数据大小的byte[]
 * 
 * @return 返回对应的buffer数据
*/
onBufferByteData(position: number, bufferSize: number): Uint8Array | null;
```
#### 3.3 音频PCM实时数据回调
```typescript
public setOnOutPcmDataListener(onOutPcmDataListener: WlOnOutPcmDataListener): void
```
##### 3.3.1 回调函数方法解释
```typescript
/**
 * 音频基本信息（子线程）
 * 
 * @param bit 音频位深
 * @param channel 音频声道数
 * @param sampleRate 音频采样率
 */
onOutPcmInfo(bit: number, channel: number, sampleRate: number);

/**
 * 音频PCM实时数据（子线程）
 * 
 * @param size PCM数据大小
 * @param buffers PCM数据
 * @param db 音频音量分贝[0~96]
*/
onOutPcmBuffer(size: number, buffers: Uint8Array, db: number);
```

### 四 常用方法
#### 4.1 数据源
数据源支持`本地文件`、`网络`、`buffer`等。

##### 4.1.1 设置数据源
```typescript
public setSource(source: String)
```
##### 4.1.2 获取数据源
```typescript
public getSource(): string
```

#### 4.2 数据源类型
数据源类型，表示当前播放器采用的数据源模式，枚举值如下：
```typescript
export enum WlSourceType {
  WL_SOURCE_NORMAL = 0, // 常规播放
  WL_SOURCE_BUFFER = 1 // 播放byte[]
}
```
- WL_SOURCE_NORMAL：常规播放，根据传入的 source 进行自动识别播放。
- WL_SOURCE_BUFFER：播放 buffer 类型的数据，数据通过 WlOnBufferDataListener回调返回。
##### 4.2.1 设置数据源类型
```typescript
public setSourceType(sourceType: WlSourceType) 
```
##### 4.2.2 获取数据源类型
```typescript
public getSourceType(): WlSourceType
```
#### 4.3 异步准备
当设置了数据源后，就可以调用异步准备开始播放音视频了。
```typescript
public prepare()
```
#### 4.4 开始播放
当异步准备成功后，在不设置自动播放的情况下，会回调 `onPrepared()` 方法，在 `onPrepared()` 方法中可以调用 `start()` 启动播放。只有异步准备好后调用一次 `start()` 即可，后续暂停后，调用 `resume()` 恢复播放。
```typescript
public start()
```

#### 4.5 暂停播放
##### 4.5.1 暂停
```typescript
public pause()
```
##### 4.5.2 是否暂停中
```typescript
public isPause(): boolean
```

#### 4.6 暂停后恢复播放
当播放器 `pause()` 后，要恢复播放，调用 `resume()`，不要调用 `start()` 。
```typescript
public resume()
```

#### 4.7 停止播放
主动调用 `stop()` 停止播放，会触发 `onComplete` 回调，回调类型为：`WlCompleteType.WL_COMPLETE_HANDLE` 表示主动停止。
```typescript
public stop()
```

#### 4.8 销毁资源
只要创建了实例，就要调用release销毁:
- 在播放中，调用 `release` 销毁资源，会触发 `onComplete` 回调，回调类型为：`WlCompleteType.WL_COMPLETE_RELEASE` 表示释放资源停止。
- 当停止后，调用 `release` 销毁资源，不会触发任何回调。
```typescript
public release()
```

#### 4.9 播放时间回调间隔
默认播放时间回调间隔为 1s , 当需要更精确的时间回调来处理业务时，可以通过此函数设置新的回调间隔，设置范围为：【0.01,5.0】秒。
##### 4.9.1 设置播放时间回调间隔
```typescript
public setTimeInfoInterval(seconds: number)
```
##### 4.9.2 获取当前播放时间回调间隔
```typescript
public getTimeInfoInterval(): number
```

#### 4.10 获取当前播放时间
单位： 秒。
```typescript
public getCurrentTime(): number
```

#### 4.11 获取当前缓冲时间
单位：秒。
```typescript
public getBufferTime(): number
```

#### 4.12 获取当前媒体总时长
单位：秒。
```typescript
public getDuration(): number
```

#### 4.13 seek 跳转
`seek` 指定跳转到相应时间（单位：秒），当 `seek` 完成后，会回调 `onSeekFinish` 方法，跳转支持三种模式, 枚举值如下：
```typescript
export enum WlSeekType {
  WL_SEEK_FAST = 0,// 快速模式
  WL_SEEK_NORMAL = 1,// 常规模式
  WL_SEEK_ACCURATE = 2// 精确模式
}
```
- WL_SEEK_FAST：快速模式，seek 完成后马上开始播放，不用等待音视频时间戳对其。 -- 速度快
- WL_SEEK_NORMAL：常规模式，seek 完成后，会等待音频视频的时间同步后，才开始播。 -- 速度中等
- WL_SEEK_ACCURATE：精确模式，seek 完成后，会等待音频视频时间同步并且大于等于 seek 时间后，才开始播放。 -- 速度慢
```typescript
public seek(seekTime: number)
public seekByType(seekTime: number, type: WlSeekType)
```

#### 4.14 设置 seekStart
当 `seek` 时，时间回调同步在进行，此时可能会出现 `seek bar` 进度异常跳转，当 `seek` 时，先设置 `seekStart` 后，会暂时禁用时间回调，此时等待 `seek `完成后才会重新回调时间：
```typescript
public seekStart(seekStart: boolean = true)
```

#### 4.15 指定播放音频或视频
指定需要播放媒体资源播放模式，支持三种播放模式，枚举值如下：
```typescript
export enum WlPlayModel {
  PLAYMODEL_AUDIO_VIDEO = 0,
  PLAYMODEL_ONLY_AUDIO = 1,
  PLAYMODEL_ONLY_VIDEO = 2
}
```
- WL_PLAY_MODEL_AUDIO_VIDEO：同时播放音频或视频，如果媒体资源只有音频或视频，也只会播放音频或视频。
- WL_PLAY_MODEL_ONLY_AUDIO：只播放音频流。
- WL_PLAY_MODEL_ONLY_VIDEO：只播放视频流。
##### 4.15.1 设置播放模式
```typescript
public setPlayModel(playModel: WlPlayModel)
```
##### 4.15.2 获取播放模式
```typescript
public getPlayModel(): WlPlayModel
```

#### 4.16 指定视频解码模式
解码模式支持2中，硬解码优先和使用软解码，只针对视频解码有效，枚举值如下：
```typescript
export enum WlCodecType {
  WL_CODEC_AUTO = 0,
  WL_CODEC_SOFT = 1
}
```
- WL_CODEC_AUTO：硬解码优先，会自动检测是否支持硬解码，当不支持硬解码时，会使用软解码。
- WL_CODEC_SOFT：只使用软解码。
##### 4.16.1 设置视频解码模式
```typescript
public setCodecType(codecType: WlCodecType)
```
##### 4.16.2 获取视频解码模式
```typescript
public getCodecType(): WlCodecType
```

#### 4.17 指定音频采样率
硬件播放音频数据，其采样率是有一定的范围的，而音频文件的采样率是多种多样的，如果不重采样，可能会出现播放异常等情况，可以对播放音频指定采样率，提高应用兼容性，指定采样率枚举如下：
```typescript
export enum WlSampleRate {
  SAMPLE_RATE_NONE = 0,
  SAMPLE_RATE_8000 = 8000,
  SAMPLE_RATE_11025 = 11025,
  SAMPLE_RATE_12000 = 12000,
  SAMPLE_RATE_16000 = 16000,
  SAMPLE_RATE_22050 = 22050,
  SAMPLE_RATE_24000 = 24000,
  SAMPLE_RATE_32000 = 32000,
  SAMPLE_RATE_44100 = 44100,
  SAMPLE_RATE_48000 = 48000,
}
```
常用采样率为 `WL_SAMPLE_RATE_44100`、`WL_SAMPLE_RATE_48000`，几乎所有的设备都支持。
##### 4.17.1 设置音频采样率
```typescript
public setSampleRate(sampleRateType: WlSampleRate)
```
##### 4.17.2 获取当前音频采样率
```typescript
public getSampleRate(): WlSampleRate
```

#### 4.18 指定音频播放速度
设置音频播放速度，速度范围为 [0.25,4.0]
##### 4.18.1 设置音频播放速度
```typescript
public setSpeed(speed: number)
```
##### 4.18.2 获取音频播放速度
```typescript
public getSpeed(): number
```

#### 4.19 指定音频播放音调
音频播放音调有三种模式，枚举值为：
```typescript
export enum WlPitchType {
  WL_PITCH_NORMAL = 0,
  WL_PITCH_SEMITONES = 1,
  WL_PITCH_OCTAVES = 2
}
```
- WL_PITCH_NORMAL：默认音调模式，范围 [0.25,4.0]。
- WL_PITCH_SEMITONES: SEMITONES模式，范围 [-12,12]。
- WL_PITCH_OCTAVES：OCTAVES模式，范围 [-1,1]。
##### 4.19.1 设置音频音调
```typescript
public setPitch(pitch: number)
public setPitchType(pitch: number, pitchType: WlPitchType)
```
##### 4.19.2 获取音频音调类型
```typescript
public getPitchType(): WlPitchType
```
##### 4.19.2 获取音频音调值
```typescript
public getPitch(): number
```

#### 4.20 指定视频缩放比例
视频缩放比例默认有4中模式，枚举值为：
```typescript
export enum WlScaleType {
  WL_SCALE_16_9 = "16:9",
  WL_SCALE_4_3 = "4:3",
  WL_SCALE_FIT = "FIT(长宽适配)",
  WL_SCALE_MATCH = "MATCH（拉伸填充）"
}
```
- WL_SCALE_16_9：宽高比为 16:9。
- WL_SCALE_4_3：宽高比为 4:3。
- WL_SCALE_FIT：宽高比为默认值。
- WL_SCALE_MATCH：宽高比为拉伸填充整个Surface。
##### 4.20.1 设置视频缩放模式
```typescript
/**
 * uniqueNum 为surface对应的唯一值，可分别对每个surface设置缩放
*/
public setVideoScale(uniqueNum: number, scaleType: WlScaleType)
```
##### 4.20.2 自定义视频缩放比例
```typescript
public setVideoScaleValue(uniqueNum: number, scaleWidth: number, scaleHeight: number)
```
##### 4.20.3 获取缩放比例宽和高
```typescript
public getVideoScaleWidth(uniqueNum: number): number
public getVideoScaleHeight(uniqueNum: number): number
```

#### 4.21 指定视频旋转角度
视频旋转角度有4个角度模式，枚举值为：
```typescript
export enum WlRotateType {
  WL_ROTATE_DEFAULT = -1,
  WL_ROTATE_0 = 0,
  WL_ROTATE_90 = 90,
  WL_ROTATE_180 = 180,
  WL_ROTATE_270 = 270
}
```
- WL_ROTATE_DEFAULT：视频默认角度，不做任何旋转。
- WL_ROTATE_0：顺时针旋转 0 度。
- WL_ROTATE_90：顺时针旋转 90 度。
- WL_ROTATE_180：顺时针旋转 180 度。
- WL_ROTATE_270：顺时针旋转 270 度。
##### 4.21.1 设置视频旋转角度
```typescript
/**
 * uniqueNum 为surface对应的唯一值，可分别对每个surface设置旋转角度
*/
public setVideoRotate(uniqueNum: number, rotateType: WlRotateType)
```
##### 4.21.2 获取视频旋转角度
```typescript
public getVideoRotate(uniqueNum: number): WlRotateType
```

#### 4.22 指定视频镜像模式
视频镜像分为4中类型，枚举值为：
```typescript
export enum WlMirrorType {
  WL_MIRROR_NONE = 0,
  WL_MIRROR_TOP_BOTTOM = 1,
  WL_MIRROR_LEFT_RIGHT = 2,
  WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT = 3
}
```
- WL_MIRROR_NONE：不设置镜像。
- WL_MIRROR_TOP_BOTTOM：上下镜像。
- WL_MIRROR_LEFT_RIGHT：左右镜像。
- WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT：上下左右同时镜像。
##### 4.22.1 设置镜像模式
```typescript
/**
 * uniqueNum 为surface对应的唯一值，可分别对每个surface设置镜像
*/
public setVideoMirror(uniqueNum: number, mirrorType: WlMirrorType)
```
##### 4.22.2 获取镜像模式
```typescript
public getVideoMirror(uniqueNum: number): WlMirrorType
```

#### 4.23 设置播放完是否清屏
当设置清屏后，视频不会停留在最后一帧，可在视频还没有播放完成时调用。
##### 4.23.1 设置清屏
```typescript
/**
 * uniqueNum 为surface对应的唯一值，可分别对每个surface设置是否清屏
*/
public setClearLastVideoFrame(uniqueNum: number, clear: boolean)
```
##### 4.23.2 获取是否清屏
```typescript
public isClearLastVideoFrame(uniqueNum: number): boolean
```

#### 4.24 设置网络资源访问超时时长
设置访问网络资源超时时长，默认 15s，当超过时间，还没有读取到媒体资源数据，将会回调 `onComplete` 方法，类型为：`WL_COMPLETE_TIMEOUT`。
##### 4.24.1 设置超时时间
```typescript
public setTimeOut(timeOut: number)
```
##### 4.24.2 获取超时时间
```typescript
public getTimeOut(): number
```

#### 4.25 循环播放
当设置循环播放时，正常播放完成（EOF）后会自动重新播放。
##### 4.25.1 设置循环播放
```typescript
public setLoopPlay(loop: boolean)
```
##### 4.25.2 是否循环播放
```typescript
public isLoopPlay(): boolean
```

#### 4.26 自动播放
当设置自动播放后，异步 `prepare` 准备好后会自动开始播放，并且会回调 `onAutoPlay` 方法。
##### 4.26.1 设置循环播放
```typescript
public setAutoPlay(autoPlay: boolean)
```
##### 4.26.2 是否循环播放
```typescript
public isAutoPlay(): boolean
```

#### 4.27 设置音量
设置音量，可以选择是否更改音频数据本身音量值来改变音量。
##### 4.27.1 设置音量
```typescript
/**
 * 设置音量
 *
 * @param percent   百分比（0 ~ 200）
 */
public setVolume(percent: number)
```
##### 4.27.2 获取当前音量
```typescript
public getVolume(): number
```

#### 4.28 获取当前是否播放中
```typescript
public isPlaying(): boolean
```
当 `isPlaying() == true && isPause() == false` 表示真正播放中。

#### 4.29 设置缓冲大小
缓冲大小设置有2个阈值，单位都是 秒：
- minBufferToPlay：表示最小缓存多少时，才开始播放。
- maxBufferWaitToPlay：表示最大缓冲多大，在当前 播放时间上面+maxBufferWaitToPlay 表示实际缓冲时间。
```typescript
public setBufferSize(minBufferToPlay: number, maxBufferWaitToPlay: number)
```
#### 4.30 设置FF参数
有些网络流，如 `rtsp` 流需要设置使用 `tcp` 或 `udp` 网络模式才能播放，设置方式如下：
##### 4.30.1 设置参数
```typescript
public setOptions(key: string, value: string)
```
##### 4.30.2 清除所有参数
```typescript
public clearOptions()
```

#### 4.31 视频截图
调用视频截图功能后，视频快照会回调 `onTakePicture` 方法。
```typescript
public takePicture()
```

#### 4.32 透明视频播放
目前支持透明视频为：视频中用灰度值表示透明度的视频，一般是灰度值和原视频比例是一比一。透明视频枚举值为：
```typescript
export enum WlAlphaVideoType {
  WL_ALPHA_VIDEO_DEFAULT = 0,
  WL_ALPHA_VIDEO_CUSTOM = 1,
  WL_ALPHA_VIDEO_LEFT_ALPHA = 2,
  WL_ALPHA_VIDEO_RIGHT_ALPHA = 3,
  WL_ALPHA_VIDEO_TOP_ALPHA = 4,
  WL_ALPHA_VIDEO_BOTTOM_ALPHA = 5
}
```
- WL_ALPHA_VIDEO_DEFAULT：默认值，视频不带透明度。
- WL_ALPHA_VIDEO_LEFT_ALPHA：透明度视频为左边百分之50%。
- WL_ALPHA_VIDEO_RIGHT_ALPHA：透明度视频为右边百分之50%。
- WL_ALPHA_VIDEO_TOP_ALPHA：透明度视频为上边百分之50%。
- WL_ALPHA_VIDEO_BOTTOM_ALPHA透明度视频为下边百分之50%。
##### 4.32.1 设置透明视频类型
```typescript
public setAlphaVideoType(alphaVideoType: WlAlphaVideoType)
```

#### 4.33 获取媒体文件中所有流信息
可以单独获取所有音频信息、视频信息和字幕信息，信息包括：音频采样率，音频码率，视频宽高，视频码率，视频帧率等等。
##### 4.33.1 获取所有音频信息
```typescript
public getAudioTracks(): WlTrackInfoBean[] | undefined
```
##### 4.33 2 获取所以视频信息
```typescript
public getVideoTracks(): WlTrackInfoBean[] | undefined
```
##### 4.33.3 获取所有字幕信息
```typescript
public getSubtitleTracks(): WlTrackInfoBean[] | undefined
```

#### 4.34 设置音频或字幕流
当播放含有多音轨的视频会多字幕的视频时，可以选择需要播放的音频或显示的字幕类型。
##### 4.34.1 设置播放音轨
```typescript
public setAudioTrackIndex(audioTrackIndex: number)
```
##### 4.34.2 设置显示字幕
```typescript
public setSubtitleTrackIndex(subtitleTrackIndex: number)
```

#### 4.35 获取当前播放的音频、视频和字幕流信息
获取当前播放流信息，就可以方便做一些业务操作。
##### 4.35.1 获取当前播放音频流信息
```typescript
public getAudioTrackIndex(mediaType: WlTrackType): number
```
##### 4.35.2 获取当前播放视频流信息
```typescript
public getVideoTrackIndex(mediaType: WlTrackType): number
```
##### 4.35.3 获取当前播放字幕流信息
```typescript
public getSubtitleTrackIndex(mediaType: WlTrackType): number
```

#### 4.36 设置最大渲染分辨率(暂未提供)
表示硬解码时使用`OpenGL`渲染的最大分辨率，比如设置最大分辨率为：1920 * 1080，当播放超过这个分辨率的视频时，并且支持`硬解码`时将不会使用`OpenGL`进行渲染，从而提高解码性能。默认大小为：4K（3840 * 2160）。
```typescript
// public void setRenderDefaultSize(int width, int height)
```

#### 4.37 设置外部渲染环境(暂未提供)
目前仅支持使用`OpenGL`渲染的情况，如果是要用到`Unity`中，`Unity`请使用`OpenGL`渲染。
```typescript
// public void initOutRenderEnv(String name, int type, int version)
```

#### 4.38 取消外部渲染设置(暂未提供)
当不使用外部渲染时，需要销毁外部环境资源。
```typescript
//public void unInitOutRenderEnv(String name)
```

#### 4.39 设置最大渲染帧率(暂未提供)
当设置最大渲染帧率后，渲染帧率会控制在设置范围左右，来太高渲染性能，默认：60fps。
```typescript
//public void setRenderFPS(int fps)
```

#### 4.40 设置声道
有些媒体文件比如部分MV视频，其原声和伴奏是分声道存储的，当要实现原声和伴奏切换时，就需要切换声道，播放器提供声道切换，枚举值为：
```typescript
export enum WlAudioChannelType {
  WL_AUDIO_CHANNEL_CENTER = 0,
  WL_AUDIO_CHANNEL_LEFT = 1,
  WL_AUDIO_CHANNEL_LEFT_CENTER = 2,
  WL_AUDIO_CHANNEL_RIGHT = 3,
  WL_AUDIO_CHANNEL_RIGHT_CENTER = 4
}
```
- WL_AUDIO_CHANNEL_CENTER：默认双声道立体声。
- WL_AUDIO_CHANNEL_LEFT：播放左声道音频，并且只有左声道有声音。
- WL_AUDIO_CHANNEL_LEFT_CENTER：播放左声道音频，并且左右声道声音都有声音。
- WL_AUDIO_CHANNEL_RIGHT：播放左声道音频，并且只有左声道有声音。
- WL_AUDIO_CHANNEL_RIGHT_CENTER：播放右声道音频，并且左右声道声音都有声音。

##### 4.40.1 设置播放声道类型
```typescript
public setAudioChannelType(audioChannelType: WlAudioChannelType)
```
##### 4.40.2 获取声道类型
```typescript
public getAudioChannelType(): WlAudioChannelType
```
#### 4.41 设置音视频同步偏移
```typescript
/**
 * 设置 音视频同步 偏移
 *
 * @param syncOffset > 0, 视频快于音频；< 0, 视频慢于音频 [-1, 1]
 */
public setSyncOffset(syncOffset: number)
```

#### 4.42 设置帧解码模式
```typescript
/**
 * 设置是否开启buffer帧解码模式
 *
 * @param enable
 */
public setBufferDeEncrypt(enable: boolean)
```

#### 4.43 获取 audioSessionId(未实现)
```typescript
/**
 * 获取 audioSessionId
 *
 * @return
 */
//public int getAudioSessionId()
```

#### 4.44 连续丢帧帧数设置
```typescript
/**
 * 如果触发丢帧，表示连续丢帧次数
 *
 * @param count 0：不丢帧
 */
public setDropFrameCount(count: number)
```
#### 4.45 设置实时回调pcm数据
```typescript
/**
 * 设置实时回调pcm数据
 * 注：数据返回是在子线程
 *
 * @param enable true: 开启 false: 关闭
 */
public setPcmCallbackEnable(enable: boolean)
```
#### 4.46 设置解码时间基准
```typescript
/**
 * 设置解码 timeBase
 *
 * @param timeBase [250000 ~ 1000000]
 */
public setCodecTimeBase(timeBase: number)
```