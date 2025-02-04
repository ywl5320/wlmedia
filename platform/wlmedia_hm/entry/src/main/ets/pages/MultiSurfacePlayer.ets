import { WlComponentController, WlPlayer, WlScaleType } from '@ywl5320/libwlmedia';
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
  private wlComponentController1: WlComponentController | null = null;
  private wlComponentController2: WlComponentController | null = null;
  private wlComponentController3: WlComponentController | null = null;
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  aboutToAppear(): void {
    this.wlPlayer = new WlPlayer();
    this.wlComponentController1 = new WlComponentController(this.wlPlayer, "#ff0000");
    this.wlComponentController2 = new WlComponentController(this.wlPlayer, "#0000ff");
    this.wlComponentController3 = new WlComponentController(this.wlPlayer, "#00ff00");

    this.wlComponentController1.setVideoScale(WlScaleType.WL_SCALE_FIT)
    this.wlComponentController2.setVideoScale(WlScaleType.WL_SCALE_MATCH)
    this.wlComponentController3.setVideoScaleValue(9, 16)
  }

  onBackPress(): boolean | void {
    this.wlPlayer?.release()
  }

  build() {
    Column() {
      Flex({ direction: FlexDirection.Column }) {
        XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController1
        })
          .onLoad((event) => {
            this.wlPlayer?.setSource(this.filesDir + "/huoying_cut.mkv");
            this.wlPlayer?.setAutoPlay(true);
            this.wlPlayer?.prepare();
          })
          .width('100%')
          .height('100%')
        XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController2
        })
          .width('100%')
          .height('100%')
        XComponent({
          type: XComponentType.SURFACE,
          controller: this.wlComponentController3
        })
          .width('100%')
          .height('100%')
      }
    }
    .width('100%')
    .height('100%')
  }
}