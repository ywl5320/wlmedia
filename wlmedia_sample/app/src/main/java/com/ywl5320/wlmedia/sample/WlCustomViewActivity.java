package com.ywl5320.wlmedia.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.widget.WlCircleLoadView;
import com.ywl5320.wlmedia.widget.WlSeekBar;

/**
 * @author ywl5320
 * @date 2021/5/16
 */
public class WlCustomViewActivity extends AppCompatActivity {

    private WlCircleLoadView wlLoadView1;
    private WlCircleLoadView wlLoadView2;
    private WlSeekBar wlSeekBar1;
    private WlSeekBar wlSeekBar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview_layout);
        wlLoadView1 = findViewById(R.id.wl_loadview1);
        wlLoadView2 = findViewById(R.id.wl_loadview2);

        wlLoadView1.setColor(R.color.white);
        wlLoadView2.setColor(R.color.purple_500);

        wlSeekBar1 = findViewById(R.id.wl_seekbar1);
        wlSeekBar2 = findViewById(R.id.wl_seekbar2);

        wlSeekBar1.setColorBg(R.color.teal_700);
        wlSeekBar1.setColorBuffer(R.color.teal_200);
        wlSeekBar1.setColorProgress(R.color.purple_200);
        wlSeekBar1.setColorThumbNormal(R.color.purple_200);
        wlSeekBar1.setColorThumbTouch(R.color.colorAccent);
        wlSeekBar1.setBgHeight(10);
        wlSeekBar1.setRound(true);
        wlSeekBar1.setThumbRadius(8);
        wlSeekBar1.setProgress(0.6, 0.8);
        wlSeekBar1.setOnWlSeekBarChangeListener(new WlSeekBar.OnWlSeekBarChangeListener() {
            @Override
            public void onStart(float v) {

            }

            @Override
            public void onMove(float v) {

            }

            @Override
            public void onEnd(float v) {

            }
        });

        wlSeekBar2.setProgress(0.5);
        wlSeekBar2.setOnWlSeekBarChangeListener(new WlSeekBar.OnWlSeekBarChangeListener() {
            @Override
            public void onStart(float v) {

            }

            @Override
            public void onMove(float v) {

            }

            @Override
            public void onEnd(float v) {

            }
        });
    }
}
