package com.ywl5320.wlmedia.util;

import android.content.Context;

/**
 * @author ywl5320
 * @date 2021/3/26
 */
public class WlDisplayUtil {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
