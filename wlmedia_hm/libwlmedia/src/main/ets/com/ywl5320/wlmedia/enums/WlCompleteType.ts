/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/12/1
 */
export enum WlCompleteType {
  WL_COMPLETE_NONE = -1, //未知
  WL_COMPLETE_EOF = 1, //文件播放完成回调
  WL_COMPLETE_ERROR = 2, //播放出错
  WL_COMPLETE_TIMEOUT = 3, //超时完成
  WL_COMPLETE_HANDLE = 4, //手动触发停止
  WL_COMPLETE_NEXT = 5, //切歌时完成
  WL_COMPLETE_LOOP = 6, //循环播放
  WL_COMPLETE_RELEASE = 7, //release完成
}