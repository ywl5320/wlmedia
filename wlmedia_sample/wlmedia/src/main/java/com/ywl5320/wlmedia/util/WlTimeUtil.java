package com.ywl5320.wlmedia.util;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/3/23
 */
public class WlTimeUtil {

    static StringBuilder time = new StringBuilder();

    /**
     * format times
     *
     * @param second
     * @return
     */
    public static String secondToTimeFormat(double second) {

        if (second < 0) {
            second = 0;
        }

        if (time.length() > 0) {
            time.delete(0, time.length());
        }

        long hours = (long) (second / (3600));
        long minutes = (long) ((second % (3600)) / (60));
        long seconds = (long) (second % (60));

        if (hours > 0) {
            time.append((hours >= 10) ? hours : "0" + hours);
            time.append(":");
        }
        time.append((minutes >= 10) ? minutes : "0" + minutes);
        time.append(":");
        time.append((seconds >= 10) ? seconds : "0" + seconds);
        return time.toString();
    }

    /**
     * format times
     *
     * @param second
     * @return
     */
    public static String secondToTimeFormat3(double second) {

        if (second < 0) {
            second = 0;
        }

        if (time.length() > 0) {
            time.delete(0, time.length());
        }

        long hours = (long) (second / (3600));
        long minutes = (long) ((second % (3600)) / (60));
        long seconds = (long) (second % (60));

        time.append((hours >= 10) ? hours : "0" + hours);
        time.append(":");
        time.append((minutes >= 10) ? minutes : "0" + minutes);
        time.append(":");
        time.append((seconds >= 10) ? seconds : "0" + seconds);
        return time.toString();
    }
}
