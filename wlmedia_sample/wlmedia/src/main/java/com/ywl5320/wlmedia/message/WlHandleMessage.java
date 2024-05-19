package com.ywl5320.wlmedia.message;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/1/9
 */
public class WlHandleMessage {
    private static final int DEFAULT_CODE = 2024;

    public static final int WL_MSG_PREPARED_CB = DEFAULT_CODE + 10;
    public static final int WL_MSG_COMPLETE_CB = DEFAULT_CODE + 20;
    public static final int WL_MSG_TIME_INFO_CB = DEFAULT_CODE + 30;
    public static final int WL_MSG_PAUSE_STATUS_CB = DEFAULT_CODE + 40;
    public static final int WL_MSG_LOADING_CB = DEFAULT_CODE + 50;
    public static final int WL_MSG_FIRST_FRAME_RENDERED_CB = DEFAULT_CODE + 60;
    public static final int WL_MSG_TAKE_PICTURE_CB = DEFAULT_CODE + 70;
    public static final int WL_MSG_OUT_RENDER_CB = DEFAULT_CODE + 80;
    public static final int WL_MSG_AUTO_PLAY_CB = DEFAULT_CODE + 90;
    public static final int WL_MSG_SEEK_FINISH_CB = DEFAULT_CODE + 100;
}
