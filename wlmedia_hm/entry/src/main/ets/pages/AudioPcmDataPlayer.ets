import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCompleteType,
  WlLoadStatus,
  WlLog,
  WlPlayer,
  WlSeekType,
  WlTimeUtil,
  WlTrackType
} from '@ywl5320/libwlmedia'
import { common } from '@kit.AbilityKit';

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/2/4
 */
@Entry
@Component
struct Index {
  private wlPlayer: WlPlayer | null = null;
  @State
  private timeInfo: string = '00:00/00:00'
  @State
  private progress: number = 0
  @State
  private pcmInfo: string = ''
  @State
  private dbInfo: string = ''
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

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
      onComplete: (wlCompleteType: WlCompleteType, msg: string): void => {
      },
      onLoadInfo: (wlLoadStatus: WlLoadStatus, progress: number, speed: number): void => {
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
      }
    });
    this.wlPlayer.setOnOutPcmDataListener({
      onOutPcmInfo: (bit: number, channel: number, sampleRate: number): void => {
        this.pcmInfo = `pcm info: bit=${bit} channel=${channel} sampleRate=${sampleRate}`
      },
      onOutPcmBuffer: (size: number, buffers: Uint8Array, db: number): void => {
        this.dbInfo = `pcm size:${size}, db:${db}`
      }
    });
    this.wlPlayer.setPcmCallbackEnable(true);
    this.wlPlayer?.setSource(this.filesDir + "/mydream.m4a");
    this.wlPlayer?.prepare();
  }

  onBackPress(): boolean | void {
    this.wlPlayer?.release()
  }

  build() {
    Column() {
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
      Button("开始播放")
        .onClick(() => {
        })
        .width('100%')
        .margin({
          top: 15
        })
      Button("停止播放")
        .onClick(() => {
        })
        .width('100%')
        .margin({
          top: 5
        })
      Text(`${this.pcmInfo}`)
        .margin({
          top: 15
        })
      Text(`${this.dbInfo}`)
        .margin({
          top: 15
        })
    }
  }
}