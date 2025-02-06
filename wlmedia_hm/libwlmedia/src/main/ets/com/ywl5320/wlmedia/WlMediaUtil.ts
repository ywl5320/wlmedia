import { WlMediaUtilNapi } from "libwlmediautil.so";
import { WlMediaInfoBean } from "./bean/WlMediaInfoBean";
import { WlTrackInfoBean } from "./bean/WlTrackInfoBean";
import { image } from "@kit.ImageKit";

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/21
 */
export class WlMediaUtil {
  private wlMediaUtilNapi: WlMediaUtilNapi;
  private mediaInfoBean: WlMediaInfoBean | null = null;

  constructor() {
    this.wlMediaUtilNapi = new WlMediaUtilNapi();
  }

  /**
   * 设置 数据源
   *
   * @param source
   */
  public setSource(source: string) {
    this.wlMediaUtilNapi.n_wlMediaUtil_setSource(source);
  }

  /**
   * 打开 数据源
   *
   * @return 0：success
   * -1: 超时
   * -2: 错误
   */
  public openSource(): number {
    return this.wlMediaUtilNapi.n_wlMediaUtil_openSource();
  }

  /**
   * 设置 option
   *
   * @param key
   * @param value
   */
  public setOptions(key: string, value: string) {
    this.wlMediaUtilNapi.n_wlMediaUtil_setOptions(key, value);
  }

  /**
   * 清除所有 option
   */
  public clearOptions() {
    this.wlMediaUtilNapi.n_wlMediaUtil_clearOptions();
  }

  /**
   * 设置超时时间
   *
   * @param time 单位秒（s) > 0
   */
  public setTimeOut(timeOut: number) {
    this.wlMediaUtilNapi.n_wlMediaUtil_setTimeOut(timeOut);
  }

  /**
   * 获取音频 track 信息
   *
   * @return
   */
  public getAudioTracks(): WlTrackInfoBean[] | undefined {
    this.getMediaInfo();
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.audioTracks
  }

  /**
   * 获取视频 track 信息
   *
   * @return
   */
  public getVideoTracks(): WlTrackInfoBean[] | undefined {
    this.getMediaInfo();
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.videoTracks
  }

  /**
   * 获取字幕 track 信息
   *
   * @return
   */
  public getSubtitleTracks(): WlTrackInfoBean[] | undefined {
    this.getMediaInfo();
    if (this.mediaInfoBean == null) {
      return undefined;
    }
    return this.mediaInfoBean.subtitleTracks
  }

  /**
   * 获取对应时间截图
   *
   * @param trackIndex  获取截图的视频track
   * @param time        如果 time > 0 , 就会取time对应的时间值，如果 time = 0，会按照顺序依次读取截图
   * @param keyFrame    表示是不是只取关键帧（速度快）
   * @param scaleWidth  缩放宽 0: 默认宽度
   * @param scaleHeight 缩放高 0: 默认高度
   * @return
   */
  public getVideoFrame(trackIndex: number, time: number, keyFrame: boolean, scaleWidth: number,
    scaleHeight: number): image.PixelMap | null {
    return this.wlMediaUtilNapi.n_wlMediaUtil_getVideoFrame(trackIndex, time, keyFrame, scaleWidth,
      scaleHeight) as image.PixelMap;
  }

  /**
   * 销毁资源 注意：只要创建了实例，就要调用release销毁
   */
  public release() {
    this.wlMediaUtilNapi.n_wlMediaUtil_release();
  }

  /**
   * 获取媒体信息
   *
   * @return
   */
  private getMediaInfo() {
    if (this.mediaInfoBean == null) {
      this.mediaInfoBean = this.wlMediaUtilNapi.n_wlMediaUtil_getMediaInfo() as WlMediaInfoBean;
    }
  }
}