# wlmedia
#### Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 功能丰富，支持手机、电视盒子等设备。<br/>

## 一、功能特点

- [x] 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- [x] 支持file、http、https、rtmp、rtp、rtsp等
- [x] 可选音频、视频、音视频播放模式
- [x] 软解硬解设置
- [x] 无缝切换surface（也可自定义surfaceview、textureview）
- [x] 支持多实例播放
- [x] 支持媒体切换
- [x] 支持播放rtsp/rtp切换udp/tcp模式
- [x] 支持byte[]数据解码
- [x] 字幕选择
- [x] 内置循环播放
- [x] 链接超时设置
- [x] 音视频加密播放
- [x] 网络流内部断线重连
- [x] 音轨选择
- [x] 音频变速变调
- [x] 音频声道选择
- [x] 音频PCM数据获取
- [x] 音频指定采样率设置
- [x] 视频截屏
- [x] 视频首帧图片或指定时间图片获取
- [x] 视频任意比例设置
- [x] 视频shader自定义视频滤镜（动态设置）
- [ ] 在线视频下载（缓存）

## 二、实例展示 ([测试APK下载](https://outexp-beta.cdn.qq.com/outbeta/2020/07/19/comvvideoplayer_1.0.3_ed6a14a8-07a6-5145-84fb-149c6729b0d6.apk))
<img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/wlmedia.gif"/><img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_3.jpg"/><br/>

<img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_1.jpg"/><img width="300" height="560" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_2.jpg"/><br/>

<img width="610" height="270" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_4.jpg"/><br/>
<img width="610" height="270" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_5.jpg"/>

## 三、集成使用
### 3.1 Gradle: [ ![Download](https://api.bintray.com/packages/ywl5320/maven/wlmedia/images/download.svg?version=1.0.9) ](https://bintray.com/ywl5320/maven/wlmedia/1.0.9/link)

    implementation 'ywl.ywl5320:wlmedia:1.0.9'
### 3.2 权限
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>//（可选）
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
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

#### 3.3.2 调用代码

```java

    WlMedia wlMedia = new WlMedia();//创建播放器实例（支持多实例）
    wlMedia.setSource(url);//设置媒体源
    wlMedia.setSourceType(WlSourceType.NORMAL);//设置数据源类型（常规、加密、bute[]）
    //wlMedia.setCodecType(WlCodecType.CODEC_MEDIACODEC);//设置解码模式（默认硬解码）
    //wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);//设置播放模式（默认音视频都播放）
    //wlMedia.setClearLastPicture(false);//设置结束后释放显示最后一帧视频
    
    //常用回调
    //准备好后回调
    wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
        
    //加载回调
    wlMedia.setOnLoadListener(new WlOnLoadListener() {
            @Override
            public void onLoad(boolean load) {
                
            }
        });
        
    //暂停回调
    wlMedia.setOnPauseListener(new WlOnPauseListener() {
            @Override
            public void onPause(boolean pause) {
            
            }
        });
        
    //播放时间回调
    wlMedia.setOnTimeInfoListener(new WlOnTimeInfoListener() {
            @Override
            public void onTimeInfo(double currentTime, double bufferTime) {
                
            }
        });
        
    //播放完成回调
    wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete(WlComplete type) {
        
                if(type == WlComplete.WL_COMPLETE_NEXT)
                {
                    
                }
                else if(type == WlComplete.WL_COMPLETE_TIMEOUT)
                {
                    
                }
                else if(type == WlComplete.WL_COMPLETE_ERROR)
                {
                    
                }
                else if(type == WlComplete.WL_COMPLETE_EOF)
                {
                    
                }
            }
        });
        
    //错误回调
    wlMedia.setOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int code, String msg) {
            
            }
        });
        
        
        
    //如果单独播放音频直接调用
    wlMedia.prepared();//wlMedia.next();
    
    //播放视频需要在surface创建好回调里面开始播放
    wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.prepared();//此时surface创建好了

            }

            @Override
            public void moveX(double value, int move_type) {
                //横向滑动surface回调，可用于seek
            }

            @Override
            public void onSingleClick() {
                //surface点击事件
            }

            @Override
            public void onDoubleClick() {
                //surface双击事件
            }

            @Override
            public void moveLeft(double value, int move_type) {
                //surface左侧滑动（可改变屏幕亮度）
            }

            @Override
            public void moveRight(double value, int move_type) {
                //surface右侧滑动（可改变播放器音量）
            }
        });
    
    //退出并回收资源
    wlMedia.exit();
    

    //获取媒体基本信息(耗时操作)
    WlMediaUtil wlMediaUtil = new WlMediaUtil();
    wlMediaUtil.setSource(url);
    if(wlMediaUtil.init() == 0)
    {
        WlMediaInfoBean wlMediaInfoBean = wlMediaUtil.getMediaInfo();
    }
    else{
    
    }
    wlMediaUtil.release();
    
    //获取视频指定帧数据(耗时操作)
    WlMediaUtil wlMediaUtilImg = new WlMediaUtil();
    wlMediaUtilImg.setSource(url);
    wlMediaUtilImg.init();
    wlMediaUtilImg.openCodec();
    WlVideoImgBean wlVideoImgBean = wlMediaUtilImg.getVideoImg(1000, false);
    if(wlVideoImgBean != null)
    {
        WlLog.d("java width:" + wlVideoImgBean.getWidth() + ",height:" + wlVideoImgBean.getHeight() + ",time:" + wlVideoImgBean.getTime());
    }
    wlMediaUtil.release();

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
	

#### 6.3 高本版系统后台播放音频卡顿问题
	建议在新的进程中播放音频，比如：
	<service android:name=".AudioService"
            android:process=":wlmedia"/>
	    
        绑定服务
	bindService(intent,serviceConnection,BIND_WAIVE_PRIORITY);//注意第三个参数BIND_WAIVE_PRIORITY
	
## 七、使用本库APP（如果你的APP使用了本库，也可以告诉我哦~）
| [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_huisheng.png" alt="荟声"/>](http://app.mi.com/details?id=com.vada.huisheng "荟声") | [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_ruixin.png" alt="睿芯智能"/>](http://app.mi.com/details?id=com.zhituan.ruixin "睿芯智能") |	……	|
|---|---|---|



## 八、参考资料
#### [我的视频课程（基础）：《（NDK）FFmpeg打造Android万能音频播放器》](https://edu.csdn.net/course/detail/6842)
#### [我的视频课程（进阶）：《（NDK）FFmpeg打造Android视频播放器》](https://edu.csdn.net/course/detail/8036)
#### [我的视频课程（编码直播推流）：《Android视频编码和直播推流》](https://edu.csdn.net/course/detail/8942)
#### [我的视频课程（C++ OpenGL）：《Android C++ OpenGL》](https://edu.csdn.net/course/detail/19367)

## 九、核心三方库
[FFmpeg](http://ffmpeg.org/)
[OpenSSL](https://github.com/openssl/openssl)
[SoundTouch](http://www.surina.net/soundtouch/)


### Create By：ywl5320 2019-12-16





