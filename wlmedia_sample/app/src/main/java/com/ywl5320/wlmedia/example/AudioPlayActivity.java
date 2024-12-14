package com.ywl5320.wlmedia.example;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.util.WlTimeUtil;
import com.ywl5320.wlmedia.widget.WlSeekBar;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/12
 */
public class AudioPlayActivity extends AppCompatActivity {

    private WlPlayer wlPlayer;
    private WlSeekBar wlSeekBar;
    private TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_audio);

        wlSeekBar = findViewById(R.id.wlseekbar);
        tvTime = findViewById(R.id.tv_time);

        wlPlayer = new WlPlayer();
        wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
            @Override
            public void onPrepared() {
                wlPlayer.start();
            }

            @Override
            public void onTimeInfo(double currentTime, double bufferTime) {
                if (wlPlayer.getDuration() > 0) {
                    double progress = currentTime / wlPlayer.getDuration();
                    double bprogress = bufferTime / wlPlayer.getDuration();
                    tvTime.setText(WlTimeUtil.secondToTimeFormat(currentTime) + "/" + WlTimeUtil.secondToTimeFormat(wlPlayer.getDuration()));
                    wlSeekBar.setProgress(progress, bprogress);
                } else {
                    wlSeekBar.setProgress(0.5);
                    tvTime.setText(WlTimeUtil.secondToTimeFormat(currentTime));
                }
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
        wlSeekBar.setOnWlSeekBarChangeListener(new WlSeekBar.OnWlSeekBarChangeListener() {
            @Override
            public void onStart(double v) {
                if (wlPlayer.getDuration() > 0) {
                    wlPlayer.seekStart();
                }
            }

            @Override
            public void onMove(double v) {

            }

            @Override
            public void onEnd(double v) {
                if (wlPlayer.getDuration() > 0) {
                    double time = v * wlPlayer.getDuration();
                    wlPlayer.seek(time);
                }
            }
        });
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/mydream.m4a");
        wlPlayer.setPlayModel(WlPlayModel.WL_PLAY_MODEL_ONLY_AUDIO);
        wlPlayer.prepare();
    }

    public void onClickPlay(View view) {
//        wlPlayer.setSource("");
        wlPlayer.prepare();
    }

    public void onClickStop(View view) {
        wlPlayer.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
