## WlPlayer API
WlPlayer 是播放器SDK，用于音视频播放，其用法如下：
### 一 构造函数
`WlPlayer` 构造函数不依赖上下文，很方便创建使用，当创建了 `WlPlayer` 实例后，都需要调用 `release` 释放资源。
#### 1.1 无参构造函数
```java
public WlPlayer()
```
#### 1.2 带参构造函数
带参构造函数用于动态加载动态库的场景，可在 `WlOnLoadLibraryListener.onLoadedLibrary()` 方法中加载动态库，返回 `true` 后，播放器将不再走自身加载动态库逻辑。 
```java
public WlPlayer(WlOnLoadLibraryListener onLoadLibraryListener)
```

### 二 设置Surface
播放器自带 `WlSurfaceView` 和 `WlTextureView` 用于视频渲染，包含最基本的滑动操作。
#### 2.1 使用播放器Surface
##### 2.1.1 布局
```xml
<com.ywl5320.wlmedia.widget.WlSurfaceView
    android:id="@+id/wlsurfaceview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>

<com.ywl5320.wlmedia.widget.WlTextureView
    android:id="@+id/wltextureview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
##### 2.1.2 关联播放器
```java
WlSurfaceView wlSurfaceView = findViewById(R.id.wlsurfaceview);
WlPlayer wlPlayer = new WlPlayer();
// 把wlplayer设置给Surface
wlSurfaceView.setWlPlayer(wlPlayer);
```
##### 2.1.3 设置回调
```java
wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {

    @Override
    public void initSuccess() {
        // surface 初始化完成
    }

    @Override
    public void onSurfaceChange(int width, int height) {
        // surface 大小改变
    }

    @Override
    public void moveX(double value, int move_type) {
        // 横向左右滑动 value 为滑动距离
        if (move_type == MOVE_START) {
            // 滑动开始
        } else if (move_type == MOVE_ING) {
            // 滑动中
        } else if (move_type == MOVE_STOP) {
            // 滑动结束
        }
    }

    @Override
    public void onSingleClick() {
        // 单击surface
    }

    @Override
    public void onDoubleClick() {
        // 双击surface
    }

    @Override
    public void moveLeft(double value, int move_type) {
        // 左侧上下滑动
    }

    @Override
    public void moveRight(double value, int move_type) {
        // 右侧上下滑动
    }
});
```

#### 2.2 自定义Surface
开发者可以选择自定义`Surface`，在自定义`Surface`中需要调用 `WlPlayer.setSurface(Surface surface, String rgba, int serialNumber)` 方法，向播放器传入`Surface`。
参数解释：
- surface：自定义Surface中的Surface，用于视频显示。
- rgba：设置清屏颜色，16进制，如：#000000 黑色。
- serialNumber：唯一区分Surface的序列号，推荐使用hashCode。
```java
@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    if (wlPlayer != null) {
        wlPlayer.setSurface(holder.getSurface(), "#000000", hashCode());
    }
}    
```

### 三 设置回调
#### 3.1 播放回调
```java
public void setOnMediaInfoListener(WlOnMediaInfoListener onMediaInfoListener)
```
#### 3.2 回调函数方法解释
```java
public interface WlOnMediaInfoListener {

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
     * 播放byte[]类型数据入口 （子线程）
     *
     * @param read_size 播放器需要buffer大小单位byte
     * @return 返回待播放数据
     */
    default byte[] readBuffer(int read_size) {
        return null;
    }

    /**
     * 截图回调
     *
     * @param bitmap
     */
    default void onTakePicture(Bitmap bitmap) {
    }

    /**
     * 外部渲染信息回调
     * 
     * @param outRenderBean 外部渲染初始化信息，如unity中渲染。
     */
    default void onOutRenderInfo(WlOutRenderBean outRenderBean) {
    }
}
```

### 四 常用方法
#### 4.1 数据源
数据源支持`本地文件`、`网络`、`buffer`等。

##### 4.1.1 设置数据源
```java
public void setSource(String source)
```
##### 4.1.2 获取数据源
```java
public String getSource()
```

#### 4.2 数据源类型
数据源类型，表示当前播放器采用的数据源模式，枚举值如下：
```java
public enum WlSourceType {
    WL_SOURCE_NORMAL("WL_SOURCE_NORMAL", 0, "normal source type eg: file or net address"),//常规播放
    WL_SOURCE_BUFFER("WL_SOURCE_BUFFER", 1, "play buffer with byte[]"),//播放byte[]
    WL_SOURCE_ENCRYPT_FILE("WL_SOURCE_ENCRYPT_FILE", 2, "play encrypted files with byte[]");//播放加密文件
}
```
- WL_SOURCE_NORMAL：常规播放，根据传入的 source 进行自动识别播放。
- WL_SOURCE_BUFFER：播放 buffer 类型的数据，数据通过 readBuffer 方法返回。
- WL_SOURCE_ENCRYPT_FILE：播放加密视频，数据通过 decryptBuffer 返回业务解密后再返回给播放器播放。
##### 4.2.1 设置数据源类型
```java
public void setSourceType(WlSourceType sourceType)
```
##### 4.2.2 获取数据源类型
```java
public WlSourceType getSourceType()
```
#### 4.3 异步准备
当设置了数据源后，就可以调用异步准备开始播放音视频了。
```java
public void prepare()
```
#### 4.4 开始播放
当异步准备成功后，在不设置自动播放的情况下，会回调 `onPrepared()` 方法，在 `onPrepared()` 方法中可以调用 `start()` 启动播放。只有异步准备好后调用一次 `start()` 即可，后续暂停后，调用 `resume()` 恢复播放。
```java
public void start()
```

#### 4.5 暂停播放
##### 4.5.1 暂停
```java
public void pause()
```
##### 4.5.2 是否暂停中
```java
public boolean isPause()
```

#### 4.6 暂停后恢复播放
当播放器 `pause()` 后，要恢复播放，调用 `resume()`，不要调用 `start()` 。
```java
public void resume()
```

#### 4.7 停止播放
主动调用 `stop()` 停止播放，会触发 `onComplete` 回调，回调类型为：`WlCompleteType.WL_COMPLETE_HANDLE` 表示主动停止。
```java
public void stop()
```

#### 4.8 销毁资源
只要创建了实例，就要调用release销毁:
- 在播放中，调用 `release` 销毁资源，会触发 `onComplete` 回调，回调类型为：`WlCompleteType.WL_COMPLETE_RELEASE` 表示释放资源停止。
- 当停止后，调用 `release` 销毁资源，不会触发任何回调。
```java
public void release()
```

#### 4.9 播放时间回调间隔
默认播放时间回调间隔为 1s , 当需要更精确的时间回调来处理业务时，可以通过此函数设置新的回调间隔，设置范围为：【0.01,5.0】秒。
##### 4.9.1 设置播放时间回调间隔
```java
public void setTimeInfoInterval(double seconds)
```
##### 4.9.2 获取当前播放时间回调间隔
```java
public double getTimeInfoInterval()
```

#### 4.10 获取当前播放时间
单位： 秒。
```java
public double getCurrentTime()
```

#### 4.11 获取当前缓冲时间
单位：秒。
```java
public double getBufferTime()
```

#### 4.12 获取当前媒体总时长
单位：秒。
```java
public double getDuration()
```

#### 4.13 seek 跳转
`seek` 指定跳转到相应时间（单位：秒），当 `seek` 完成后，会回调 `onSeekFinish` 方法，跳转支持三种模式, 枚举值如下：
```java
public enum WlSeekType {
    WL_SEEK_FAST("WL_SEEK_FAST", 0, "not sync audio and video pts, use default"), // 快速模式
    WL_SEEK_NORMAL("WL_SEEK_NORMAL", 1, "sync audio and video pts, base on max pts"), // 常规模式
    WL_SEEK_ACCURATE("WL_SEEK_ACCURATE", 2, "sync audio and video pts, base on seek pts or max pts"); // 精确模式
}
```
- WL_SEEK_FAST：快速模式，seek 完成后马上开始播放，不用等待音视频时间戳对其。 -- 速度快
- WL_SEEK_NORMAL：常规模式，seek 完成后，会等待音频视频的时间同步后，才开始播。 -- 速度中等
- WL_SEEK_ACCURATE：精确模式，seek 完成后，会等待音频视频时间同步并且大于等于 seek 时间后，才开始播放。 -- 速度慢
```java
public void seek(double seekTime)
public void seek(double seekTime, WlSeekType seekType)
```

#### 4.14 设置 seekStart
当 `seek` 时，时间回调同步在进行，此时可能会出现 `seek bar` 进度异常跳转，当 `seek` 时，先设置 `seekStart` 后，会暂时禁用时间回调，此时等待 `seek `完成后才会重新回调时间：
```java
public void seekStart()
```

#### 4.15 指定播放音频或视频
指定需要播放媒体资源播放模式，支持三种播放模式，枚举值如下：
```java
public enum WlPlayModel {
    WL_PLAY_MODEL_AUDIO_VIDEO("WL_PLAY_MODEL_AUDIO_VIDEO", 0, "play audio and video, if the media have audio and video, play both, if only have audio, play audio, if only have video, play video"),
    WL_PLAY_MODEL_ONLY_AUDIO("WL_PLAY_MODEL_ONLY_AUDIO", 1, "only play audio, if the media not have audio, then play nothing"),
    WL_PLAY_MODEL_ONLY_VIDEO("WL_PLAY_MODEL_ONLY_VIDEO", 2, "only play video, if the media not have video, then play nothing");
}
```
- WL_PLAY_MODEL_AUDIO_VIDEO：同时播放音频或视频，如果媒体资源只有音频或视频，也只会播放音频或视频。
- WL_PLAY_MODEL_ONLY_AUDIO：只播放音频流。
- WL_PLAY_MODEL_ONLY_VIDEO：只播放视频流。
##### 4.15.1 设置播放模式
```java
public void setPlayModel(WlPlayModel playModel)
```
##### 4.15.2 获取播放模式
```java
public WlPlayModel getPlayModel()
```

#### 4.16 指定视频解码模式
解码模式支持2中，硬解码优先和使用软解码，只针对视频解码有效，枚举值如下：
```java
public enum WlCodecType {
    WL_CODEC_HARD_FIRST("WL_CODEC_HARD_FIRST", 0, "try use hard codec first, if not work, then use soft codec"),
    WL_CODEC_SOFT("WL_CODEC_SOFT", 1, "only use soft codec");
}
```
- WL_CODEC_HARD_FIRST：硬解码优先，会自动检测是否支持硬解码，当不支持硬解码时，会使用软解码。
- WL_CODEC_SOFT：只使用软解码。
##### 4.16.1 设置视频解码模式
```java
public void setCodecType(WlCodecType codecType)
```
##### 4.16.2 获取视频解码模式
```java
public WlCodecType getCodecType()
```

#### 4.17 指定音频采样率
硬件播放音频数据，其采样率是有一定的范围的，而音频文件的采样率是多种多样的，如果不重采样，可能会出现播放异常等情况，可以对播放音频指定采样率，提高应用兼容性，指定采样率枚举如下：
```java
public enum WlSampleRate {
    WL_SAMPLE_RATE_NONE("WL_SAMPLE_RATE_NONE", 0, "use default audio sample rate"),
    WL_SAMPLE_RATE_8000("WL_SAMPLE_RATE_8000", 8000, "convert audio sample rate to 8000HZ"),
    WL_SAMPLE_RATE_11025("WL_SAMPLE_RATE_11025", 11025, "convert audio sample rate to 11025HZ"),
    WL_SAMPLE_RATE_12000("WL_SAMPLE_RATE_12000", 12000, "convert audio sample rate to 12000HZ"),
    WL_SAMPLE_RATE_16000("WL_SAMPLE_RATE_16000", 16000, "convert audio sample rate to 16000HZ"),
    WL_SAMPLE_RATE_22050("WL_SAMPLE_RATE_22050", 22050, "convert audio sample rate to 22050HZ"),
    WL_SAMPLE_RATE_24000("WL_SAMPLE_RATE_24000", 24000, "convert audio sample rate to 24000HZ"),
    WL_SAMPLE_RATE_32000("WL_SAMPLE_RATE_32000", 32000, "convert audio sample rate to 32000HZ"),
    WL_SAMPLE_RATE_44100("WL_SAMPLE_RATE_44100", 44100, "convert audio sample rate to 44100HZ"),
    WL_SAMPLE_RATE_48000("WL_SAMPLE_RATE_48000", 48000, "convert audio sample rate to 48000HZ");
}
```
常用采样率为 `WL_SAMPLE_RATE_44100`、`WL_SAMPLE_RATE_48000`，几乎所有的设备都支持。
##### 4.17.1 设置音频采样率
```java
public void setSampleRate(WlSampleRate sampleRate)
```
##### 4.17.2 获取当前音频采样率
```java
public WlSampleRate getSampleRate()
```

#### 4.18 指定音频播放速度
设置音频播放速度，速度范围为 [0.25,4.0]
##### 4.18.1 设置音频播放速度
```java
public void setSpeed(double speed)
```
##### 4.18.2 获取音频播放速度
```java
public double getSpeed()
```

#### 4.19 指定音频播放音调
音频播放音调有三种模式，枚举值为：
```java
public enum WlPitchType {
    WL_PITCH_NORMAL("WL_PITCH_NORMAL", 0, "normal pitch range[0.25,4.0]"),
    WL_PITCH_SEMITONES("WL_PITCH_SEMITONES", 1, "semitones pitch range[-12,12]"),
    WL_PITCH_OCTAVES("WL_PITCH_OCTAVES", 2, "octaves pitch range[-1,1]");//(-1 ~ 1)
}
```
- WL_PITCH_NORMAL：默认音调模式，范围 [0.25,4.0]。
- WL_PITCH_SEMITONES: SEMITONES模式，范围 [-12,12]。
- WL_PITCH_OCTAVES：OCTAVES模式，范围 [-1,1]。
##### 4.19.1 设置音频音调
```java
public void setPitch(double pitch)
public void setPitch(WlPitchType pitchType, double pitch)
```
##### 4.19.2 获取音频音调类型
```java
public WlPitchType getPitchType()
```
##### 4.19.2 获取音频音调值
```java
public double getPitch()
```

#### 4.20 指定视频缩放比例
视频缩放比例默认有4中模式，枚举值为：
```java
public enum WlScaleType {
    WL_SCALE_16_9("WL_SCALE_16_9", 16, 9, "scale video width and height use 16:9"),
    WL_SCALE_4_3("WL_SCALE_4_3", 4, 3, "scale video width and height use 4:3"),
    WL_SCALE_FIT("WL_SCALE_FIT", 0, 0, "scale video width and height fit"),
    WL_SCALE_MATCH("WL_SCALE_MATCH", -1, -1, "scale video width and height match");
}
```
- WL_SCALE_16_9：宽高比为 16:9。
- WL_SCALE_4_3：宽高比为 4:3。
- WL_SCALE_FIT：宽高比为默认值。
- WL_SCALE_MATCH：宽高比为拉伸填充整个Surface。
##### 4.20.1 设置视频缩放模式
```java
public void scaleVideo(WlScaleType scaleType)
```
##### 4.20.2 自定义视频缩放比例
```java
public void scaleVideo(int scaleWidth, int scaleHeight)
```
##### 4.20.3 获取缩放比例宽和高
```java
public int getScaleWidth()
public int getScaleHeight()
```

#### 4.21 指定视频旋转角度
视频旋转角度有4个角度模式，枚举值为：
```java
public enum WlRotateType {
    WL_ROTATE_DEFAULT("WL_ROTATE_DEFAULT", -1, "the video not rotate any degree"),
    WL_ROTATE_0("WL_ROTATE_0", 0, "the video rotate 0 degree"),
    WL_ROTATE_90("WL_ROTATE_90", 90, "the video rotate 90 degree"),
    WL_ROTATE_180("WL_ROTATE_180", 180, "the video rotate 180 degree"),
    WL_ROTATE_270("WL_ROTATE_270", 270, "the video rotate 270 degree");
}
```
- WL_ROTATE_DEFAULT：视频默认角度，不做任何旋转。
- WL_ROTATE_0：顺时针旋转 0 度。
- WL_ROTATE_90：顺时针旋转 90 度。
- WL_ROTATE_180：顺时针旋转 180 度。
- WL_ROTATE_270：顺时针旋转 270 度。
##### 4.21.1 设置视频旋转角度
```java
public void rotateVideo(WlRotateType rotateType)
```
##### 4.21.2 获取视频旋转角度
```java
public WlRotateType getVideoRotate()
```

#### 4.22 指定视频镜像模式
视频镜像分为4中类型，枚举值为：
```java
public enum WlMirrorType {
    WL_MIRROR_NONE("WL_MIRROR_NONE", 0, "not mirror video"),
    WL_MIRROR_TOP_BOTTOM("WL_MIRROR_TOP_BOTTOM", 1, "mirror video top and bottom"),
    WL_MIRROR_LEFT_RIGHT("WL_MIRROR_LEFT_RIGHT", 2, "mirror video left and right"),
    WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT("WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT", 3, "mirror video top and bottom, left and right");
}
```
- WL_MIRROR_NONE：不设置镜像。
- WL_MIRROR_TOP_BOTTOM：上下镜像。
- WL_MIRROR_LEFT_RIGHT：左右镜像。
- WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT：上下左右同时镜像。
##### 4.22.1 设置镜像模式
```java
public void mirrorVideo(WlMirrorType wlMirrorType)
```
##### 4.22.2 获取镜像模式
```java
public WlMirrorType getVideoMirror()
```

#### 4.23 设置播放完是否清屏
当设置清屏后，视频不会停留在最后一帧，可在视频还没有播放完成时调用。
##### 4.23.1 设置清屏
```java
public void setClearLastVideoFrame(boolean clear)
```
##### 4.23.2 获取是否清屏
```java
public boolean isClearLastVideoFrame()
```

#### 4.24 设置网络资源访问超时时长
设置访问网络资源超时时长，默认 15s，当超过时间，还没有读取到媒体资源数据，将会回调 `onComplete` 方法，类型为：`WL_COMPLETE_TIMEOUT`。
##### 4.24.1 设置超时时间
```java
public void setTimeOut(double timeOut)
```
##### 4.24.2 获取超时时间
```java
public double getTimeOut()
```

#### 4.25 循环播放
当设置循环播放时，正常播放完成（EOF）后会自动重新播放。
##### 4.25.1 设置循环播放
```java
public void setLoopPlay(boolean loopPlay)
```
##### 4.25.2 是否循环播放
```java
public boolean isLoopPlay()
```

#### 4.26 自动播放
当设置自动播放后，异步 `prepare` 准备好后会自动开始播放，并且会回调 `onAutoPlay` 方法。
##### 4.26.1 设置循环播放
```java
public void setAutoPlay(boolean autoPlay)
```
##### 4.26.2 是否循环播放
```java
public boolean isAutoPlay()
```

#### 4.27 设置音量
设置音量，可以选择是否更改音频数据本身音量值来改变音量。
##### 4.27.1 设置音量
```java
/**
 * 设置音量
 *
 * @param percent   百分比（0 ~ 200）
 * @param changePcm 是否改变pcm数据
 */
public void setVolume(int percent, boolean changePcm) {
    n_wlPlayer_setVolume(percent, changePcm);
}
```
##### 4.27.2 获取当前音量
```java
public int getVolume()
```

#### 4.28 获取当前是否播放中
```java
public boolean isPlaying()
```
当 `isPlaying() == true && isPause() == false` 表示真正播放中。

#### 4.29 设置缓冲大小
缓冲大小设置有2个阈值，单位都是 秒：
- minBufferToPlay：表示最小缓存多少时，才开始播放。
- maxBufferWaitToPlay：表示最大缓冲多大，在当前 播放时间上面+maxBufferWaitToPlay 表示实际缓冲时间。
```java
public void setBufferSize(double minBufferToPlay, double maxBufferWaitToPlay)
```
#### 4.30 设置FF参数
有些网络流，如 `rtsp` 流需要设置使用 `tcp` 或 `udp` 网络模式才能播放，设置方式如下：
##### 4.30.1 设置参数
```java
public void setOptions(String key, String value)
```
##### 4.30.2 清除所有参数
```java
public void clearOptions()
```

#### 4.31 视频截图
调用视频截图功能后，视频快照会回调 `onTakePicture` 方法。
```java
public void takePicture()
```

#### 4.32 透明视频播放
目前支持透明视频为：视频中用灰度值表示透明度的视频，一般是灰度值和原视频比例是一比一。透明视频枚举值为：
```java
public enum WlAlphaVideoType {
    WL_ALPHA_VIDEO_DEFAULT("WL_ALPHA_VIDEO_DEFAULT", 0, "the video have no alpha channel"),
    WL_ALPHA_VIDEO_CUSTOM("WL_ALPHA_VIDEO_CUSTOM", 1, "custom"),
    WL_ALPHA_VIDEO_LEFT_ALPHA("WL_ALPHA_VIDEO_LEFT_ALPHA", 2, "the video have left alpha channel(50%)"),
    WL_ALPHA_VIDEO_RIGHT_ALPHA("WL_ALPHA_VIDEO_RIGHT_ALPHA", 3, "the video have right alpha channel(50%)"),
    WL_ALPHA_VIDEO_TOP_ALPHA("WL_ALPHA_VIDEO_TOP_ALPHA", 4, "the video have left top channel(50%)"),
    WL_ALPHA_VIDEO_BOTTOM_ALPHA("WL_ALPHA_VIDEO_BOTTOM_ALPHA", 5, "the video have bottom alpha channel(50%)");
}
```
- WL_ALPHA_VIDEO_DEFAULT：默认值，视频不带透明度。
- WL_ALPHA_VIDEO_LEFT_ALPHA：透明度视频为左边百分之50%。
- WL_ALPHA_VIDEO_RIGHT_ALPHA：透明度视频为右边百分之50%。
- WL_ALPHA_VIDEO_TOP_ALPHA：透明度视频为上边百分之50%。
- WL_ALPHA_VIDEO_BOTTOM_ALPHA透明度视频为下边百分之50%。
##### 4.32.1 设置透明视频类型
```java
public void setAlphaVideoType(WlAlphaVideoType alphaVideoType)
```

#### 4.33 获取媒体文件中所有流信息
可以单独获取所有音频信息、视频信息和字幕信息，信息包括：音频采样率，音频码率，视频宽高，视频码率，视频帧率等等。
##### 4.33.1 获取所有音频信息
```java
public WlTrackInfoBean[] getAudioTracks()
```
##### 4.33 2 获取所以视频信息
```java
public WlTrackInfoBean[] getVideoTracks()
```
##### 4.33.3 获取所有字幕信息
```java
public WlTrackInfoBean[] getSubtitleTracks()
```

#### 4.34 设置音频或字幕流
当播放含有多音轨的视频会多字幕的视频时，可以选择需要播放的音频或显示的字幕类型。
##### 4.34.1 设置播放音轨
```java
public void setAudioPlayTrack(int audioTrackIndex)
```
##### 4.34.2 设置显示字幕
```java
public void setSubtitlePlayTrack(int subtitleTrackIndex)
```

#### 4.35 获取当前播放的音频、视频和字幕流信息
获取当前播放流信息，就可以方便做一些业务操作。
##### 4.35.1 获取当前播放音频流信息
```java
public WlTrackInfoBean getCurrentAudioTrack()
```
##### 4.35.2 获取当前播放视频流信息
```java
public WlTrackInfoBean getCurrentVideoTrack()
```
##### 4.35.3 获取当前播放字幕流信息
```java
public WlTrackInfoBean getCurrentSubtitleTrack()
```

#### 4.36 设置最大渲染分辨率
表示硬解码时使用`OpenGL`渲染的最大分辨率，比如设置最大分辨率为：1920 * 1080，当播放超过这个分辨率的视频时，并且支持`硬解码`时将不会使用`OpenGL`进行渲染，从而提高解码性能。默认大小为：4K（3840 * 2160）。
```java
public void setRenderDefaultSize(int width, int height)
```

#### 4.37 设置外部渲染环境
目前仅支持使用`OpenGL`渲染的情况，如果是要用到`Unity`中，`Unity`请使用`OpenGL`渲染。
```java
public void initOutRenderEnv(String name, int type, int version)
```

#### 4.38 取消外部渲染设置
当不使用外部渲染时，需要销毁外部环境资源。
```java
public void unInitOutRenderEnv(String name)
```

#### 4.39 设置最大渲染帧率
当设置最大渲染帧率后，渲染帧率会控制在设置范围左右，来太高渲染性能，默认：60fps。
```java
public void setRenderFPS(int fps)
```

#### 4.40 设置声道
有些媒体文件比如部分MV视频，其原声和伴奏是分声道存储的，当要实现原声和伴奏切换时，就需要切换声道，播放器提供声道切换，枚举值为：
```java
public enum WlAudioChannelType {
    WL_AUDIO_CHANNEL_CENTER("WL_AUDIO_CHANNEL_CENTER", 0, "both left and right channel together play"), //立体声（左右声道）
    WL_AUDIO_CHANNEL_LEFT("WL_AUDIO_CHANNEL_LEFT", 1, "only left channel play"), //左声道 一个扬声器
    WL_AUDIO_CHANNEL_LEFT_CENTER("WL_AUDIO_CHANNEL_LEFT_CENTER", 2, "left channel play from left and right together channel"), //左声道 2个扬声器
    WL_AUDIO_CHANNEL_RIGHT("WL_AUDIO_CHANNEL_RIGHT", 3, "only right channel play"), //右声道 一个扬声器
    WL_AUDIO_CHANNEL_RIGHT_CENTER("WL_AUDIO_CHANNEL_RIGHT_CENTER", 4, "right channel play from left and right together channel"); //左声道 2个扬声器
}
```
- WL_AUDIO_CHANNEL_CENTER：默认双声道立体声。
- WL_AUDIO_CHANNEL_LEFT：播放左声道音频，并且只有左声道有声音。
- WL_AUDIO_CHANNEL_LEFT_CENTER：播放左声道音频，并且左右声道声音都有声音。
- WL_AUDIO_CHANNEL_RIGHT：播放左声道音频，并且只有左声道有声音。
- WL_AUDIO_CHANNEL_RIGHT_CENTER：播放右声道音频，并且左右声道声音都有声音。

##### 4.40.1 设置播放声道类型
```java
public void setAudioChannelType(WlAudioChannelType audioChannelType)
```
##### 4.40.2 获取声道类型
```java
public WlAudioChannelType getAudioChannelType()
```