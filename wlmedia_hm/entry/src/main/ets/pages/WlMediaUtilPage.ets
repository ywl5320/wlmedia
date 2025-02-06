import { WlLog, WlMediaUtil } from '@ywl5320/libwlmedia'
import { common } from '@kit.AbilityKit';
import { image } from '@kit.ImageKit';

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/2/3
 */
@Entry
@Component
struct Index {
  private wlMediaUtil: WlMediaUtil = new WlMediaUtil();
  @State
  private pixelMap: PixelMap | null = null;
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  build() {
    Column() {
      Image(this.pixelMap)
        .width('100%')
        .height('260')
        .objectFit(ImageFit.Contain)
        .border({ width: 1, color: Color.Blue })
        .margin({
          top: 5
        })
      Button(`获取视频帧`)
        .width('100%')
        .height('50')
        .onClick(() => {
          this.wlMediaUtil.setSource(this.filesDir + "/qyn2.mkv");
          let ret = this.wlMediaUtil.openSource();
          WlLog.d(`open source is ${ret}`);
          this.startFrameUpdate();
        });
    }
  }

  private async getPixelsMap(): Promise<image.PixelMap | null> {
    return this.wlMediaUtil.getVideoFrame(0, 0, false, 0, 0);
  }

  private async startFrameUpdate() {
    try {
      while (true) {
        const frame = await this.getPixelsMap();
        if (frame) {
          WlLog.d("get frame success");
          if (this.pixelMap != null) {
            this.pixelMap.release();
          }
          this.pixelMap = frame; // 更新状态以触发 UI 刷新
        }
        await this.sleep(5); // 控制帧率，例如 40ms 大约为 25fps
      }
    } catch (error) {
      console.error("Error during frame update:", error);
    }
  }

  private sleep(ms: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }
}