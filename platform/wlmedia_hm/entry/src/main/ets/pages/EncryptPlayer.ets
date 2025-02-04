import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCompleteType,
  WlComponentController,
  WlLoadStatus,
  WlLog,
  WlPlayer,
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
        WlLog.d(`onPrepared: ${this.wlPlayer?.isBufferDeEncrypt()}`);
        this.wlPlayer?.start();
      },
      onTimeInfo: (currentTime: number, bufferTime: number): void => {
      },
      onComplete: (complete: WlCompleteType, msg: string): void => {
      },
      onLoadInfo: (loadStatus: WlLoadStatus, progress: number, speed: number): void => {
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
        // 模拟解密
        // data[0] = 100;
        // data[1] = 100;
        // data[2] = 100;
        // data[3] = 100;
        // data[4] = 100;
        return data;
      },
      onTakePicture: (pixelMap: image.PixelMap) => {
      }
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
          this.wlPlayer?.setSource(this.filesDir + "/big_buck_bunny_cut.mp4");
          this.wlPlayer?.setBufferDeEncrypt(true);
          this.wlPlayer?.prepare();
        })
        .width('100%')
        .height('100%')
    }
  }
}