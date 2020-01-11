package com.ywl5320.wlmedia.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlMute;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnErrorListener;
import com.ywl5320.wlmedia.listener.WlOnLoadListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnTakePictureListener;
import com.ywl5320.wlmedia.listener.WlOnTimeInfoListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.surface.WlSurfaceView;
import com.ywl5320.wlmedia.surface.WlTextureView;
import com.ywl5320.wlmedia.util.WlTimeUtil;

public class WlNormalPlayerActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlTextureView wlTextureView;
    private FrameLayout flView;

    private WlMedia wlMedia;
    private boolean exit = false;

    private TextView tvTime;
    private TextView tvTotalTime;
    private TextView tvVolume;
    private SeekBar seekBar;
    private ImageView ivPic;
    private LinearLayout lyChannels;

    private boolean changeSurface = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_player_layout);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlTextureView = findViewById(R.id.wltextureveiw);
        flView = findViewById(R.id.fl_view);
        tvTime = findViewById(R.id.tv_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        tvVolume = findViewById(R.id.tv_volume);
        seekBar = findViewById(R.id.seek_bar);
        ivPic = findViewById(R.id.iv_pic);
        lyChannels = findViewById(R.id.ly_channels);

        wlMedia = WlMedia.getInstance();
        wlMedia.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia.setVolume(100);
        tvVolume.setText("音量：" + wlMedia.getVolume() + "%");
        seekBar.setProgress(wlMedia.getVolume());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                wlMedia.setVolume(progress);
                tvVolume.setText("音量：" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                if(wlMedia.getDuration() > 0)
                {
                    tvTotalTime.setText("/" + WlTimeUtil.secdsToDateFormat((int) Math.floor(wlMedia.getDuration())));
                }
                wlMedia.start();
            }
        });
        wlMedia.setOnTimeInfoListener(new WlOnTimeInfoListener() {
            @Override
            public void onTimeInfo(double currentTime) {
                WlLog.d("time is :" + currentTime);
                tvTime.setText(WlTimeUtil.secdsToDateFormat((int) Math.floor(currentTime)));
            }
        });
        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete() {
                WlLog.d("onComplete");
                if(exit)
                {
                    WlNormalPlayerActivity.this.finish();
                }
            }
        });

        wlMedia.setOnTakePictureListener(new WlOnTakePictureListener() {
            @Override
            public void takePicture(Bitmap bitmap) {
                ivPic.setImageBitmap(bitmap);
            }
        });

        wlMedia.setOnLoadListener(new WlOnLoadListener() {
            @Override
            public void onLoad(boolean b) {
                if(b)
                {
                    WlLog.d("加载中");
                }
                else
                {
                    WlLog.d("加载完成");
                }
            }
        });

        wlMedia.setOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(WlNormalPlayerActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(WlNormalPlayerActivity.this, "fcrs.1080p.mp4"));
                wlMedia.prepared();
            }

            @Override
            public void moveSlide(double value) {
                tvTime.setText(WlTimeUtil.secdsToDateFormat((int) Math.floor(value)));
            }

            @Override
            public void movdFinish(double value) {
                wlMedia.seek(value);
            }
        });
    }

    public void video_one(View view) {
        wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(WlNormalPlayerActivity.this, "fcrs.1080p.mp4"));
        wlMedia.next();
    }

    public void video_two(View view) {
        wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(WlNormalPlayerActivity.this, "xjzw_cut.mkv"));
        wlMedia.next();
    }

    @Override
    public void onBackPressed() {
        exit = true;
        if(wlMedia.isPlay())
        {
            wlMedia.stop();
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void stop(View view) {
        wlMedia.setClearLastPicture(true);
        wlMedia.stop();
    }

    public void pause(View view) {
        wlMedia.pause();
    }

    public void resume(View view) {
        wlMedia.resume();
    }

    public void scale_4_3(View view) {
        wlMedia.scaleVideo(4, 3);
    }

    public void scale_normal(View view) {
        wlMedia.scaleVideo(wlMedia.getVideoWidth(), wlMedia.getVideoHeight());
    }

    public void scale_16_9(View view) {
        wlMedia.scaleVideo(16, 9);
    }

    public void filter_gray(View view) {
        String fs = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "vec4 v=texture2D(sTexture, ft_Position); " +
                "float average = (v.r + v.g + v.b) / 3.0;" +
                "gl_FragColor = vec4(average, average, average, v.a);" +
                "}";
        wlMedia.setfShader(fs);
        wlMedia.changeFilter();
    }

    public void filter_inversion(View view) {

        String fs = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "vec4 v=texture2D(sTexture, ft_Position); " +
                "gl_FragColor = vec4(vec3(1.0 - v), v.a);" +
                "}";
        wlMedia.setfShader(fs);
        wlMedia.changeFilter();
    }

    public void filter_normal(View view) {
        String fs = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "gl_FragColor = texture2D(sTexture, ft_Position);" +
                "}";
        wlMedia.setfShader(fs);
        wlMedia.changeFilter();
    }

    public void screenshot(View view) {
        wlMedia.takePicture();
    }

    public void left(View view) {
        wlMedia.setMute(WlMute.MUTE_LEFT);
    }

    public void right(View view) {
        wlMedia.setMute(WlMute.MUTE_RIGHT);
    }

    public void center(View view) {
        wlMedia.setMute(WlMute.MUTE_CENTER);
    }

    public void pitch_0_5(View view) {
        wlMedia.setPitch(0.5f);
    }

    public void pitch_1_0(View view) {
        wlMedia.setPitch(1.0f);
    }

    public void pitch_1_5(View view) {
        wlMedia.setPitch(1.5f);
    }

    public void speed_0_5(View view) {
        wlMedia.setSpeed(0.5f);
    }

    public void speed_1_0(View view) {
        wlMedia.setSpeed(1.0f);
    }

    public void speed_1_5(View view) {
        wlMedia.setSpeed(1.2f);
    }

    public void getAudioChannels(View view) {

        lyChannels.removeAllViews();
        String[] chanels = wlMedia.getAudioChannels();
        if(chanels != null && chanels.length > 0)
        {
            for(int i = 0; i < chanels.length; i++)
            {
                Button button = (Button) LayoutInflater.from(this).inflate(R.layout.item_button, lyChannels, false);
                button.setText(chanels[0]);
                lyChannels.addView(button);
                final int audioIndex = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wlMedia.setAudioChannel(audioIndex);
                    }
                });
            }
        }
    }

    public void changeSurface(View view) {
        changeSurface = !changeSurface;
        if(changeSurface)
        {
            wlTextureView.updateMedia(wlMedia);
            wlSurfaceView.updateMedia(null);
        }
        else
        {
            wlSurfaceView.updateMedia(wlMedia);
            wlTextureView.updateMedia(null);
        }
    }
}
