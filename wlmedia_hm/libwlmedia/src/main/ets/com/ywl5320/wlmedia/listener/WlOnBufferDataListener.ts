/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/14
 */
export interface WlOnBufferDataListener {
  onBufferByteLength(): number;

  onBufferByteData(position: number, bufferSize: number): Uint8Array | null;
}