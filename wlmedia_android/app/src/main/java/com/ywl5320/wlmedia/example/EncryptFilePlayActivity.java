package com.ywl5320.wlmedia.example;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.enums.WlTrackType;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/18
 */
public class EncryptFilePlayActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlPlayer wlPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_encrypt_file);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlPlayer = new WlPlayer();
        wlSurfaceView.setWlPlayer(wlPlayer, "#ff0000");
        wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
            @Override
            public void onPrepared() {
                wlPlayer.start();
            }

            @Override
            public void onTimeInfo(double v, double v1) {

            }

            @Override
            public void onComplete(WlCompleteType wlCompleteType, String s) {
                WlLog.d("complete:" + wlCompleteType.getKey() + "," + s);
            }

            @Override
            public void onLoad(WlLoadStatus wlLoadStatus, int i, long l) {

            }

            @Override
            public void onSeekFinish() {

            }

            @Override
            public void onFirstFrameRendered() {

            }

            @Override
            public byte[] onDeEncryptData(WlTrackType mediaType, byte[] data) {
                if(mediaType == WlTrackType.WL_TRACK_AUDIO){
                    // 这里可以对音频 data 帧数据进行解密 如aes解密
                }
                else if(mediaType == WlTrackType.WL_TRACK_VIDEO){
                    // 这里可以对视频 data 帧数据进行解密 如aes解密
                }
                return data;
            }
        });
    }

    public void onClickPlay(View view) {
        wlPlayer.setBufferDeEncrypt(true);
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/big_buck_bunny_cut.mp4");
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
