## &#x1F680;wlmedia&#x1F680;
#### Android / HarmonyOS 音视频播放SDK，几句代码即可实现音视频播放功能~
#### 使用简单，功能丰富，支持手机、车机系统、电视、电视盒子、手表等智能设备
#### 全新架构，增强稳定性，性能和系统播放器接近
## 一 功能&特点
- [x] 基本信息获取（音频：采样率、声道数、时长等，视频：宽、高、fps、时长等）
- [x] 支持file、http、https、udp、rtmp、rtp、rtsp、byte[]等几乎所有协议
- [x] 支持AV1解码
- [x] 可选音频、视频、音视频播放模式
- [x] 软解硬解设置
- [x] 无缝切换surface
- [x] 支持多实例播放
- [x] 支持播放完成（EOF）后，再次seek又继续播放
- [x] 支持媒体自由切换
- [x] 支持FFmpeg参数设置
- [x] 支持byte[]数据类型
- [x] 音视频加密播放
- [x] 支持音视频帧解码
- [x] 支撑音视频变速变调
- [x] 支撑透明视频播放（可实现不错的视觉效果）
- [x] 字幕选择
- [x] 内置循环播放
- [x] 链接超时设置
- [x] 缓存大小设置（时间维度）
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
- [x] 支持实时获取音频PCM数据 
- [x] 支持设置音视频同步偏移
- [x] 支持外部OpenGL渲染（如：Unity、Cocos2d等视频播放）
## 二 集成使用
### [Android](doc/android/android.md)
### [HarmonyOS](doc/android/android.md)
## 三 效果展示
### Android
|  常规播放  |  透明视频  |  多Surface渲染  | 多实例播放  | 播放音频 | 音量分贝  |
| :----: | :----: | :----: | :----: |:----:| ------ |
| <img src="doc/android/imgs/normal.gif" width="320" height="693"> | <img src="doc/android/imgs/alpha.gif" width="320" height="693"> | <img src="doc/android/imgs/multisurface.gif" width="320" height="693"> | <img src="doc/android/imgs/multiplayer.gif" width="320" height="693"> | <img src="doc/android/imgs/audio.gif" width="320" height="693"> | <img src="doc/android/imgs/pcmdb.gif" width="320" height="693"> |

### HarmonyOS
|  常规播放  |  透明视频  |  多Surface渲染  | 多实例播放  | 播放音频 | 音量分贝  |
| :----: | :----: | :----: | :----: |:----:| ------ |
| <img src="doc/ohos/imgs/normal.gif" width="320" height="693"> | <img src="doc/ohos/imgs/alpha.gif" width="320" height="693"> | <img src="doc/ohos/imgs/multisurface.gif" width="320" height="693"> | <img src="doc/ohos/imgs/multiplayer.gif" width="320" height="693"> | <img src="doc/ohos/imgs/audio.gif" width="320" height="693"> | <img src="doc/ohos/imgs/pcmdb.gif" width="320" height="693"> |

## 四 免费和增值服务
`WlMedia` 是按应用根据 `包名` 定制的，分免费版和付费定制版
### 免费版
- 仅`视频画面`带有`wlmedia`水印，其余功能无限制

### 增值版
- 如要定制去水印，可付费根据包名定制打包，具体费用和规则请邮件联系：
  - 邮箱：[ywl5320@163.com](mailto:ywl5320@163.com)

## 五 讨论群（1085618246）
![QQ](doc/imgs/qq_ercode.png)

## 六 核心三方库
- [ffmpeg](http://ffmpeg.org/)
- [openssL](https://github.com/openssl/openssl)
- [soundtouch](http://www.surina.net/soundtouch/)
- [dav1d](https://code.videolan.org/videolan/dav1d)

#### Update By：ywl5320 2025-02-04
#### Create By：ywl5320 2019-12-16
