## &#x1F680;wlmedia&#x1F680; [English](README_EN.md) [中文](README.md)
#### Android & HarmonyOS Audio/Video SDK – Implement multimedia playback in just a few lines of code!
#### Simple integration, rich features, supports smartphones, car systems, TVs, TV boxes, smartwatches, and other devices.
## I. Features & Highlights
- [x] Basic media info retrieval (audio: sample rate, channels, duration; video: width, height, fps, duration)
- [x] Supports almost all protocols: `file`, `http`, `https`, `udp`, `rtmp`, `rtp`, `rtsp`, `byte[]`, etc.
- [x] AV1 decoding support
- [x] Playback modes: audio-only, video-only, or audio-video
- [x] Software/hardware decoding configuration
- [x] Seamless surface switching
- [x] Multi-instance playback support
- [x] Resume playback after EOF via seek
- [x] Dynamic media switching
- [x] Custom FFmpeg parameters
- [x] `byte[]` data type support
- [x] Encrypted audio/video playback
- [x] Audio/video frame decoding
- [x] Pitch and playback speed adjustment
- [x] Transparent video playback (visual effects)
- [x] Subtitle selection
- [x] Built-in loop playback
- [x] Connection timeout configuration
- [x] Cache size settings (time-based)
- [x] Audio track selection
- [x] Custom audio sample rate
- [x] Custom audio channel output
- [x] Video screenshot capture
- [x] Extract first frame or frame at specified time
- [x] Custom video aspect ratio
- [x] Video rotation (0°, 90°, 180°, 270°)
- [x] Video mirroring modes
- [x] Video background color (default: black)
- [x] Multiple simultaneous surfaces rendering (e.g., KTV dual screens)
- [x] Real-time PCM audio data retrieval
- [x] Audio/video sync offset adjustment
- [x] External OpenGL rendering (e.g., Unity, Cocos2d)

## II. Demos
### Android
|  Normal Playback  |  Transparent Video  |  Multi-Surface  | Multi-Instance | Audio Playback | Volume Meter  |
| :----: | :----: | :----: | :----: |:----:| ------ |
| <img src="doc/android/imgs/normal.gif" width="320" height="693"> | <img src="doc/android/imgs/alpha.gif" width="320" height="693"> | <img src="doc/android/imgs/multisurface.gif" width="320" height="693"> | <img src="doc/android/imgs/multiplayer.gif" width="320" height="693"> | <img src="doc/android/imgs/audio.gif" width="320" height="693"> | <img src="doc/android/imgs/pcmdb.gif" width="320" height="693"> |

### HarmonyOS
|  Normal Playback  |  Transparent Video  |  Multi-Surface  | Multi-Instance | Audio Playback | Volume Meter  |
| :----: | :----: | :----: | :----: |:----:| ------ |
| <img src="doc/ohos/imgs/normal.gif" width="320" height="693"> | <img src="doc/ohos/imgs/alpha.gif" width="320" height="693"> | <img src="doc/ohos/imgs/multisurface.gif" width="320" height="693"> | <img src="doc/ohos/imgs/multiplayer.gif" width="320" height="693"> | <img src="doc/ohos/imgs/audio.gif" width="320" height="693"> | <img src="doc/ohos/imgs/pcmdb.gif" width="320" height="693"> |

## III. Performance Comparison
### Android
#### vs. ExoPlayer
- Device: Xiaomi Redmi Note 7
- Media: `big_buck_bunny_cut.mp4`

|  ExoPlayer  |  wlplayer (no OpenGL)  |  wlplayer (OpenGL)  | wlplayer Realtime |
| :----: | :----: | :----: | :----: |
| <img src="doc/android/imgs/exoplayer.png" width="320" height="240"> | <img src="doc/android/imgs/wlplayer_no_opengl.png" width="320" height="240"> | <img src="doc/android/imgs/wlplayer_use_opengl.png" width="320" height="240"> | <img src="doc/android/imgs/wlplayer_profiler.png" width="320" height="240"> |

## IV. Integration Guide
### 4.1 Android
#### 4.1.1 Gradle [![](https://jitpack.io/v/ywl5320/wlmedia.svg)](https://jitpack.io/#ywl5320/wlmedia)
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
    
dependencies {
    implementation 'com.github.ywl5320:wlmedia:4.2.0'
}
``` 
### 4.1.2 Required Permissions
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```
### 4.1.3 Configure NDK ABIs
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
### 4.1.4 Set Up Surface
```xml
<!-- WlSurfaceView for general playback -->
<com.ywl5320.wlmedia.widget.WlSurfaceView
    android:id="@+id/wlsurfaceview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

<!-- WlTextureView for transparency, rotation, etc. -->
<com.ywl5320.wlmedia.widget.WlTextureView
    android:id="@+id/wltextureview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
### 4.1.5 Basic Usage (See Full Demo)
```java
// 1. Create player
WlPlayer wlPlayer = new WlPlayer();
wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
    @Override
    public void onPrepared() {
        wlPlayer.start(); // Start playback after preparation
    }

    @Override
    public void onTimeInfo(double currentTime, double bufferTime) {
        // Time progress callback
    }

    @Override
    public void onComplete(WlCompleteType type, String msg) {
        switch (type) {
            case WL_COMPLETE_EOF: // Normal EOF
            case WL_COMPLETE_ERROR: // Playback error (see msg)
            case WL_COMPLETE_HANDLE: // Stopped by wlPlayer.stop()
            case WL_COMPLETE_NEXT: // Media switched during playback
            case WL_COMPLETE_TIMEOUT: // Timeout
            case WL_COMPLETE_LOOP: // Loop restart
                break;
        }
    }

    @Override
    public void onLoad(WlLoadStatus status, int progress, long speed) {
        switch (status) {
            case WL_LOADING_STATUS_START: // Loading started
            case WL_LOADING_STATUS_PROGRESS: // Loading progress
            case WL_LOADING_STATUS_FINISH: // Loading completed
                break;
        }
    }

    @Override
    public void onSeekFinish() {
        // Seek completed
    }

    @Override
    public void onFirstFrameRendered() {
        // First frame rendered
    }
});

// 2. Bind WlSurfaceView to player
WlSurfaceView wlSurfaceView = findViewById(R.id.wlsurfaceview);
wlSurfaceView.setWlPlayer(wlPlayer);
wlSurfaceView.setClearLastVideoFrame(false); // Keep last frame
wlSurfaceView.setVideoScale(WlScaleType.WL_SCALE_FIT); // Scaling mode
wlSurfaceView.setVideoRotate(WlRotateType.WL_ROTATE_90); // Rotation
wlSurfaceView.setVideoMirror(WlMirrorType.WL_MIRROR_TOP_BOTTOM); // Mirroring

// 3. Set data source and prepare
wlPlayer.setSource(url);
wlPlayer.prepare();
```
### [Android More Doc](doc/android/android.md)

## 4.2 HarmonyOS

### 4.2.1 Install SDK Package
```
ohpm i @ywl5320/libwlmedia
```
### 4.2.2 Required Permissions
```json5
// module.json5
"requestPermissions": [
    { "name": "ohos.permission.INTERNET" }
]
```
### 4.2.3 Configure XComponentController
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
### 4.2.4 Basic Usage (See Full Demo)
```typescript
@Entry
@Component
struct Index {
    private wlPlayer: WlPlayer | null = null;
    private wlComponentController: WlComponentController | null = null;

    aboutToAppear(): void {
        this.wlPlayer = new WlPlayer();
        this.wlPlayer.setOnMediaInfoListener({
            onPrepared: (): void => {
                this.wlPlayer?.start();
            },
            onTimeInfo: (currentTime: number, bufferTime: number): void => {
                // Time progress
            },
            onComplete: (type: WlCompleteType, msg: string): void => {
                // Handle completion types
            },
            onLoadInfo: (status: WlLoadStatus, progress: number, speed: number): void => {
                // Loading status
            },
            onFirstFrameRendered: (): void => {
                // First frame rendered
            },
            onSeekFinish: (): void => {
                // Seek completed
            },
            onAudioInterrupt: (type: WlAudioInterruptType, hint: WlAudioInterruptHint) => {
                // Audio interruption handling
            }
        });
        this.wlComponentController = new WlComponentController(this.wlPlayer);
        this.wlPlayer.setClearLastVideoFrame(this.wlComponentController.getUniqueNum(), false);
    }

    onBackPress(): boolean | void {
        this.wlPlayer?.release(); // Release resources
    }

    build() {
        Column() {
            XComponent({
                type: XComponentType.SURFACE,
                controller: this.wlComponentController
            })
            .onLoad(() => {
                this.wlPlayer?.setSource(this.filesDir + "/huoying_cut.mkv");
                this.wlPlayer?.prepare();
            })
            .width('100%')
            .height('100%')
        }
    }
}
```
### [OHOS More DOC](doc/ohos/harmonyos.md)
## V. Licensing
`WlMedia` is licensed per app `(by package name)` with Free and Paid tiers.

### Free Tier
- Video contains a `wlmedia` watermark. All other features are unrestricted.

### Paid Tier
- Remove watermark and request custom builds. Contact via email:
  - Email: ywl5320@163.com
## VI. Discussion Group (QQ: 1085618246)
![QQ](doc/imgs/qq_ercode.png)

## VII. Core Libraries
- [ffmpeg](http://ffmpeg.org/)
- [openssL](https://github.com/openssl/openssl)
- [soundtouch](http://www.surina.net/soundtouch/)
- [dav1d](https://code.videolan.org/videolan/dav1d)

#### Update By：ywl5320 2025-04-05
#### Create By：ywl5320 2019-12-16