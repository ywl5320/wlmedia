package com.ywl5320.wlmedia.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.surface.WlSurfaceView;

public class WlMutPlayActivity extends AppCompatActivity {

    private WlSurfaceView wlsurfaceview1, wlsurfaceview2, wlsurfaceview3;
    private WlMedia wlMedia1, wlMedia2, wlMedia3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mut_play_layout);

        wlsurfaceview1 = findViewById(R.id.wlsurfaceview1);
        wlsurfaceview2 = findViewById(R.id.wlsurfaceview2);
        wlsurfaceview3 = findViewById(R.id.wlsurfaceview3);

        //线路一
        wlMedia1 = new WlMedia();
        wlsurfaceview1.setWlMedia(wlMedia1);
        wlMedia1.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia1.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlMedia1.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia1.start();
            }
        });
        wlsurfaceview1.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia1.setSource(WlAssetsUtil.getAssetsFilePath(WlMutPlayActivity.this, "fcrs.1080p.mp4"));
                wlMedia1.prepared();
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {

            }
        });

        //线路二
        wlMedia2 = new WlMedia();
        wlsurfaceview2.setWlMedia(wlMedia2);
        wlMedia2.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia2.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlMedia2.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia2.start();
            }
        });
        wlsurfaceview2.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia2.setSource(WlAssetsUtil.getAssetsFilePath(WlMutPlayActivity.this, "xjzw_cut.mkv"));
                wlMedia2.prepared();
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {

            }
        });

        //线路三
        wlMedia3 = new WlMedia();
        wlsurfaceview3.setWlMedia(wlMedia3);
        wlMedia3.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia3.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlMedia3.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia3.start();
            }
        });
        wlsurfaceview3.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia3.setSource(WlAssetsUtil.getAssetsFilePath(WlMutPlayActivity.this, "mytest.h264"));
                wlMedia3.prepared();
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wlMedia1.release();
        wlMedia2.release();
        wlMedia3.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia1.stop();
        wlMedia2.stop();
        wlMedia3.stop();
    }
}
