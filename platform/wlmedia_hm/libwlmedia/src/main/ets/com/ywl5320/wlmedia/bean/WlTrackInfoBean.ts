import { WlTrackType } from "../enums/WlTrackType";

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/10
 */
export class WlTrackInfoBean {
  trackType: WlTrackType;
  trackIndex: number;
  programIndex: number;
  duration: number;
  startTime: number;
  bitrate: number;
  variantBitrate: number;
  language: string;
  videoWidth: number;
  videoHeight: number;
  videoScaleWidth: number;
  videoScaleHeight: number;
  videoRotate: number;
  videoFrameRate: number;
  audioSampleRate: number;
  audioChannelNum: number;

  constructor() {
  }

  public getString(): string {
    return `
      Track Info:
      - Track Type: ${this.trackType}
      - Track Index: ${this.trackIndex}
      - Program Index: ${this.programIndex}
      - Duration: ${this.duration}
      - Start Time: ${this.startTime}
      - Bitrate: ${this.bitrate}
      - Variant Bitrate: ${this.variantBitrate}
      - Language: ${this.language}
      - Video Width: ${this.videoWidth}
      - Video Height: ${this.videoHeight}
      - Video Scale Width: ${this.videoScaleWidth}
      - Video Scale Height: ${this.videoScaleHeight}
      - Video Rotate: ${this.videoRotate}
      - Video Frame Rate: ${this.videoFrameRate}
      - Audio Sample Rate: ${this.audioSampleRate}
      - Audio Channel Number: ${this.audioChannelNum}
    `.trim();
  }

  toString(): string {
    return `
      Track Info:
      - Track Type: ${this.trackType}
      - Track Index: ${this.trackIndex}
      - Program Index: ${this.programIndex}
      - Duration: ${this.duration}
      - Start Time: ${this.startTime}
      - Bitrate: ${this.bitrate}
      - Variant Bitrate: ${this.variantBitrate}
      - Language: ${this.language}
      - Video Width: ${this.videoWidth}
      - Video Height: ${this.videoHeight}
      - Video Scale Width: ${this.videoScaleWidth}
      - Video Scale Height: ${this.videoScaleHeight}
      - Video Rotate: ${this.videoRotate}
      - Video Frame Rate: ${this.videoFrameRate}
      - Audio Sample Rate: ${this.audioSampleRate}
      - Audio Channel Number: ${this.audioChannelNum}
    `.trim();
  }
}