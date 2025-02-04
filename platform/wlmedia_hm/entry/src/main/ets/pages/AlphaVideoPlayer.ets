import { WlAlphaVideoType, WlComponentController, WlPlayer } from '@ywl5320/libwlmedia';
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
    // 视频左边包含透明度信息
    this.wlPlayer.setAlphaVideoType(WlAlphaVideoType.WL_ALPHA_VIDEO_LEFT_ALPHA);
    // 设置清屏颜色为透明
    this.wlComponentController = new WlComponentController(this.wlPlayer, "#00000000");
    // 视频最后一帧 不清屏
    this.wlPlayer.setClearLastVideoFrame(this.wlComponentController.getUniqueNum(), false);
  }

  onBackPress(): boolean | void {
    this.wlPlayer?.release()
  }

  build() {
    Column() {
      RelativeContainer() {
        Image($r('app.media.dhf'))
          .objectFit(ImageFit.Fill)
          .width('100%')
          .height('100%')
        XComponent({
          type: XComponentType.TEXTURE, // 透明视频必须是texture
          controller: this.wlComponentController
        })
          .onLoad((event) => {
            this.wlPlayer?.setSource(this.filesDir + "/alpha_left5.mp4");
            this.wlPlayer?.setAutoPlay(true);
            this.wlPlayer?.setLoopPlay(true);
            this.wlPlayer?.prepare();
          })
          .width('100%')
          .height('100%')
      }
    }
    .width('100%')
    .height('100%')
  }
}