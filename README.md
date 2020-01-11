# wlmedia
android 音视频播放SDK，几句代码即可实现音视频播放功能~
## 功能
##### **支持：http、https、rtsp、rtp、rtmp、byte[]、加密视频和各种文件格式视频；
##### **截图、音轨选择、自定义视频滤镜、变速变调、声道切换、无缝切换surface（surfaceview和textureview）、视频比例设置等；
##### **目前包含全部FFmpeg音视频解码器，故SDK包比较大；如需定制大小，可联系我~（Email：ywl5320@163.com）

### 连续播放10小时直播内存情况
<img width="640" height="360" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/play_video.png"/><br/>
<img width="640" height="313" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/play_memery.png"/>


## 1、Usage

### Gradle: [ ![Download](https://api.bintray.com/packages/ywl5320/maven/wlmedia/images/download.svg?version=1.0.5) ](https://bintray.com/ywl5320/maven/wlmedia/1.0.5/link)

    implementation 'ywl.ywl5320:wlmedia:1.0.5'


## 2、实例图片

<img width="360" height="640" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/wlmedia.gif"/><br/>
<img width="360" height="640" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/demo.png"/><br/>
<img width="360" height="640" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/video_pic.png"/>


## 3、调用方式

### 配置NDK编译平台:

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
	
### 基本权限

	<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>

## 4、接入代码

#### 4.1、视频surface选择
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
        
#### 4.2、创建播放器

##### 4.2.1 播放视频
```java
	WlMedia wlMedia = WlMedia.getInstance();//单例模式主要用于视频，音频可以new对象
    wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);//同时播放音频视频
	wlSurfaceView.setWlMedia(wlMedia);//给视频surface设置播放器
	
	//异步准备完成后开始播放
	wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();//开始播放
            }
        });
    //surface初始化好了后 开始播放视频（用于一打开activity就播放场景）
    wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.setSource("/storage/sdcard1/fcrs.1080p.mp4");//设置数据源
                wlMedia.prepared();//异步开始准备播放
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {
                wlMedia.seek(value);//seek
            }
        });
    //自定义滤镜方式
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
```
    
##### 4.2.2 播放音频
```java
    WlMedia wlMedia = WlMedia.getInstance();//或 new WlMedia();
    wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_AUDIO);//设置只播放音频（必须）
    wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(this, "mydream.m4a"));//设置数据源
    wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
        @Override
        public void onPrepared() {
            wlMedia.start();
        }
    });
    wlMedia.prepared();
```
##### 4.2.3 播放加密视频文件
```java
    WlMedia wlMedia = WlMedia.getInstance();
    wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
    wlSurfaceView.setWlMedia(wlMedia);
    wlMedia.setBufferSource(true, true);//必须都为true
    wlMedia.setOnDecryptListener(new WlOnDecryptListener() {
    
        //解密算法
        @Override
        public byte[] decrypt(byte[] encrypt_data) {
            int length = encrypt_data.length;
            for(int i = 0; i < length; i++)
            {
                encrypt_data[i] = (byte) ((int)encrypt_data[i] ^ 666);
            }
            WlLog.d("decrypt");
            return encrypt_data;
        }
    });
    wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(WlEncryptActivity.this, "fhcq-ylgzy-dj-encrypt.mkv"));//加密视频源
                wlMedia.prepared();
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {
                wlMedia.seek(value);
            }
        });
```
##### 4.2.4 播放byte[]音视频数据
```java
    wlMedia = WlMedia.getInstance();
    wlMedia.setBufferSource(true, false);//必须第一个为true,第二个为false
    wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_VIDEO);//根据byte类型来设置（可以音频、视频、音视频）
    wlTextureView.setWlMedia(wlMedia);
    wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
    wlTextureView.setOnVideoViewListener(new WlOnVideoViewListener() {
        @Override
        public void initSuccess() {
            new Thread(new Runnable() {
                long length = 0;
                @Override
                public void run() {
                    try {
                        //从文件模拟byte[]数据
                        File file = new File(WlAssetsUtil.getAssetsFilePath(WlBufferActivity.this, "mytest.h264"));
                        FileInputStream fi = new FileInputStream(file);
                        byte[] buffer = new byte[1024 * 64];
                        int buffersize = 0;
                        int bufferQueueSize = 0;
                        exit = false;
                        while(true)
                        {
                            if(exit)
                            {
                                break;
                            }
                            if(wlMedia.isPlay())
                            {
                                WlLog.d("read thread " + bufferQueueSize);
                                if(bufferQueueSize < 20)//控制队列大小，不然内存可能会增大溢出
                                {
                                    buffersize = fi.read(buffer);
                                    if(buffersize <= 0)
                                    {
                                        WlLog.d("read thread ==============================  read buffer exit ...");
                                        wlMedia.putBufferSource(null, -1);
                                        break;
                                    }
                                    bufferQueueSize = wlMedia.putBufferSource(buffer, buffersize);//返回值为底层buffer队列个数
                                    while(bufferQueueSize < 0)
                                    {
                                        bufferQueueSize = wlMedia.putBufferSource(buffer, buffersize);
                                    }
                                }
                                else
                                {
                                    bufferQueueSize = wlMedia.putBufferSource(null, 0);
                                }
                                sleep(10);
                            }
                            else
                            {
                                WlLog.d("buffer exit");
                                break;
                            }

                        }
                        wlMedia.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            wlMedia.prepared();
        }

        @Override
        public void moveSlide(double value) {

        }

        @Override
        public void movdFinish(double value) {

        }
    });
```
	
##### 4.2.5 获取视频图片（类似于缩略图）
```java
	WlMediaUtil wlMediaUtil = new WlMediaUtil();//可以用单利模式，自己封装图片加载库
	Bitmap bitmap = wlMediaUtil.getVideoPic(url);
```


	
	
## 5、API

#### 5.1 播放器API
    
```java
    public WlMedia();//构造函数，不依赖上下文
	
	public static WlMedia.getInstance();//单例模式 用于APP周期内只创建一次播放器实例（主要视频），当APP退出时调用WlMedia.releaseAndExit();释放surface资源
	
	public static void releaseAndExit();//退出APP时调用，释放surface资源
    
    public void setSource(String source);//设置数据源（可以是file、url）
    
    public void setPlayModel(WlPlayModel playModel);//设置音视频播放模式（可以独立播放音频和视频或者同时播放音视频，默认同时播放音视频）
    
    public void setCodecType(WlCodecType codecType);//设置解码器类型（默认优先使用硬解码）
    
    public void prepared();异步开始准备播放，成功后会回调WlOnPreparedListener接口
    
    public void next();//切歌（数据源设置方法为：setSource）
    
    public void start();//开始播放（当异步准备完成后，在WlOnPreparedListener回调里面开始播放）
    
    public void pause();//播放暂停
    
    public void resume();//暂停后恢复播放
    
    public boolean isPlay();//判断是否在播放中
    
    public void setMute(WlMute mute);//设置声道（立体声、左声道、右声道）
    
    public void setVolume(int percent);//设置声音（0~100）
    
    public void setSpeed(float speed);//设置播放速度（0.5f~2f）
    
    public void setPitch(float pitch);//设置播放音调（0.5f~2f）
    
    public void seek(double secds);//seek 目前是到关键帧（实测精确到时间帧，体验不好，平均等待时间过长，故弃之）
    
    public void setSampleRate(WlSampleRate wlSampleRate);//设置音频采样率（用于回调pcm时需要制定采样率情况）
    
    public void setShowPcm(boolean showPcm);//设置是否回调pcm音频数据，回调方法：WlOnPcmDataListener
    
    public double getNowClock();//获取当前播放时间
    
    public double getDuration();//获取时长（如果有在异步准备好后可获取）
    
    public void takePicture();//视频截图（是截取surface屏幕图片，自己可根据surface大小和视频宽高对视频图片进行裁剪），回调方法：WlOnTakePictureListener
    
    public int putBufferSource(byte[] buffer, int buffer_len);//byte[]方式播放数据入口，返回值为底层队列大小，当buffer_len == 0时，也返回底层队列大小。
    
    public void seekNoCallTime();//设置不回调时间，可用于seek过程中UI展示（具体看自己的需求）
    
    public void onSurfaceCreate(Surface surface);//设置surface（用于自定义surfaceview）
    
    public void onSurfaceChange(int width, int height, Surface surface);//surface大小改变
    
    public void onSurfaceDestroy();//surface销毁
    
    public void release();//回收surface底层opengl资源
    
    public void setTransportModel(WlTransportModel transportModel);//设置播放rtsp的方式（UDP/TCP）
    
    public void setClearLastPicture(boolean clearLastPicture);//视频播放完最后一帧是否清屏，true:停留在最后一帧;false:清屏为黑色
    
    /**
     * 设置数据源模式
     * 1、bufferSource为true，encryptFileSource为false时，是byte[]模式
     * 2、bufferSource为true，encryptFileSource为true时，是file模式，可用于加密视频播放，（回WlOnDecryptListener调里面自己解密）
     * @param bufferSource byte[]模式
     * @param encryptFileSource 是否加密
     */
    public void setBufferSource(boolean bufferSource, boolean encryptFileSource);
    
    public void setvShader(String vShader);//设置顶点着色器
    
    public void setfShader(String fShader);//设置纹理着色器
    
    public void changeFilter();//设置着色器后用于使之生效（切换滤镜）
    
    public void scaleVideo(int w, int h);//设置视频宽高比
    
    public int getVideoWidth();//获取视频宽
    
    public int getVideoHeight();//获取视频高
    
    public String[] getAudioChannels();//获取所有音轨，返回音轨名字（默认为Audio）
    
    public void setAudioChannel(int index);//设置播放音轨，index为getAudioChannels中得到音轨的索引
	
	public void setDelayOffsetTime(double offsetTime);//用于单独播放视频（buffer）时动态调整视频渲染速率，单位秒。
```
    
#### 5.2 WlSurfaceView和WlTextureView
    SDK自带这2个自定义surfaceview，通过setWlMedia方法与播放器关联，updateMedia方法用于播放中切换显示surface；WlOnVideoViewListener为surface初始化完成回调。
    开发者可以根据自己情况自定义自己的surfaceview，实现自己的特殊需求。
    
    
    ***注：***
    自定义surface必须调用方法：
```java
    public void onSurfaceCreate(Surface surface);
    
    public void onSurfaceChange(int width, int height, Surface surface);
    
    public void onSurfaceDestroy();
```
    
    
#### 5.3 回调函数
```java
    public interface WlOnPreparedListener; //异步准备完成回调
    
    public interface WlOnTimeInfoListener; //播放时间回调
    
    public interface WlOnLoadListener; //加载状态回调
    
    public interface WlOnErrorListener; //错误回调
    
    public interface WlOnCompleteListener; //播放（资源回收）完成回调
    
    public interface WlOnDecryptListener; //解密算法回调
    
    public interface WlOnPcmDataListener; //音频PCM数据回调
    
    public interface WlOnTakePictureListener; //截图回调
    
    public interface WlOnVideoViewListener; //surface 初始化完成回调
```
    
    
## 6、混淆
    -keep class com.ywl5320.wlmedia.* {*;} 
	
## 7、注意事项
#### 7.1 播放器activity配置：
	
	android:configChanges="orientation|keyboardHidden|screenSize"
	android:launchMode="singleTask"//(建议)
	
#### 7.2 播放器生命周期逻辑
	7.2.1、对于视频播放，提供单利模式，整个APP周期只创建一次播放器，当APP周期结束时，再释放整个实例即可。</br>
	7.2.2、对于音频，单例和new对象都可以。</br>
	7.3.3、常规播放流程（具体可看demo）：</br>
	如：APP启动->startactivity->WlMedia.getInstance()->播放中各种操作->关闭播放页面(stop->complete/orerror->activityfinish)->App退出（WlMedia.releaseAndExit()）
	

#### 7.3 高本版系统后台播放音频卡顿问题
	建议在新的进程中播放音频，比如：
	<service android:name=".AudioService"
            android:process=":wlmedia"/>
	    
        绑定服务
	bindService(intent,serviceConnection,BIND_WAIVE_PRIORITY);//注意第三个参数BIND_WAIVE_PRIORITY
	
## 8、使用本库APP（如果你的APP使用了本库，也可以告诉我哦~）
| [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_huisheng.png" alt="荟声"/>](http://app.mi.com/details?id=com.vada.huisheng "荟声") | [<img width="100" height="100" src="https://github.com/wanliyang1990/wlmedia/blob/master/img/app_ruixin.png" alt="睿芯智能"/>](http://app.mi.com/details?id=com.zhituan.ruixin "睿芯智能") |	……	|
|---|---|---|



## 9、参考资料
### [我的视频课程（基础）：《（NDK）FFmpeg打造Android万能音频播放器》](https://edu.csdn.net/course/detail/6842)
### [我的视频课程（进阶）：《（NDK）FFmpeg打造Android视频播放器》](https://edu.csdn.net/course/detail/8036)
### [我的视频课程（编码直播推流）：《Android视频编码和直播推流》](https://edu.csdn.net/course/detail/8942)
### [我的视频课程（C++ OpenGL）：《Android C++ OpenGL》](https://edu.csdn.net/course/detail/19367)

### Create By：ywl5320 2019-12-16





