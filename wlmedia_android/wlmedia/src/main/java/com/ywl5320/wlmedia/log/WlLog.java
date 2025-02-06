package com.ywl5320.wlmedia.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public class WlLog {
    private static String TAG = "wlmedia-log-java";
    private static boolean isDebug = false;

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static void d(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.d(TAG, msg);
        }
    }
}
