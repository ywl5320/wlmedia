/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2025/2/4
 */
export class WlColorUtil {
  public static isRGBAColor(color: string | null): boolean {
    if (color === null) {
      return false;
    }
    if (!color.startsWith("#")) {
      return false;
    }
    if (color.length !== 9 && color.length !== 7) {
      return false;
    }
    for (let i = 1; i < color.length; i++) {
      const c = color.charAt(i);
      if (!this.isHexChar(c)) {
        return false;
      }
    }
    return true;
  }

  private static isHexChar(c: string): boolean {
    return /[0-9a-fA-F]/.test(c);
  }
}