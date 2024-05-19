package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlSeekType;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/18
 */
public class ShowFirstFrameActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlPlayer wlPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_first_frame);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlPlayer = new WlPlayer();
        wlSurfaceView.setWlPlayer(wlPlayer);
        wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
            @Override
            public void onPrepared() {
                wlPlayer.start();
                wlPlayer.pause();
                wlPlayer.seek(10);
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
        });
    }

    public void onClickPlay(View view){
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/yfx.mp4");
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
