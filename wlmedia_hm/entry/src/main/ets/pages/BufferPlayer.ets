import {
  WlAudioInterruptHint,
  WlAudioInterruptType,
  WlCompleteType,
  WlComponentController,
  WlLoadStatus,
  WlLog,
  WlPlayer,
  WlSourceType,
} from '@ywl5320/libwlmedia';
import { common } from '@kit.AbilityKit';
import { fileIo as fs } from '@kit.CoreFileKit';

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
  private randomFile: fs.RandomAccessFile | null = null

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
        WlLog.d("onComplete:" + complete + "," + msg);
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
    this.wlPlayer.setOnBufferDataListener({
      onBufferByteLength: (): number => {
        WlLog.d("onBufferByteLength call")
        let filePath = this.filesDir + "/huoying_cut.mkv";
        this.randomFile = fs.createRandomAccessFileSync(filePath, fs.OpenMode.READ_ONLY);
        this.randomFile.setFilePointer(0);
        const stat = fs.statSync(filePath);
        WlLog.d(`onBufferByteLength call size is: ${stat.size}`)
        return stat.size
      },
      onBufferByteData: (position: number, bufferSize: number): Uint8Array => {
        WlLog.d(`onBufferByteData call: position=${position} bufferSize=${bufferSize}`)
        if (this.randomFile != null) {
          this.randomFile.setFilePointer(position);
          let buf = new ArrayBuffer(bufferSize);
          let number = this.randomFile.readSync(buf);
          if (number > 0) {
            return new Uint8Array(buf);
          }
        }
        return null
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
          this.wlPlayer?.setSourceType(WlSourceType.WL_SOURCE_BUFFER);
          this.wlPlayer?.prepare();
        })
        .width('100%')
        .height('100%')
    }
  }
}