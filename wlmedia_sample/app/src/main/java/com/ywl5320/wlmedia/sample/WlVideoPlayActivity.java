package com.ywl5320.wlmedia.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.surface.WlSurfaceView;

/**
 * @author ywl5320
 * @date 2021/5/18
 */
public class WlVideoPlayActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlMedia wlMedia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_layout);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlMedia = new WlMedia();
        wlMedia.setSource("http://zhibo.hkstv.tv/livestream/mutfysrq.flv");
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setOnMediaInfoListener(new WlOnMediaInfoListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onComplete(WlComplete type, String msg) {

            }

            @Override
            public void onTimeInfo(double currentTime, double bufferTime) {

            }

            @Override
            public void onSeekFinish() {

            }

            @Override
            public void onLoopPlay(int loopCount) {

            }

            @Override
            public void onLoad(boolean load) {

            }

            @Override
            public byte[] decryptBuffer(byte[] encryptBuffer) {
                return new byte[0];
            }

            @Override
            public byte[] readBuffer(int read_size) {
                return new byte[0];
            }

            @Override
            public void onPause(boolean pause) {

            }
        });

        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.prepared();
            }

            @Override
            public void onSurfaceChange(int width, int height) {

            }

            @Override
            public void moveX(double value, int move_type) {

            }

            @Override
            public void onSingleClick() {

            }

            @Override
            public void onDoubleClick() {

            }

            @Override
            public void moveLeft(double value, int move_type) {

            }

            @Override
            public void moveRight(double value, int move_type) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia.release();
    }
}
