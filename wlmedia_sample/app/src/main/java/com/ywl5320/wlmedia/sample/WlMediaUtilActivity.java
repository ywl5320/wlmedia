package com.ywl5320.wlmedia.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMediaUtil;
import com.ywl5320.wlmedia.bean.WlTrackInfoBean;
import com.ywl5320.wlmedia.log.WlLog;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/18
 */
public class WlMediaUtilActivity extends AppCompatActivity {

    private ImageView ivPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlmedia_util);
        ivPicture = findViewById(R.id.iv_picture);
    }

    public void onClickSnapshot(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WlMediaUtil wlMediaUtil = new WlMediaUtil();
                wlMediaUtil.setSource(getFilesDir().getAbsolutePath() + "/testvideos/yfx.mp4");
                int ret = wlMediaUtil.openSource();
                WlLog.d("open source ret = " + ret);
                WlTrackInfoBean[] tracks = wlMediaUtil.getAudioTracks();
                if (tracks != null) {
                    for (WlTrackInfoBean trackInfoBean : tracks) {
                        WlLog.d(trackInfoBean.toString());
                    }
                }
                Bitmap bitmap = wlMediaUtil.getVideoFrame(0, false);
                if (bitmap != null) {
                    WlLog.d("get video bitmap " + bitmap.getWidth() + "*" + bitmap.getHeight());
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                } else {
                    WlLog.d("get video bitmap is null");
                }
                wlMediaUtil.release();
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            ivPicture.setImageBitmap(bitmap);
        }
    };
}
