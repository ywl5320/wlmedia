import { WlTrackInfoBean } from "./WlTrackInfoBean";

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/10
 */
export class WlMediaInfoBean {
  duration: number;
  startTime: number;
  bitRate: number;

  audioTracks: WlTrackInfoBean[] | undefined;
  videoTracks: WlTrackInfoBean[] | undefined;
  subtitleTracks: WlTrackInfoBean[] | undefined;

  constructor() {
  }
}