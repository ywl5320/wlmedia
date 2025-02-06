/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/6
 */
export enum WlScaleType {
  WL_SCALE_16_9 = "16:9",
  WL_SCALE_4_3 = "4:3",
  WL_SCALE_FIT = "FIT(长宽适配)",
  WL_SCALE_MATCH = "MATCH（拉伸填充）"
}

interface WlScaleValue {
  scaleWidth: number;
  scaleHeight: number;
  msg: string
}

export const WlScaleTypeInfoMap: Record<WlScaleType, WlScaleValue> = {
  [WlScaleType.WL_SCALE_16_9]: { scaleWidth: 16, scaleHeight: 9, msg: "16:9" },
  [WlScaleType.WL_SCALE_4_3]: { scaleWidth: 4, scaleHeight: 3, msg: "4:3" },
  [WlScaleType.WL_SCALE_FIT]: { scaleWidth: 0, scaleHeight: 0, msg: "FIT(长宽适配)" },
  [WlScaleType.WL_SCALE_MATCH]: { scaleWidth: -1, scaleHeight: -1, msg: "MATCH（拉伸填充）" }
};
