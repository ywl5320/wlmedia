import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCompleteType,
  WlComponentController,
  WlLoadStatus,
  WlLog,
  WlPlayer,
  WlSeekType,
  WlTimeUtil
} from '@ywl5320/libwlmedia';
import { common } from '@kit.AbilityKit';

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/2/2
 */
@Entry
@Component
struct Index {
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;
  private wlComponentController: WlComponentController | null = null;
  private wlPlayer: WlPlayer | null = null;
  @State
  private loadStatus: WlLoadStatus = WlLoadStatus.WL_LOADING_STATUS_START
  @State
  private timeInfo: String = '00:00/00:00'
  @State
  private progress: number = 0

  aboutToAppear(): void {
    this.wlPlayer = new WlPlayer();
    this.wlPlayer.setOnMediaInfoListener({
      onPrepared: (): void => {
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
      onComplete: (complete: WlCompleteType, msg: string): void => {
      },
      onLoadInfo: (loadStatus: WlLoadStatus, progress: number, speed: number): void => {
        this.loadStatus = loadStatus;
      },
      onFirstFrameRendered: (): void => {
      },
      onSeekFinish: (): void => {
      },
      onAudioInterrupt: (type: WlAudioInterruptType, hint: WlAudioInterruptHint) => {
      }
    });
    this.wlComponentController = new WlComponentController(this.wlPlayer);
  }

  onBackPress(): boolean | void {
    this.wlPlayer?.release()
  }

  build() {
    Column() {
      RelativeContainer() {
        // 显示音频封面
        XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController
        })
          .onLoad(() => {
            this.wlPlayer?.setSource(this.filesDir + "/fhcq-whcyygyd.mp3");
            this.wlPlayer?.prepare();
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
          .fontColor("#000000")
          .fontSize(14)
          .alignRules({
            right: { anchor: "__container__", align: HorizontalAlign.End }
          })
        if (this.loadStatus !== WlLoadStatus.WL_LOADING_STATUS_FINISH) {
          LoadingProgress()
            .color(Color.Blue)
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

      Button("开始播放")
        .onClick(() => {
          this.wlPlayer?.setSource(this.filesDir + "/fhcq-whcyygyd.mp3");
          this.wlPlayer?.setAutoPlay(true);
          this.wlPlayer?.prepare();
        })
        .width('100%')
        .height(50)
        .margin({
          top: 5
        })
      Button("停止播放")
        .onClick(() => {
          this.wlPlayer?.stop();
        })
        .width('100%')
        .height(50)
        .margin({
          top: 5
        })
    }
    .width('100%')
    .height('100%')
  }
}