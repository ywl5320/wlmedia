package com.ywl5320.wlmedia.sample;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.ywl5320.wlmedia.log.WlLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wanli on 2019/11/28
 */
public class WlAssetsUtil {

    public static String getAssetsFilePath(Context context, String fileName)
    {
        File afile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wlmedia");
            if(!file.exists())
            {
                file.mkdir();
            }
            afile = new File(file.getAbsolutePath() + "/" + fileName);
            if(!afile.exists())
            {
                WlLog.d("拷贝视频中，请稍等...");
                Toast.makeText(context, "初次使用正在拷贝视频中，请稍等...", Toast.LENGTH_LONG).show();
                is = context.getResources().getAssets().open(fileName);
                fos = new FileOutputStream(afile);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();
            }
            return afile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(afile != null &&afile.exists())
        {
            return afile.getAbsolutePath();
        }
        return null;
    }

}
