package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnTimeInfoListener;
import com.ywl5320.wlmedia.util.WlTimeUtil;

public class WlAudioActivity extends AppCompatActivity {

    private WlMedia wlMedia;

    private TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_layout);
        tvTime = findViewById(R.id.tv_time);
        wlMedia = new WlMedia();
        wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_AUDIO);
        wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(this, "mydream.m4a"));
        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
        wlMedia.setOnTimeInfoListener(new WlOnTimeInfoListener() {
            @Override
            public void onTimeInfo(double currentTime) {
                tvTime.setText(WlTimeUtil.secdsToDateFormat((int) currentTime) + "/" + WlTimeUtil.secdsToDateFormat((int) wlMedia.getDuration()));
            }
        });
        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete() {
                WlAudioActivity.this.finish();
            }
        });
        wlMedia.prepared();
    }

    @Override
    public void onBackPressed() {
        wlMedia.stop();
    }
}
