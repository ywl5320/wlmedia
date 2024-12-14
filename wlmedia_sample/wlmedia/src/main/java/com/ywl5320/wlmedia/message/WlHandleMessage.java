package com.ywl5320.wlmedia.message;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public class WlHandleMessage {
    private static final int DEFAULT_CODE = 2024;

    public static final int WL_PLAYER_HANDEL_MSG__PREPARED_CB = DEFAULT_CODE + 10;
    public static final int WL_PLAYER_HANDEL_MSG__TIME_INFO_CB = DEFAULT_CODE + 20;
    public static final int WL_PLAYER_HANDEL_MSG__COMPLETE_CB = DEFAULT_CODE + 30;
    public static final int WL_PLAYER_HANDEL_MSG__LOADING_CB = DEFAULT_CODE + 40;
    public static final int WL_PLAYER_HANDEL_MSG__SEEK_FINISH_CB = DEFAULT_CODE + 50;
    public static final int WL_PLAYER_HANDEL_MSG__FIRST_FRAME_RENDERED_CB = DEFAULT_CODE + 60;
    public static final int WL_PLAYER_HANDEL_MSG__OUT_RENDER_TEXTURE_CB = DEFAULT_CODE + 70;
    public static final int WL_PLAYER_HANDEL_MSG__AUTO_PLAY_CB = DEFAULT_CODE + 80;
    public static final int WL_PLAYER_HANDEL_MSG__TAKE_PICTURE_CB = DEFAULT_CODE + 90;
}
