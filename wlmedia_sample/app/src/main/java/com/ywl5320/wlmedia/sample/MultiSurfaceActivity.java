package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.widget.WlCircleLoadView;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/14
 */
public class MultiSurfaceActivity extends AppCompatActivity {

    private WlPlayer wlPlayer;
    private WlSurfaceView wlSurfaceView1, wlSurfaceView2, wlSurfaceView3, wlSurfaceView4;
    private WlCircleLoadView loadView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_multi_surface);

        wlSurfaceView1 = findViewById(R.id.wlsurfaceview1);
        wlSurfaceView2 = findViewById(R.id.wlsurfaceview2);
        wlSurfaceView3 = findViewById(R.id.wlsurfaceview3);
        wlSurfaceView4 = findViewById(R.id.wlsurfaceview4);
        loadView = findViewById(R.id.loadview);

        wlPlayer = new WlPlayer();
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
                wlPlayer.prepare();
            }

            @Override
            public void onLoad(WlLoadStatus wlLoadStatus, int i, long l) {
                if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_START) {
                    loadView.setVisibility(View.VISIBLE);
                } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_FINISH) {
                    loadView.setVisibility(View.GONE);
                }
            }
        });

        wlSurfaceView1.setWlPlayer(wlPlayer);
        wlSurfaceView2.setWlPlayer(wlPlayer, "#FF0000");
        wlSurfaceView3.setWlPlayer(wlPlayer, "#00FF00");
        wlSurfaceView4.setWlPlayer(wlPlayer, "#0000FF");

        wlPlayer.setClearLastVideoFrame(false);
        wlPlayer.setOptions("reconnect", "1");
        wlPlayer.setOptions("reconnect_streamed", "1");
        wlPlayer.setOptions("multiple_requests", "1");
        wlPlayer.setOptions("reconnect_delay_max", "5");
        wlPlayer.setOptions("user_agent", "User-Agent:Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
        wlPlayer.setSource("https://xy61x159x92x170xy.mcdn.bilivideo.cn:486/live-bvc/771751/live_7734200_bs_1348183_minihevc/index.m3u8");
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
