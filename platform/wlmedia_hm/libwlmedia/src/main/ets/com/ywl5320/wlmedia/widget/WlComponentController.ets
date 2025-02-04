/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/11/29
 */
import { WlMirrorType } from "../enums/WlMirrorType";
import { WlRotateType } from "../enums/WlRotateType";
import { WlScaleType } from "../enums/WlScaleType";
import { WlColorUtil } from "../util/WlColorUtil";
import { WlUniqueNumUtil } from "../util/WlUniqueNumUtil";
import { WlPlayer } from "../WlPlayer"

export class WlComponentController extends XComponentController {
  private wlPlayer: WlPlayer | null = null;
  private rgba: string = '#000000FF';
  private uniqueNum: number = 0

  constructor(wlPlayer: WlPlayer, rgba: string = '#000000FF') {
    super();
    this.uniqueNum = WlUniqueNumUtil.generateUniqueNum();
    if (WlColorUtil.isRGBAColor(rgba)) {
      this.rgba = rgba;
    }
    this.wlPlayer = wlPlayer;
  }

  onSurfaceCreated(surfaceId: string): void {
    try {
      this.wlPlayer?.setSurface(surfaceId, this.rgba, this.uniqueNum);
    } catch (error) {
    }
  }

  onSurfaceChanged(surfaceId: string, rect: SurfaceRect): void {
    try {
      this.wlPlayer?.setSurface(surfaceId, this.rgba, this.uniqueNum);
    } catch (error) {
    }
  }

  onSurfaceDestroyed(surfaceId: string): void {
    try {
      this.wlPlayer?.setSurface(null, this.rgba, this.uniqueNum);
    } catch (error) {
    }
  }

  public getUniqueNum(): number {
    return this.uniqueNum;
  }

  public setVideoScale(scaleType: WlScaleType) {
    this.wlPlayer?.setVideoScale(this.getUniqueNum(), scaleType);
  }

  public setVideoScaleValue(scaleWidth: number, scaleHeight: number) {
    this.wlPlayer?.setVideoScaleValue(this.getUniqueNum(), scaleWidth, scaleHeight);
  }

  public setVideoRotate(rotateType: WlRotateType) {
    this.wlPlayer?.setVideoRotate(this.getUniqueNum(), rotateType);
  }

  public setVideoMirror(mirrorType: WlMirrorType) {
    this.wlPlayer?.setVideoMirror(this.getUniqueNum(), mirrorType);
  }

  public setClearLastVideoFrame(clear: boolean) {
    this.wlPlayer?.setClearLastVideoFrame(this.getUniqueNum(), clear);
  }
}