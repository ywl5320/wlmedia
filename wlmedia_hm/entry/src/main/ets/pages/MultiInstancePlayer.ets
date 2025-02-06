import { WlComponentController, WlPlayer } from '@ywl5320/libwlmedia'
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
  private wlPlayer1: WlPlayer | null = null;
  private wlPlayer2: WlPlayer | null = null;
  private wlComponentController1: WlComponentController | null = null;
  private wlComponentController2: WlComponentController | null = null;
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  aboutToAppear(): void {
    this.wlPlayer1 = new WlPlayer();
    this.wlPlayer2 = new WlPlayer();
    this.wlComponentController1 = new WlComponentController(this.wlPlayer1);
    this.wlComponentController2 = new WlComponentController(this.wlPlayer2);
    this.wlComponentController1.setClearLastVideoFrame(false);
  }

  onBackPress(): boolean | void {
    this.wlPlayer1?.release()
    this.wlPlayer2?.release()
  }

  build() {
    Column() {
      Column() {
        XComponent({
          type: XComponentType.SURFACE, // 透明视频必须是texture
          controller: this.wlComponentController1
        })
          .onLoad((event) => {
            this.wlPlayer1?.setSource(this.filesDir + "/big_buck_bunny_cut.mp4");
            this.wlPlayer1?.setAutoPlay(true);
            this.wlPlayer1?.prepare();
          })
          .width('100%')
          .height('85%')
        Flex() {
          Button("准备")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer1?.setSource(this.filesDir + "/big_buck_bunny_cut.mp4");
              this.wlPlayer1?.prepare();
            });
          Button("播放")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer1?.resume();
            });
          Button("暂停")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer1?.pause();
            });
          Button("停止")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer1?.stop();
            });
        }
        .height('40')
        .margin({
          top: 5
        })
      }
      .width('100%')
      .height('50%')

      Column() {
        XComponent({
          type: XComponentType.SURFACE, // 透明视频必须是texture
          controller: this.wlComponentController2
        })
          .onLoad((event) => {
            this.wlPlayer2?.setSource(this.filesDir + "/huoying_cut.mkv");
            this.wlPlayer2?.setAutoPlay(true);
            this.wlPlayer2?.prepare();
          })
          .width('100%')
          .height('85%')
        Flex() {
          Button("准备")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer2?.setSource(this.filesDir + "/huoying_cut.mkv");
              this.wlPlayer2?.prepare();
            });
          Button("播放")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer2?.resume();
            });
          Button("暂停")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer2?.pause();
            });
          Button("停止")
            .width('100%')
            .height('100%')
            .onClick(() => {
              this.wlPlayer2?.stop();
            });
        }
        .height('40')
        .margin({
          top: 5
        })
      }
      .width('100%')
      .height('50%')
    }
  }
}