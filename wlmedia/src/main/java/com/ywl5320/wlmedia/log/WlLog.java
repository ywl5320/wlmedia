package com.ywl5320.wlmedia.log;

import android.text.TextUtils;
import android.util.Log;

import com.ywl5320.wlmedia.WlPlayer;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/1/9
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

    public static void getPlayerInfo(String tag, WlPlayer wlPlayer) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("source:");
        stringBuffer.append(wlPlayer.getSource());
        stringBuffer.append(", \nisPlaying:");
        stringBuffer.append(wlPlayer.isPlaying());
        stringBuffer.append(", \nisPause:");
        stringBuffer.append(wlPlayer.isPause());
        stringBuffer.append(", \nisAutoPlay:");
        stringBuffer.append(wlPlayer.isAutoPlay());
        stringBuffer.append(", \nisLoopPlay:");
        stringBuffer.append(wlPlayer.isLoopPlay());
        stringBuffer.append(", \nduration:");
        stringBuffer.append(wlPlayer.getDuration());
        stringBuffer.append(", \ncurrentTime:");
        stringBuffer.append(wlPlayer.getCurrentTime());
        stringBuffer.append(", \nbufferTime:");
        stringBuffer.append(wlPlayer.getBufferTime());
        stringBuffer.append(", \nsourceType:");
        stringBuffer.append(wlPlayer.getSourceType().getKey());
        stringBuffer.append(", \ntimeInfoInterval:");
        stringBuffer.append(wlPlayer.getTimeInfoInterval());
        stringBuffer.append(", \nplayModel:");
        stringBuffer.append(wlPlayer.getPlayModel().getKey());
        stringBuffer.append(", \ncodecType:");
        stringBuffer.append(wlPlayer.getCodecType().getKey());
        stringBuffer.append(", \nsampleRate:");
        stringBuffer.append(wlPlayer.getSampleRate().getKey());
        stringBuffer.append(", \nrenderFPS:");
        stringBuffer.append(wlPlayer.getRenderFPS());
        stringBuffer.append(", \nspeed:");
        stringBuffer.append(wlPlayer.getSpeed());
        stringBuffer.append(", \npitchType:");
        stringBuffer.append(wlPlayer.getPitchType().getKey());
        stringBuffer.append(", \npitch:");
        stringBuffer.append(wlPlayer.getPitch());
        stringBuffer.append(", \nscaleWidth:");
        stringBuffer.append(wlPlayer.getScaleWidth());
        stringBuffer.append(", \nscaleHeight:");
        stringBuffer.append(wlPlayer.getScaleHeight());
        stringBuffer.append(", \nvideoRotate:");
        stringBuffer.append(wlPlayer.getVideoRotate());
        stringBuffer.append(", \nvideoMirror:");
        stringBuffer.append(wlPlayer.getVideoMirror());
        stringBuffer.append(", \nisClearLastVideoFrame:");
        stringBuffer.append(wlPlayer.isClearLastVideoFrame());
        stringBuffer.append(", \ntimeOut:");
        stringBuffer.append(wlPlayer.getTimeOut());
        stringBuffer.append(", \nvolume:");
        stringBuffer.append(wlPlayer.getVolume());
        if (wlPlayer.getCurrentAudioTrack() != null) {
            stringBuffer.append(", \naudioTrackInfo:");
            stringBuffer.append(wlPlayer.getCurrentAudioTrack().toString());
        }
        if (wlPlayer.getCurrentVideoTrack() != null) {
            stringBuffer.append(", \nvideoTrackInfo:");
            stringBuffer.append(wlPlayer.getCurrentVideoTrack().toString());
        }
        if (wlPlayer.getCurrentSubtitleTrack() != null) {
            stringBuffer.append(", \ncurrentTrackInfo:");
            stringBuffer.append(wlPlayer.getCurrentSubtitleTrack().toString());
        }
        WlLog.d(tag + ":\n" + stringBuffer.toString());
    }

}
