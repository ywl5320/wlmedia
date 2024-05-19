package com.ywl5320.wlmedia.sample;

import android.content.Context;
import android.os.Environment;

import com.ywl5320.wlmedia.WlPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/12
 */
public class Util {

    public static String getPlayerInfo(WlPlayer wlPlayer) {
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

        return stringBuffer.toString();
    }

    public static boolean copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.isEmpty()) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isFilesExists(String[] files) {
        if (files == null || files.length == 0) {
            return false;
        }
        for (String file : files) {
            File f = new File(file);
            if (!f.exists()) {
                return false;
            }
        }
        return true;
    }
}
