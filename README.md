# wlmedia
#### Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 功能丰富，支持手机、电视盒子、手表等智能设备。<br/>
#### 如有疑问请联系：ywl5320@163.com

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



## 二、实例展示 ([测试APK《V视-1.0.7》下载](https://outexp-beta.cdn.qq.com/outbeta/2020/10/04/comvvideoplayer_1.0.7_79abd6b8-6b98-5236-94cd-54729615c77f.apk))
<img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/wlmedia.gif"/><img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_3.jpg"/><br/>

<img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_1.jpg"/><img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_2.jpg"/><br/>

<img width="610" height="270" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_4.jpg"/><br/>
<img width="610" height="270" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/90.jpg"/><br/>
<img width="610" height="270" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/alphvideo.gif"/>

## 三、集成使用
### 3.1 Gradle:

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
			wlMedia.start();
		}

		@Override
		public void onError(int code, String msg) {

		}

		@Override
		public void onComplete(WlComplete type, String msg) {

		}

		@Override
		public void onTimeInfo(double currentTime, double bufferTime) {

		}

		@Override
		public void onSeekFinish() {

		}

		@Override
		public void onLoopPlay(int loopCount) {

		}

		@Override
		public void onLoad(boolean load) {
			
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

		}
	});

	wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
		@Override
		public void initSuccess() {
			wlMedia.prepared();
		}

		@Override
		public void onSurfaceChange(int width, int height) {

		}

		@Override
		public void moveX(double value, int move_type) {

		}

		@Override
		public void onSingleClick() {

		}

		@Override
		public void onDoubleClick() {

		}

		@Override
		public void moveLeft(double value, int move_type) {

		}

		@Override
		public void moveRight(double value, int move_type) {

		}
	});
    

```

## 四、讨论群（1085618246）
<img width="238" height="250" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/wlmedia_ercode.png"/><br/>


    
    
## 五、混淆
    -keep class com.ywl5320.wlmedia.* {*;} 
    
## 六、注意事项
#### 6.1 播放器activity配置：
    
	android:configChanges="orientation|keyboardHidden|screenSize"

	
#### 6.2 播放器生命周期逻辑
	6.2.1、对于视频播放，new一个对象就对应播放一路视频，在退出播放页面时，调用exit停止并销毁资源。
	6.2.2、对于音频，new一个对象就对应播放一个音频，PlayModel设置为：WlPlayModel.PLAYMODEL_ONLY_AUDIO 即可。在退出播放页面时，调用exit停止并销毁资源。
	6.3.3、常规播放流程（具体可看demo）：
	如：APP启动->startactivity->new WlMedia()->播放中各种操作->关闭播放页面(exit())
	
	
## 七、使用本库APP（如果你的APP使用了本库，请告诉我哦~）
| [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_huisheng.png" alt="荟声"/>](http://app.mi.com/details?id=com.vada.huisheng "荟声") | [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_ruixin.png" alt="睿芯智能"/>](http://app.mi.com/details?id=com.zhituan.ruixin "睿芯智能") |[<img width="100" height="100" src="https://img-blog.csdnimg.cn/20201028121927255.png" alt="录音转文字助手"/>](https://appgallery.huawei.com/#/app/C100432201 "录音转文字助手")|
|---|---|---|

## 八、相关实例博客
#### [Android获取（网络和本地）视频缩略图](https://blog.csdn.net/ywl5320/article/details/107576410)

## 九、参考资料
#### [我的视频课程（基础）：《（NDK）FFmpeg打造Android万能音频播放器》](https://edu.csdn.net/course/detail/6842)
#### [我的视频课程（进阶）：《（NDK）FFmpeg打造Android视频播放器》](https://edu.csdn.net/course/detail/8036)
#### [我的视频课程（编码直播推流）：《Android视频编码和直播推流》](https://edu.csdn.net/course/detail/8942)
#### [我的视频课程（C++ OpenGL）：《Android C++ OpenGL》](https://edu.csdn.net/course/detail/19367)
#### [测试音视频文件地址（提取码：ivbh）](https://pan.baidu.com/s/1Gkm9cgmsvk4dXGPZVyHgZw)

## 十、核心三方库
[FFmpeg](http://ffmpeg.org/)
[OpenSSL](https://github.com/openssl/openssl)
[SoundTouch](http://www.surina.net/soundtouch/)


### Create By：ywl5320 2019-12-16





