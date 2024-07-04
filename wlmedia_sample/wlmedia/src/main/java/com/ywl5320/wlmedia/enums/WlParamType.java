package com.ywl5320.wlmedia.enums;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/1/9
 */
public enum WlParamType {

    WL_PARAM_STRING_SOURCE("source", 0),
    WL_PARAM_BOOL_PAUSE_RESUME("isPause resume", 1),
    WL_PARAM_DOUBLE_TIME_INTERVAL("time interval", 2),
    WL_PARAM_BOOL_SEEK_START("seek start", 3),
    WL_PARAM_INT_PLAY_MODEL("play model", 4),
    WL_PARAM_INT_CODEC_TYPE("codec type", 5),
    WL_PARAM_INT_SAMPLE_RATE("sample rate", 6),
    WL_PARAM_INT_RENDER_FPS("render fps", 7),
    WL_PARAM_DOUBLE_SPEED("play speed", 8),
    WL_PARAM_DOUBLE_PITCH_NORMAL("pitch normal", 9),
    WL_PARAM_DOUBLE_PITCH_SEMITONES("pitch semitones", 10),
    WL_PARAM_DOUBLE_PITCH_OCTAVES("pitch octaves", 11),
    WL_PARAM_BOOL_CLEAR_LAST_FRAME("clear last frame", 12),
    WL_PARAM_DOUBLE_TIME_OUT("time out", 13),
    WL_PARAM_BOOL_LOOP_PLAY("loop play", 14),
    WL_PARAM_BOOL_IS_PLAYING("is playing", 15),
    WL_PARAM_DOUBLE_GET_CURRENT_TIME("get current time", 16),
    WL_PARAM_STRING_KV_OPTION("set options", 17),
    WL_PARAM_INT_SOURCE_TYPE("source type", 18),
    WL_PARAM_BOOL_AUTO_PLAY("auto play", 19),
    WL_PARAM_INT_PITCH_TYPE("pitch type", 20),
    WL_PARAM_DOUBLE_PITCH("pitch", 21),
    WL_PARAM_INT_SCALE_WIDTH("scale width", 22),
    WL_PARAM_INT_SCALE_HEIGHT("scale height", 23),
    WL_PARAM_INT_VIDEO_ROTATE("video rotate", 24),
    WL_PARAM_INT_VIDEO_MIRROR("video mirror", 25),
    WL_PARAM_INT_VOLUME("volume", 26),
    WL_PARAM_DOUBLE_GET_BUFFER_TIME("get buffer time", 27),
    WL_PARAM_DOUBLE_GET_MIN_BUFFER_TO_PLAY("get min buffer to play", 28),
    WL_PARAM_DOUBLE_GET_MAX_BUFFER_WAIT_TO_PLAY("get max buffer wait to play", 29),
    WL_PARAM_INT_AUDIO_CHANNEL("set audio channel", 30),
    WL_PARAM_BOOL_CALL_BACK_PCM("call back pcm", 31);
    private String key;
    private int value;

    WlParamType(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
