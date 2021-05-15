package com.ywl5320.wlmedia.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * create by ywl5320 2019/8/22
 */
public class WlLog {

    private static final String TAG = "wlmedia";
    private static boolean debug = true;

    public static void setDebug(boolean debug) {
        WlLog.debug = debug;
    }

    public static void d(String msg)
    {
        if(!debug)
        {
            return;
        }
        if(!TextUtils.isEmpty(msg))
        {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg)
    {
        if(!debug)
        {
            return;
        }
        if(!TextUtils.isEmpty(msg))
        {
            Log.e(TAG, msg);
        }
    }
}
