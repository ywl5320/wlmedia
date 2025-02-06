import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCompleteType,
  WlComponentController,
  WlLoadStatus,
  WlLog,
  WlMirrorType,
  WlPlayer,
  WlRotateType,
  WlScaleType,
  WlSeekType,
  WlTimeUtil,
  WlTrackType
} from '@ywl5320/libwlmedia'
import { image } from '@kit.ImageKit';
import { common } from '@kit.AbilityKit';

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/2/1
 */
@Entry
@Component
struct Index {
  private wlPlayer: WlPlayer | null = null;
  private wlComponentController: WlComponentController | null = null;
  @State
  private loadStatus: WlLoadStatus = WlLoadStatus.WL_LOADING_STATUS_START
  @State
  private timeInfo: String = '00:00/00:00'
  @State
  private progress: number = 0
  @State pixelMap: image.PixelMap | null = null;
  @State
  private trackInfo: String = "track info: \n"
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  aboutToAppear(): void {
    this.wlPlayer = new WlPlayer();
    this.wlPlayer.setOnMediaInfoListener({
      onPrepared: (): void => {
        this.trackInfo = "track info: \n";
        let audioTracks = this.wlPlayer?.getAudioTracks();
        let videoTracks = this.wlPlayer?.getVideoTracks();
        if (audioTracks != undefined) {
          for (let i = 0; i < audioTracks.length; i++) {
            this.trackInfo += `${JSON.stringify(audioTracks[i])} \n`;
          }
        }
        if (videoTracks != undefined) {
          for (let i = 0; i < videoTracks.length; i++) {
            this.trackInfo += `${JSON.stringify(videoTracks[i])} \n`;
          }
        }
        this.wlPlayer?.start();
      },
      onTimeInfo: (currentTime: number, bufferTime: number): void => {
        if (this.wlPlayer!!.getDuration() > 0) {
          this.progress = currentTime * 100 / this.wlPlayer!!.getDuration();
        } else {
          this.progress = 50;
        }
        this.timeInfo =
          `${WlTimeUtil.secondToTimeFormat(currentTime)}/${WlTimeUtil.secondToTimeFormat(this.wlPlayer?.getDuration())}`
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
      onLoadInfo: (wlLoadStatus: WlLoadStatus, progress: number, speed: number): void => {
        this.loadStatus = wlLoadStatus;
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
      },
      onSeekFinish: (): void => {
      },
      onAudioInterrupt: (type: WlAudioInterruptType, hint: WlAudioInterruptHint) => {
      },
      onAutoPlay: (): void => {
      },
      onDeEncryptData: (mediaType: WlTrackType, data: Uint8Array): Uint8Array | null => {
        return null;
      },
      onTakePicture: (pixelMap: image.PixelMap) => {
        if (this.pixelMap != null) {
          this.pixelMap.release();
        }
        this.pixelMap = pixelMap;
      }
    });
    this.wlPlayer.setBufferSize(1, 15);
    this.wlComponentController = new WlComponentController(this.wlPlayer);
    this.wlPlayer.setClearLastVideoFrame(this.wlComponentController.getUniqueNum(), false);
  }

  onBackPress(): boolean | void {
    this.wlPlayer?.release()
  }

  build() {
    Column() {
      RelativeContainer() {
        XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController
        })
          .onLoad((event) => {
            this.wlPlayer?.setSource(this.filesDir + "/big_buck_bunny_cut.mp4");
            this.wlPlayer?.prepare();
            WlLog.d(`is auto play : ${this.wlPlayer?.isAutoPlay()}`)
          })
          .width('100%')
          .height('100%')
        Slider({
          value: this.progress
        })
          .width('100%')
          .height('40')
          .onChange((value: number, mode: SliderChangeMode) => {
            if (mode == SliderChangeMode.Begin) {
              WlLog.d("SliderChangeMode.Begin")
              this.wlPlayer?.seekStart();
            } else if (mode == SliderChangeMode.Moving) {
              WlLog.d("SliderChangeMode.Moving")
            } else if (mode == SliderChangeMode.End) {
              WlLog.d("SliderChangeMode.End")
              let time = value / 100.0 * this.wlPlayer!!.getDuration();
              this.wlPlayer?.seekByType(time, WlSeekType.WL_SEEK_ACCURATE)
            } else if (mode == SliderChangeMode.Click) {
              WlLog.d("SliderChangeMode.Click")
            }
          })
          .alignRules({
            bottom: { anchor: "__container__", align: VerticalAlign.Bottom }
          })
        Text(`${this.timeInfo}`)
          .fontColor("#ffffff")
          .fontSize(14)
          .alignRules({
            right: { anchor: "__container__", align: HorizontalAlign.End }
          })
        if (this.loadStatus !== WlLoadStatus.WL_LOADING_STATUS_FINISH) {
          LoadingProgress()
            .color(Color.White)
            .layoutWeight(1)
            .alignRules({
              center: { anchor: "__container__", align: VerticalAlign.Center },
              middle: { anchor: "__container__", align: HorizontalAlign.Center }
            })
            .width(40)
            .height(40)
        }
      }
      .width('100%')
      .height('260')

      Column() {
        Flex() {
          Button("准备")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSource(this.filesDir + "/big_buck_bunny_cut.mp4");
              this.wlPlayer?.prepare();
            });
          Button("播放")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.resume();
            });
          Button("暂停")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.pause();
            });
          Button("停止")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.stop();
            });
          Button("切换")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSource(this.filesDir + "/huoying_cut.mkv");
              this.wlPlayer?.prepare();
            });
        }
        .height('40')
        .margin({
          top: 5
        })

        Flex() {
          Button("适配")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoScale(WlScaleType.WL_SCALE_FIT);
            });
          Button("拉伸")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoScale(WlScaleType.WL_SCALE_MATCH);
            });
          Button("16:9")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoScale(WlScaleType.WL_SCALE_16_9);
            });
          Button("4:3")
            .width('100%')
            .height('100%')
            .onClick(() => {
              // this.wlComponentController?.setVideoScaleType(WlScaleType.WL_SCALE_4_3);
              this.wlComponentController?.setVideoScaleValue(1, 1)
            });
        }
        .width('100%')
        .height('40')
        .margin({
          top: 5
        })

        Flex() {
          Button("R0")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoRotate(WlRotateType.WL_ROTATE_DEFAULT);
            });
          Button("R90")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoRotate(WlRotateType.WL_ROTATE_90);
            });
          Button("R180")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoRotate(WlRotateType.WL_ROTATE_180);
            });
          Button("R270")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoRotate(WlRotateType.WL_ROTATE_270);
            });
        }
        .width('100%')
        .height('40')
        .margin({
          top: 5
        })

        Flex() {
          Button("NO_MIR")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoMirror(WlMirrorType.WL_MIRROR_NONE);
            });
          Button("TB_MIR")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoMirror(WlMirrorType.WL_MIRROR_TOP_BOTTOM);
            });
          Button("LR_MIR")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoMirror(WlMirrorType.WL_MIRROR_LEFT_RIGHT);
            });
          Button("TB_LR_MIR")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlComponentController?.setVideoMirror(WlMirrorType.WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT);
            });
        }
        .width('100%')
        .height('40')
        .margin({
          top: 5
        })

        Flex() {
          Button("S_05")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSpeed(0.5);
            });
          Button("S_10")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSpeed(1.0);
            });
          Button("S_15")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSpeed(1.5);
            });
          Button("S_20")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setSpeed(2.0);
            });
        }
        .width('100%')
        .height('40')
        .margin({
          top: 5
        })

        Flex() {
          Button("P_05")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setPitch(0.5);
            });
          Button("P_10")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setPitch(1.0);
            });
          Button("P_15")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setPitch(1.5);
            });
          Button("P_20")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer?.setPitch(2.0);
            });
        }
        .width('100%')
        .height('40')
        .margin({
          top: 5
        })

        Row() {
          Button("截图")
            .onClick(() => {
              this.wlPlayer?.takePicture();
            })
            .width('20%')
            .height('40')
            .margin({
              top: 5
            })
            .alignSelf(ItemAlign.Start)
          Image(this.pixelMap)
            .width('80%')
            .height('100%')
            .objectFit(ImageFit.Contain)
            .border({ width: 1, color: Color.Blue })
            .margin({
              top: 5
            })
        }
        .width('100%')
        .height('100')

        Text(`${this.trackInfo}}`)
          .fontColor("#000000")
          .fontSize(14)
          .width('100%')
          .margin({
            top: 5,
            left: 5,
            right: 5,
            bottom: 5
          })
      }
      .width('100%')
      .height('100%')
    }
    .width('100%')
    .height('100%')
  }
}