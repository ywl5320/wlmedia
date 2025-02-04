import { WlLoadStatus } from "../enums/WlLoadStatus";
import { WlTrackType } from "../enums/WlTrackType";
import { image } from "@kit.ImageKit";
import { WlAudioInterruptType } from "../enums/WlAudioInterruptType";
import { WlAudioInterruptHint } from "../enums/WlAudioInterruptHint";
import { WlCompleteType } from "../enums/WlCompleteType";

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/1
 */
export interface WlOnMediaInfoListener {

  /**
   * 异步准备好
   * 当不是自动播放 setAutoPlay(false) 时，调用 prepare 异步准备好后会回调此函数，在此函数中可以调用 start() 方法即可开始播放。
   */
  onPrepared();

  /**
   * 时间回调
   *
   * @param currentTime 当前播放时间
   * @param bufferTime 缓存时长
   */
  onTimeInfo(currentTime: number, bufferTime: number);

  /**
   * complete回调
   *
   * @param wlCompleteType 表示 Complete 类型，具体值，参考 WlCompleteType 枚举类
   * @param msg 对应 Complete 类型的原因
   */
  onComplete(wlCompleteType: WlCompleteType, msg: string);

  /**
   * 加载回调接口
   *
   * @param loadStatus 加载状态（开始加载、加载中、加载完成）
   * @param progress   加载进度 (0~100)
   * @param speed      加载速度 KB/s
   */
  onLoadInfo(loadStatus: WlLoadStatus, progress: number, speed: number);

  /**
   * 首帧渲染回调
   */
  onFirstFrameRendered();

  /**
   * seek 完成回调
   */
  onSeekFinish();

  /**
   * 音频打断
   * @param type
   * @param hint
   */
  onAudioInterrupt(type: WlAudioInterruptType, hint: WlAudioInterruptHint);

  /**
   * 自动播放回调，当设置 setAutoPlay(true) 时，异步准备好后会回调此函数，并自动播放，不会再回调 onPrepared()。
   */
  onAutoPlay?();

  /**
   * 音视频帧解密回调
   * @param mediaType 数据类型： 音频 或 视频
   * @param data 对应的解码前帧数据
   * @return
   */
  onDeEncryptData?(mediaType: WlTrackType, data: Uint8Array): Uint8Array | null;

  /**
   * 外部渲染信息回调（OpenGL）
   *
   * @param textureId OpenGL纹理，可用于Unity，cocos等显示视频
   * @param videoWidth 视频宽
   * @param videoHeight 视频高
   * @param videoRotate 视频旋转角度
   */
  onOutRenderTexture?(textureId: number, videoWidth: number, videoHeight: number, videoRotate: number);

  /**
   * 截图回调
   *
   * @param bitmap
   */
  onTakePicture?(pixelMap: image.PixelMap);
}