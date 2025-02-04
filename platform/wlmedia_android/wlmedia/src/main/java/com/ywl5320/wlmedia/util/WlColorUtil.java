package com.ywl5320.wlmedia.util;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/10
 */
public class WlColorUtil {
    public static boolean isRGBAColor(String color) {
        if (color == null) {
            return false;
        }
        if (!color.startsWith("#")) {
            return false;
        }
        if (color.length() != 9 && color.length() != 7) {
            return false;
        }
        for (int i = 1; i < color.length(); i++) {
            char c = color.charAt(i);
            if (!Character.isDigit(c) && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                return false;
            }
        }
        return true;
    }
}
