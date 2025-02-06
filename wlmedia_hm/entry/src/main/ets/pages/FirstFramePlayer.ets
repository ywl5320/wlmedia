import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCodecType,
  WlCompleteType,
  WlComponentController,
  WlLoadStatus,
  WlLog,
  WlPlayer,
  WlSeekType,
  WlTrackType
} from '@ywl5320/libwlmedia';
import { image } from '@kit.ImageKit';
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
  private wlPlayer: WlPlayer | null = null;
  private wlComponentController: WlComponentController | null = null;
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  aboutToAppear(): void {
    this.wlPlayer = new WlPlayer();
    this.wlPlayer.setOnMediaInfoListener({
      onPrepared: (): void => {
        this.wlPlayer?.start();
        this.wlPlayer?.pause();
        this.wlPlayer?.seekByType(10, WlSeekType.WL_SEEK_NORMAL)
      },
      onTimeInfo: (currentTime: number, bufferTime: number): void => {
      },
      onComplete: (complete: WlCompleteType, msg: string): void => {
        WlLog.d(`onComplete: ${msg}`)
      },
      onLoadInfo: (loadStatus: WlLoadStatus, progress: number, speed: number): void => {
      },
      onFirstFrameRendered: (): void => {
      },
      onSeekFinish: (): void => {
      },
      onAudioInterrupt: (type: WlAudioInterruptType, hint: WlAudioInterruptHint) => {
      },
    });
    this.wlComponentController = new WlComponentController(this.wlPlayer);
    this.wlPlayer.setClearLastVideoFrame(this.wlComponentController.getUniqueNum(), true);
  }

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
          this.wlPlayer?.setSource(this.filesDir + "/huoying_cut.mkv");
          this.wlPlayer?.setCodecType(WlCodecType.WL_CODEC_AUTO);
          this.wlPlayer?.prepare();
        })
        .width('100%')
        .height('100%')
    }
  }
}