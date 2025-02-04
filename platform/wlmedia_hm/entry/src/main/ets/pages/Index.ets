import { router } from '@kit.ArkUI';
import { FileUtil } from '../util/FileUtil';
import { common } from '@kit.AbilityKit';

@Entry
@Component
struct Index {
  private context = getContext(this) as common.UIAbilityContext;
  filesDir = this.context.filesDir;

  aboutToAppear(): void {
    FileUtil.resourcesFile2SandboxFile("testvideos/alpha_left5.mp4", this.filesDir + "/alpha_left5.mp4");
    FileUtil.resourcesFile2SandboxFile("testvideos/fhcq-whcyygyd.mp3", this.filesDir + "/fhcq-whcyygyd.mp3");
    FileUtil.resourcesFile2SandboxFile("testvideos/mydream.m4a", this.filesDir + "/mydream.m4a");
    FileUtil.resourcesFile2SandboxFile("testvideos/big_buck_bunny_cut.mp4", this.filesDir + "/big_buck_bunny_cut.mp4");
    FileUtil.resourcesFile2SandboxFile("testvideos/huoying_cut.mkv", this.filesDir + "/huoying_cut.mkv");
  }

  build() {
    Column() {
      Button("基础播放")
        .onClick(() => {
          router.pushUrl({ url: "pages/NormalPlayer" })
        })
        .width('100%')
      Button("音频播放")
        .onClick(() => {
          router.pushUrl({ url: "pages/AudioPlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("透明视频播放")
        .onClick(() => {
          router.pushUrl({ url: "pages/AlphaVideoPlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("多Surface渲染")
        .onClick(() => {
          router.pushUrl({ url: "pages/MultiSurfacePlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("多播放器实例")
        .onClick(() => {
          router.pushUrl({ url: "pages/MultiInstancePlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("加密视频播放")
        .onClick(() => {
          router.pushUrl({ url: "pages/EncryptPlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("Buffer播放")
        .onClick(() => {
          router.pushUrl({ url: "pages/BufferPlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("音频PCM实时数据获取")
        .onClick(() => {
          router.pushUrl({ url: "pages/AudioPcmDataPlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("显示首帧")
        .onClick(() => {
          router.pushUrl({ url: "pages/FirstFramePlayer" })
        })
        .width('100%')
        .margin({
          top: 5
        })
      Button("WlMediaUtil")
        .onClick(() => {
          router.pushUrl({ url: "pages/WlMediaUtilPage" })
        })
        .width('100%')
        .margin({
          top: 15
        })

    }
    .height('100%')
  }
}