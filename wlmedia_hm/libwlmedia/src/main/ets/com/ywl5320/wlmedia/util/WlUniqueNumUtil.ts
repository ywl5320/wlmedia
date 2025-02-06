/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/1/4
 */
export class WlUniqueNumUtil {
  public static generateUniqueNum(): number {
    return Date.now() + Math.floor(Math.random() * 1000);
  }
}