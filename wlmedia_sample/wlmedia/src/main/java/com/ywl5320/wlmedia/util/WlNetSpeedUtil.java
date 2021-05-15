package com.ywl5320.wlmedia.util;

import android.content.Context;
import android.net.TrafficStats;

/**
 * @author ywl5320
 * @date 2020/8/3
 */
public class WlNetSpeedUtil {

    private static long lastTotalBytes = 0;
    private static long lastTime = 0;

    /**
     * 得到网速
     * @param context
     * @return
     */
    public static long getNetSpeed(Context context) {
        if(TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED)
        {
            return 0;
        }
        long totalBytes = TrafficStats.getTotalRxBytes();
        long nowTime = System.currentTimeMillis();
        if((nowTime - lastTime) == 0)
        {
            return 0;
        }
        long netSpeed = (totalBytes - lastTotalBytes) * 1000 / (nowTime - lastTime);
        lastTotalBytes = totalBytes;
        lastTime = nowTime;
        return netSpeed;
    }

    /**
     * 重置网速
     * @param context
     * @return
     */
    public static boolean reset(Context context)
    {
        if(TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED)
        {
            return false;
        }
        lastTime = System.currentTimeMillis();
        lastTotalBytes = TrafficStats.getTotalRxBytes();
        return true;
    }

}
