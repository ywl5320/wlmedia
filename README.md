## &#x1F680;wlmedia&#x1F680;
#### Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 使用简单，功能丰富，支持手机、车机系统、电视盒子、手表等智能设备
#### 全新架构，增加稳定性
#### HarmonyOS Coming Soon
- 若有`HarmonyOS NEXT`开发资质的伙伴，想合作的可以联系：[ywl5320@163.com](mailto:ywl5320@163.com)
#### iOS Coming Soon
## 一 功能&特点
- [x] 支持系统：Android 4.4+（api 19+）
- [x] 支持架构：armeabi-v7a、arm64-v8a、x86、x86_64
- [x] 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- [x] 支持file、http、https、rtmp、rtp、rtsp、byte[]等
- [x] 支持AV1解码
- [x] 可选音频、视频、音视频播放模式
- [x] 软解硬解设置
- [x] 无缝切换surface（也可自定义surfaceview、textureview）
- [x] 支持多实例播放
- [x] 支持播放完成（EOF）后，再次seek又继续播放
- [x] 支持媒体自由切换
- [x] 支持FFmpeg参数设置
- [x] 支持byte[]数据解码
- [x] 支撑音视频变速变调
- [x] 支撑透明视频播放（可实现不错的视觉效果）
- [x] 字幕选择
- [x] 内置循环播放
- [x] 链接超时设置
- [x] 缓存大小设置（时间维度）
- [x] 音视频加密播放
- [x] 音轨选择
- [x] 音频指定采样率设置
- [x] 音频指定声道播放
- [x] 视频截屏
- [x] 视频首帧图片或指定时间图片获取
- [x] 视频任意比例设置
- [x] 视频旋转角度设置（0,90,180,270）
- [x] 视频镜像模式设置
- [x] 视频背景颜色设置（默认黑色）
- [x] 视频支持同时多个surface渲染（如：KTV大小屏幕）
- [x] 支持Unity播放（需定制对接）
## 二 集成使用
### 2.1 gradle [![](https://jitpack.io/v/wanliyang1990/wlmedia.svg)](https://jitpack.io/#wanliyang1990/wlmedia)
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
	
dependencies {
        implementation 'com.github.wanliyang1990:wlmedia:3.0.1'
}
 ```
 ### 2.2 常用权限
 ```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.INTERNET"/>
 ```

 ### 2.3 配置NDK编译平台
 ```gradle
 defaultConfig {
    ...
    ndk {
        abiFilter("armeabi-v7a")
        abiFilter("arm64-v8a")
        abiFilter("x86")
        abiFilter("x86_64")
        }
...
}
 ```
 ### 2.4 设置Surface
 ```xml
 <-- WlSurfaceView 一般播放使用 -->
 <com.ywl5320.wlmedia.widget.WlSurfaceView
     android:id="@+id/wlsurfaceview"
     android:layout_width="match_parent"
     android:layout_height="match_parent" />

 <-- WlTextureView 需要做透明、移动、旋转等使用 -->
 <com.ywl5320.wlmedia.widget.WlTextureView
     android:id="@+id/wltextureview"
     android:layout_width="match_parent"
     android:layout_height="match_parent" />
 ```
 ### 2.5 基础调用代码（更多功能见Demo）
 ```java
 // 1.创建播放器
 WlPlayer wlPlayer = new WlPlayer();
 wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
    @Override
    public void onPrepared() {
        // 异步准备好后回调，这里调用 wlplayer.start() 开始播放
        wlPlayer.start();
    }

    @Override
    public void onTimeInfo(double currentTime, double bufferTime) {
        // 时间进度回调 
    }

    @Override
    public void onComplete(WlCompleteType wlCompleteType, String msg) {
        // 播放完成回调，根据 WlCompleteType 区分对应类型
        if (wlCompleteType == WlCompleteType.WL_COMPLETE_EOF) {
            // 正常播放完成
        } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_ERROR) {
            // 播放出错，原因为：msg 字段
        } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_HANDLE) {
            // 主动调用 wlPlayer.stop() 会回调此类型
        } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_NEXT) {
            // 正在播放中，切换了新的数据源，会回调此类型
        } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_TIMEOUT) {
            // 播放超时，会回调此接口
        } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_RELEASE) {
            // 播放中调用 wlPlayer.release() 会回调此接口
        }
    }

    @Override
    public void onLoad(WlLoadStatus wlLoadStatus, int progress, long speed) {
        // 加载状态回调
        if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_START) {
            // 开始加载
        } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_PROGRESS) {
            // 加载进度
        } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_FINISH) {
            // 加载完成
        }
    }

    @Override
    public void onTakePicture(Bitmap bitmap) {
        // 截图回调
    }

    @Override
    public void onAutoPlay() {
        // 如果设置了 wlplayer.setAutoPlay(true) 异步准备好后，将会回调此接口
    }

    @Override
    public void onFirstFrameRendered() {
        // 首帧渲染回调
    }

    @Override
    public void onSeekFinish() {
        // seek 完回调
    }

    @Override
    public byte[] decryptBuffer(byte[] encryptBuffer, long position) {
        // （子线程）加密视频数据会通过此接口返回，经过解密后再返回给播放器
        return encryptBuffer;
    }

    @Override
    public byte[] readBuffer(int read_size) {
        // （子线程）byte[] 类型的buffer，通过此接口给播放器提供数据
        return null;
    }

    @Override
    public void onOutRenderInfo(WlOutRenderBean outRenderBean) {
        // 供外部渲染获取初始化信息，如 unity播放。
    }
});

 // 2.获取 WlSurfaceView 并绑定播放器
 WlSurfaceView wlSurfaceView = findViewById(R.id.wlsurfaceview);
 wlSurfaceView.setWlPlayer(wlPlayer);

 // 3.设置数据源异步准备
 wlPlayer.setSource(url);
 wlPlayer.prepare();
 ```

## 三 详细 API
- [1. WlPlayer（音视频播放SDK）](doc/wlplayer_api.md)
- [2. WlMediaUtil（音视频工具类SDK）](doc/wlmediautil_api.md)
- 3.HarmonyOS（coming soon）
- 4.iOS (coming soon)
## 四 效果展示

|  常规播放  |  透明视频  |  多Surface渲染  | 多实例播放  |
| :----: | :----: | :----: | ------ |
| <img src="doc/imgs/normal.gif" alt="描述" width="240" height="520"> | <img src="doc/imgs/alphavideo.gif" alt="描述" width="240" height="520"> | <img src="doc/imgs/multisurface.gif" alt="描述" width="240" height="520"> | <img src="doc/imgs/multiplayer.gif" alt="描述" width="240" height="520"> |


## 五 混淆
```pro
-keep class com.ywl5320.wlmedia.* {*;} 
```

## 六 免费和增值服务
`WlMedia` 是按应用根据 `包名` 定制的，分免费版和付费定制版
### 免费版
- 免费版需要根据 `包名` 定制打包，功能上包含正常的播放功能。
- 如需咨询或定制，联系方式：
  - 邮箱：[ywl5320@163.com](mailto:ywl5320@163.com)
### 增值版
- demo 默认包名为: `com.ywl5320.wlmedia.example`，要测试功能，把包名改为 `com.ywl5320.wlmedia.example` 即可，无功能限制。
- 应用包名首次定制打包，`￥29.90` 可试用三个月，每个应用包名仅限一次。
- 如需咨询或定制，联系方式：
  - 邮箱：[ywl5320@163.com](mailto:ywl5320@163.com)

## 七 讨论群（1085618246）
![QQ](doc/imgs/qq_ercode.png)

## 八 核心三方库
- [ffmpeg](http://ffmpeg.org/)
- [openssL](https://github.com/openssl/openssl)
- [soundtouch](http://www.surina.net/soundtouch/)
- [dav1d](https://code.videolan.org/videolan/dav1d)

#### Update By：ywl5320 2024-05-20
#### Create By：ywl5320 2019-12-16
