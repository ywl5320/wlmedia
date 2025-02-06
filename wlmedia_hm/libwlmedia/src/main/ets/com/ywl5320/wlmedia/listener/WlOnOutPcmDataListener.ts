/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/17
 */
export interface WlOnOutPcmDataListener {

  onOutPcmInfo(bit: number, channel: number, sampleRate: number);

  onOutPcmBuffer(size: number, buffers: Uint8Array, db: number);
}