package com.ywl5320.wlmedia.example;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/14
 */
public class MultiPlayerActivity extends AppCompatActivity {

    private WlPlayer wlPlayer1, wlPlayer2;
    private WlSurfaceView wlSurfaceView1, wlSurfaceView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_multi_player);
        wlSurfaceView1 = findViewById(R.id.wlsurfaceview1);
        wlSurfaceView2 = findViewById(R.id.wlsurfaceview2);
        wlPlayer1 = createPlayer();
        wlPlayer2 = createPlayer();

        wlSurfaceView1.setWlPlayer(wlPlayer1);
        wlSurfaceView2.setWlPlayer(wlPlayer2);
        wlSurfaceView1.setClearLastVideoFrame(false);
        wlSurfaceView2.setClearLastVideoFrame(true);

        wlPlayer1.setSource(getFilesDir().getAbsolutePath() + "/testvideos/big_buck_bunny_cut.mp4");
        wlPlayer1.prepare();
        wlPlayer2.setSource(getFilesDir().getAbsolutePath() + "/testvideos/huoying_cut.mkv");
        wlPlayer2.prepare();
    }

    private WlPlayer createPlayer() {
        WlPlayer wlPlayer = new WlPlayer();
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
        });
        return wlPlayer;
    }

    public void onClickPrepare1(View view) {
        wlPlayer1.prepare();
    }

    public void onClickResume1(View view) {
        wlPlayer1.resume();
    }

    public void onClickPause1(View view) {
        wlPlayer1.pause();
    }

    public void onClickStop1(View view) {
        wlPlayer1.stop();
    }

    public void onClickPrepare2(View view) {
        wlPlayer2.prepare();
    }

    public void onClickResume2(View view) {
        wlPlayer2.resume();
    }

    public void onClickPause2(View view) {
        wlPlayer2.pause();
    }

    public void onClickStop2(View view) {
        wlPlayer2.stop();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer1.release();
        wlPlayer2.release();
    }
}
