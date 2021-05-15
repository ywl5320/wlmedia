package com.ywl5320.wlmedia.util;

/**
 * Created by ywl5320 on 2019-08-28.
 */

public class WlTimeUtil {

    static StringBuilder time = new StringBuilder();
    /**
     * format times
     * @param secds
     * @return
     */
    public static String secdsToDateFormat(double secds) {

        if(secds < 0)
        {
            secds = 0;
        }

        if(time.length() > 0)
        {
            time.delete(0, time.length());
        }

        long hours = (long) (secds / (3600));
        long minutes = (long) ((secds % (3600)) / (60));
        long seconds = (long) (secds % (60));

        if(hours > 0)
        {
            time.append((hours >= 10) ? hours : "0" + hours);
            time.append(":");
        }
        time.append((minutes >= 10) ? minutes : "0" + minutes);
        time.append(":");
        time.append((seconds >= 10) ? seconds : "0" + seconds);
        return time.toString();
    }
}
