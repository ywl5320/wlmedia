# wlmedia
#### Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 功能丰富，支持手机、电视盒子等设备。<br/>
#### 如有疑问可联系：ywl5320@163.com

## 一、功能特点

- [x] 兼容androidx和support库
- [x] 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- [x] 支持file、http、https、rtmp、rtp、rtsp、byte[]等
- [x] 可选音频、视频、音视频播放模式
- [x] 软解硬解设置
- [x] 无缝切换surface（也可自定义surfaceview、textureview）
- [x] 支持多实例播放
- [x] 支持媒体自由切换
- [x] 支持播放rtsp/rtp设置udp/tcp模式
- [x] 支持byte[]数据解码
- [x] 支撑音视频变速变调
- [x] 支撑透明视频播放（可实现不错的视觉效果）
- [x] 字幕选择
- [x] 内置循环播放
- [x] 链接超时设置
- [x] 缓存大小设置（按时间、内存和队列设置）
- [x] 音视频加密播放
- [x] 网络流内部断线重连
- [x] 音轨选择
- [x] 音频声道选择
- [x] 音频PCM数据获取
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
### 3.1 Gradle: [ ![Download](https://api.bintray.com/packages/ywl5320/maven/wlmedia/images/download.svg?version=1.1.5) ](https://bintray.com/ywl5320/maven/wlmedia/1.1.5/link)

    implementation 'ywl.ywl5320:wlmedia:1.1.5' //(全平台约26M)
	
	implementation 'ywl.ywl5320:wlmedia:1.1.4-small' //(全平台约13M，只包含常用格式)
	
	
### 3.2 常用权限
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

#### 3.3.2 基础调用代码（更多功能见Demo）

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
                    //切歌时回调
                }
                else if(type == WlComplete.WL_COMPLETE_TIMEOUT)
                {
                    //超时回调
                }
                else if(type == WlComplete.WL_COMPLETE_ERROR)
                {
                    //错误回调
                }
                else if(type == WlComplete.WL_COMPLETE_EOF)
                {
                    //播放到文件结尾回调
                }
                else if(type == WlComplete.WL_COMPLETE_HANDLE)
                {
                    //手动触发停止回调
                }
                else if(type == WlComplete.WL_COMPLETE_LOOP)
                {
                    //循环播放回调
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
    // or
    //播放视频需要在surface创建好回调里面开始播放
    wlSurfaceView.setWlMedia(wlMedia);
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
    
    
    //自定义滤镜（以黑白滤镜为例）
    String fs = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "vec4 v=texture2D(sTexture, ft_Position); " +
                "float average = (v.r + v.g + v.b) / 3.0;" +
                "gl_FragColor = vec4(average, average, average, v.a);" +
                "}";
    wlMedia.setfShader(fs);
    wlMedia.changeFilter();
	
	//透明视频播放
	wlSurfaceView.enableTransBg(true); //设置surface背景透明
	wlMedia.setVideoClearColor(0, 0, 0, 0); //设置背景颜色为透明
	wlMedia.enableTransVideo(WlVideoTransType.VIDEO_TRANS_LEFT_ALPHA); //设置透明视频模式（alpha通道位置）
    

```

#### 3.3.3 常用API

```java

    public WlMedia()    //构造函数（支撑多实例）
    public void setSource(String source)    //设置数据源
    public void setSourceType(WlSourceType sourceType)  //设置数据源类型（常规播放、byte[]播放或加密播放）
    public boolean isPlaying()  //是否在播放中
    public void prepared()  //异步准备
    public void start() //异步准备好后开始播放
    public void stop()  //停止播放（不会释放全部资源，适用于页面内停止）
    public void exit()  //退出播放器（会释放所有资源）
    public void pause()     //暂停
    public void resume()    //播放（对应暂停）
    public void next()  //播放下一曲（切歌）
    public void setSampleRate(WlSampleRate wlSampleRate)    //设置音频重采样采用率
    public void setSpeed(float speed)   //设置音视频播放速度
    public void setPitch(float pitch)   //设置音频播放音调
    public void setUseSoundTouch(boolean useSoundTouch)     //设置是否启用SoundTouch（若不启用变速变调不生效，默认启用）
    public void setVolume(int percent)  //设置音量大小
    public void setMute(WlMute mute)    //设置声道
    public void scaleVideo(int w, int h, WlVideoRotate videoRotate)    //设置视频画面比例和旋转角度
    public String[] getAudioChannels()  //获取所有音轨
    public String[] getSubTitleChannels()   //获取所有字幕
    public double getDuration()     //获取总时长
    public void setLoopPlay(boolean loopPlay)   //是否开启循环播放
    public void setPlayModel(WlPlayModel playModel)     //设置播放模式（只播放音频、只播放视频或音频视频都播放）
    public double getNowClock()     //获取当前播放时间戳
    public void seek(double secds)  // seek到指定位置
    public void setAudioChannel(int index)  //切换对应音轨
    public void setSubTitleChannel(int index) //切换对应字幕
    public void setShowPcm(boolean showPcm) //是否返回PCM数据
    public void takePicture()   //截屏
    public void setfShader(String fShader)  //设置滤镜
    public void changeFilter()  //应用滤镜
    public void setTransportModel(WlTransportModel transportModel)  //设置rtsp播放模式（UDP或TCP）
    public void setSmoothTime(boolean smooth) //是否把每一帧时间戳返回（默认一秒返回一次）
    public void setClearLastPicture(boolean clearLastPicture)   //播放完后是否清屏（false：将保留视频最后一帧）
    public void setTimeOut(int timeOut) //设置超时时间
    public void setVideoClearColor(float rgba_r, float rgba_g, float rgba_b, float rgba_a)  //设置视频播放背景为指定颜色（默认黑色）
    public void setBufferSize(WlBufferType bufferType, double bufferValue)  //设置底层缓存模式（队列大小、文件大小或指定时间以内）
    public void enableTransVideo(WlVideoTransType transType) //设置透明视频
    
    public WlMediaUtil()    //音视频工具类
    public WlMediaInfoBean getMediaInfo()   //获取音视频基础信息
    public WlVideoImgBean getVideoImg(double time, boolean keyFrame) //获取视频指定时间的图片
    

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
	

#### 6.3 高版本系统后台播放音频卡顿问题
	建议在新的进程中播放音频，比如：
	<service android:name=".AudioService"
            android:process=":wlmedia"/>
	    
        绑定服务
	bindService(intent,serviceConnection,BIND_WAIVE_PRIORITY);//注意第三个参数BIND_WAIVE_PRIORITY
	
## 七、使用本库APP（如果你的APP使用了本库，也可以告诉我哦~）
| [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_huisheng.png" alt="荟声"/>](http://app.mi.com/details?id=com.vada.huisheng "荟声") | [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_ruixin.png" alt="睿芯智能"/>](http://app.mi.com/details?id=com.zhituan.ruixin "睿芯智能") |	……	|
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





