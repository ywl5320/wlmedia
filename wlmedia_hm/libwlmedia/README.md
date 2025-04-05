## &#x1F680;wlmedia&#x1F680;
#### HarmonyOS & Android 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 使用简单，功能丰富，支持手机、车机系统、电视、电视盒子、手表等智能设备

## 一 功能&特点
- 支持系统：5.0.0(12)
- 支持架构：arm64-v8a
- 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- 支持file、http、https、udp、rtmp、rtp、rtsp、byte[]等几乎所有协议
- 支持AV1解码
- 可选音频、视频、音视频播放模式
- 软解硬解设置
- 无缝切换XComponent
- 支持多实例播放
- 支持播放完成（EOF）后，再次seek又继续播放
- 支持媒体自由切换
- 支持FFmpeg参数设置
- 支持byte[]数据类型
- 音视频加密播放
- 支持音视频帧解码
- 支撑音视频变速变调
- 支撑透明视频播放（可实现不错的视觉效果）
- 字幕选择
- 内置循环播放
- 链接超时设置
- 缓存大小设置（时间维度）
- 音轨选择
- 音频指定采样率设置
- 音频指定声道播放
- 视频截屏
- 视频首帧图片或指定时间图片获取
- 视频任意比例设置
- 视频旋转角度设置（0,90,180,270）
- 视频镜像模式设置
- 视频背景颜色设置（默认黑色）
- 视频支持同时多个surface渲染（如：KTV大小屏幕）
- 支持实时获取音频PCM数据
- 支持设置音视频同步偏移
- 支持外部OpenGL渲染（如：Unity、Cocos2d等视频播放）
## 二 集成使用
### 2.1 导入SDK
```
ohpm i @ywl5320/libwlmedia
```
### 2.2 常用权限
```json5
#module.json5
"requestPermissions": [
    {
    "name": "ohos.permission.INTERNET"
    }
]
```

### 2.3 设置XComponentController
```typescript
let wlPlayer: WlPlayer = new WlPlayer();
let wlComponentController: WlComponentController = new WlComponentController(this.wlPlayer);
...
XComponent({
        type: XComponentType.SURFACE,
        controller: this.wlComponentController
    })
    .width('100%')
    .height('100%')
...
```
### 2.4 基础调用代码（[更多实例见Demo](https://github.com/ywl5320/wlmedia)）
```typescript
@Entry
@Component
struct Index{
    private wlPlayer: WlPlayer | null = null;
    private wlComponentController: WlComponentController | null = null;

    aboutToAppear(): void {
        this.wlPlayer = new WlPlayer();
        this.wlPlayer.setOnMediaInfoListener({
        onPrepared: (): void => {
            // 异步准备好后回调，这里调用 wlplayer.start() 开始播放
            this.wlPlayer?.start();
        },
        onTimeInfo: (currentTime: number, bufferTime: number): void => {
            // 时间进度回调 
        },
        onComplete: (wlCompleteType: WlCompleteType, msg: string): void => {
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
            } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_LOOP) {
                // 循环播放中，每开始新的一次循环，会回调此接口
            }
        },
        onLoadInfo: (loadStatus: WlLoadStatus, progress: number, speed: number): void => {
            // 加载状态回调
            if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_START) {
            // 开始加载
            } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_PROGRESS) {
            // 加载进度
            } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_FINISH) {
            // 加载完成
            }
        },
        onFirstFrameRendered: (): void => {
            // seek 完成回调
        },
        onSeekFinish: (): void => {
            // seek 完成回调
        },
        onAudioInterrupt: (type: WlAudioInterruptType, hint: WlAudioInterruptHint) => {
            // 音频打断，和系统一致
        }
        this.wlComponentController = new WlComponentController(this.wlPlayer);
        this.wlPlayer.setClearLastVideoFrame(this.wlComponentController.getUniqueNum(), false);
    }

    // 退出 销毁资源
    onBackPress(): boolean | void {
        this.wlPlayer?.release()
    }

    build() {
        Column() {
            XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController
        })
          .onLoad((event) => {
            // 加载完成后就开始播放
            this.wlPlayer?.setSource(this.filesDir + "/huoying_cut.mkv");
            this.wlPlayer?.prepare();
          })
          .width('100%')
          .height('100%')
        }
    }
}

```
## 三 详细 API
- [1. WlPlayer（音视频播放SDK）](https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/wlplayer_api.md)
- [2. WlMediaUtil（音视频工具类SDK）](https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/wlmediautil_api.md)

## 四 效果展示
|  常规播放  |  透明视频  |  多Surface渲染  | 多实例播放  | 播放音频 | 音量分贝  |
| :----: | :----: | :----: | :----: |:----:| ------ |
| <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/normal.gif" width="320" height="693"> | <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/alpha.gif" width="320" height="693"> | <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/multisurface.gif" width="320" height="693"> | <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/multiplayer.gif" width="320" height="693"> | <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/audio.gif" width="320" height="693"> | <img src="https://gitee.com/ywl5320/wlmedia/blob/master/doc/ohos/imgs/pcmdb.gif" width="320" height="693"> |

## 五 免费和增值服务
`WlMedia` 是按应用根据 `包名` 定制的，分免费版和付费定制版
### 免费版
- 仅`视频画面`带有`wlmedia`水印，其余功能无限制

### 增值版
- 如要定制去水印，可付费根据包名定制打包，具体费用和规则请邮件联系：
    - 邮箱：[ywl5320@163.com](mailto:ywl5320@163.com)

## 六 讨论群（1085618246）
![QQ](https://gitee.com/ywl5320/wlmedia/blob/master/doc/imgs/qq_ercode.png)

## 七 核心三方库
- [ffmpeg](http://ffmpeg.org/)
- [openssL](https://github.com/openssl/openssl)
- [soundtouch](http://www.surina.net/soundtouch/)
- [dav1d](https://code.videolan.org/videolan/dav1d)

#### Update By：ywl5320 2025-04-05
#### Create By：ywl5320 2025-02-06
