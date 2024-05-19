package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlAlphaVideoType;
import com.ywl5320.wlmedia.enums.WlScaleType;
import com.ywl5320.wlmedia.widget.WlSurfaceView;
import com.ywl5320.wlmedia.widget.WlTextureView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/14
 */
public class AlphaVideoActivity extends AppCompatActivity {

    private WlTextureView wlSurfaceView;
    private WlPlayer wlPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_alpha_video);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlPlayer = new WlPlayer();
        wlPlayer.setAutoPlay(true);
        wlPlayer.scaleVideo(WlScaleType.WL_SCALE_MATCH);
        wlSurfaceView.setWlPlayer(wlPlayer, "#00000000");
        wlSurfaceView.setAlphaVideoType(WlAlphaVideoType.WL_ALPHA_VIDEO_LEFT_ALPHA);
        wlPlayer.setLoopPlay(true);
        wlPlayer.setClearLastVideoFrame(false);
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/alpha_left5.mp4");
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
