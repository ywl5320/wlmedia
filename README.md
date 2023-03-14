# wlmedia
#### Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 功能丰富，支持手机、电视盒子、手表等智能设备。<br/>
###  **有意购买源码请联系：ywl5320@163.com**


## 一、功能特点

- [x] 兼容androidx和support库
- [x] 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- [x] 支持file、http、https、rtmp、rtp、rtsp、byte[]等
- [x] 可选音频、视频、音视频播放模式
- [x] 软解硬解设置
- [x] 无缝切换surface（也可自定义surfaceview、textureview）
- [x] 支持多实例播放
- [x] 支持媒体自由切换
- [x] 支持FFmpeg参数设置
- [x] 支持byte[]数据解码
- [x] 支撑音视频变速变调
- [x] 支撑透明视频播放（可实现不错的视觉效果）
- [x] 字幕选择
- [x] 内置循环播放
- [x] 链接超时设置
- [x] 缓存大小设置（按时间、内存和队列设置）
- [x] 音视频加密播放
- [x] 音轨选择
- [x] 音频声道选择
- [x] 音频PCM数据和实时分贝获取
- [x] 音频指定采样率设置
- [x] 视频截屏
- [x] 视频首帧图片或指定时间图片获取
- [x] 视频任意比例设置
- [x] 视频旋转角度设置（0,90,180,270）
- [x] 视频shader自定义视频滤镜（动态设置）
- [x] 视频背景颜色设置（默认黑色）



## 二、实例展示
<img width="300" height="560" src="https://img-blog.csdnimg.cn/20210516155358862.jpg"/>

## 三、集成使用
### 3.1 [![](https://jitpack.io/v/wanliyang1990/wlmedia.svg)](https://jitpack.io/#wanliyang1990/wlmedia)

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.wanliyang1990:wlmedia:2.0.0'
	}
	
	
### 3.2 常用权限
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>

### 3.2 配置NDK编译平台

    defaultConfig {
        ...
        ndk {
            abiFilter("arm64-v8a")
            abiFilter("armeabi-v7a")
            abiFilter("x86")
            abiFilter("x86_64")
            }
        ...
        }

### 3.3 API

#### 3.3.1 视频Surface

```java
    // WlSurfaceView 一般播放使用
    <com.ywl5320.wlmedia.surface.WlSurfaceView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    
    // WlTextureView 需要做透明、移动、旋转等使用
    <com.ywl5320.wlmedia.surface.WlTextureView
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

#### 3.3.2 基础调用代码（更多功能见Demo）

```java

    WlSurfaceView wlSurfaceView = findViewById(R.id.wlsurfaceview);
	WlMedia wlMedia = new WlMedia();
	wlMedia.setSource(url);
	wlSurfaceView.setWlMedia(wlMedia);

	wlMedia.setOnMediaInfoListener(new WlOnMediaInfoListener() {
		@Override
		public void onPrepared() {
			//异步准备好后开始播放
			wlMedia.start();
		}

		@Override
		public void onError(int code, String msg) {
			//错误回调，主要用于查看错误信息

		}

		@Override
		public void onComplete(WlComplete type, String msg) {
			//播放完成（包含：正常播放完成、超时播放完成、手动触发播放完成等）

		}

		@Override
		public void onTimeInfo(double currentTime, double bufferTime) {
			//时间回调，当前时间和缓冲时间

		}

		@Override
		public void onSeekFinish() {
			//seek完成后回调，可用于类似iptv这种快进快退
		}

		@Override
		public void onLoopPlay(int loopCount) {
			//循环播放此时回调
		}

		@Override
		public void onLoad(boolean load) {
			//加载状态回调
		}

		@Override
		public byte[] decryptBuffer(byte[] encryptBuffer) {
			return new byte[0];
		}

		@Override
		public byte[] readBuffer(int read_size) {
			return new byte[0];
		}

		@Override
		public void onPause(boolean pause) {
			//暂停回调
		}
	});

	wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
		@Override
		public void initSuccess() {
			//surfaceview初始化完成
			wlMedia.prepared();
		}

		@Override
		public void onSurfaceChange(int width, int height) {
			//surfaceview大小改变
		}

		@Override
		public void moveX(double value, int move_type) {
			//surfaceview横向左右滑动
		}

		@Override
		public void onSingleClick() {
			//surfaceview单击事件
		}

		@Override
		public void onDoubleClick() {
			//surfaceview双击事件
		}

		@Override
		public void moveLeft(double value, int move_type) {
			//surfaceview左侧上下滑动事件
		}

		@Override
		public void moveRight(double value, int move_type) {
			//surfaceview右侧上下滑动事件
		}
	});

```
## 四、博客详解
#### [wlmedia播放器集成（1）— 播放器集成](https://blog.csdn.net/ywl5320/article/details/116899303)
#### [wlmedia播放器集成（2）— 常用自定义view](https://blog.csdn.net/ywl5320/article/details/116901140)
#### [wlmedia播放器集成（3）— 常用api](https://blog.csdn.net/ywl5320/article/details/116945049)
#### [wlmedia播放器集成（4）— 实现视频播放](https://blog.csdn.net/ywl5320/article/details/117000589)

## 五、讨论群（1085618246）
<img width="238" height="250" src="https://img-blog.csdnimg.cn/20210516155929425.png"/><br/>

## 六、混淆
    -keep class com.ywl5320.wlmedia.* {*;} 


## 七、参考资料
#### [我的视频课程（基础）：《（NDK）FFmpeg打造Android万能音频播放器》](https://edu.csdn.net/course/detail/6842)
#### [我的视频课程（进阶）：《（NDK）FFmpeg打造Android视频播放器》](https://edu.csdn.net/course/detail/8036)
#### [我的视频课程（编码直播推流）：《Android视频编码和直播推流》](https://edu.csdn.net/course/detail/8942)
#### [我的视频课程（C++ OpenGL）：《Android C++ OpenGL》](https://edu.csdn.net/course/detail/19367)
#### [测试音视频文件地址（提取码：ivbh）](https://pan.baidu.com/s/1Gkm9cgmsvk4dXGPZVyHgZw)

## 八、核心三方库
#### [FFmpeg](http://ffmpeg.org/)
#### [OpenSSL](https://github.com/openssl/openssl)
#### [SoundTouch](http://www.surina.net/soundtouch/)


### Create By：ywl5320 2019-12-16





