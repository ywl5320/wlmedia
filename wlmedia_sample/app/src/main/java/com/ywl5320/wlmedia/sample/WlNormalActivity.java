package com.ywl5320.wlmedia.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlAudioChannel;
import com.ywl5320.wlmedia.enums.WlBufferType;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlVideoRotate;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.listener.WlOnPcmDataListener;
import com.ywl5320.wlmedia.listener.WlOnTakePictureListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.surface.WlSurfaceView;
import com.ywl5320.wlmedia.widget.WlCircleLoadView;
import com.ywl5320.wlmedia.widget.WlSeekBar;


public class WlNormalActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlCircleLoadView wlCircleLoadView;
    private WlSeekBar wlSeekBar;
    private WlSeekBar wlSeeVolume;
    private ImageView ivImg;

    private WlMedia wlMedia;
    private WlmediaListener wlmediaListener;
    private TextView tvVolume;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
        setContentView(R.layout.activity_normal_player_layout);
        url = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4";
        url = "http://zhibo.hkstv.tv/livestream/mutfysrq.flv";
        wlSurfaceView = findViewById(R.id.wlsurface);
        wlCircleLoadView = findViewById(R.id.circleview);
        wlSeekBar = findViewById(R.id.seekbar);
        ivImg = findViewById(R.id.iv_img);
        wlSeeVolume = findViewById(R.id.seekVolume);
        wlCircleLoadView.setVisibility(View.GONE);
        tvVolume = findViewById(R.id.tv_volume);

        wlMedia = new WlMedia();
        wlMedia.setSource(url);
        wlMedia.setUseSoundTouch(true);
        wlMedia.setClearLastPicture(true);
        wlMedia.setLoopPlay(true);
        wlSeeVolume.setProgress(wlMedia.getVolume() / 200);
        tvVolume.setText(((int)wlMedia.getVolume()) + "%");
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setTimeOut(30);
        wlSeeVolume.setColorProgress(R.color.teal_200);
        wlSeeVolume.setColorThumbNormal(R.color.teal_700);
        wlSeeVolume.setColorThumbTouch(R.color.purple_200);

        wlmediaListener = new WlmediaListener();
        wlMedia.setOnMediaInfoListener(wlmediaListener);
        wlMedia.setOnTakePictureListener(new WlOnTakePictureListener() {
            @Override
            public void takePicture(Bitmap bitmap) {
                ivImg.setImageBitmap(bitmap);
            }
        });
        wlMedia.setOnPcmDataListener(new WlOnPcmDataListener() {
            @Override
            public void onPcmInfo(int bit, int channel, int samplerate) {
                WlLog.d("pcmcallback bit:" + bit + ",channel:" + channel + ",sample" + samplerate);
            }

            @Override
            public void onPcmData(int size, byte[] data, double db) {
                WlLog.d("pcmcallback size:" + size + ",db:" + db);
            }
        });
        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                wlMedia.prepared();
            }

            @Override
            public void onSurfaceChange(int width, int height)
            {
            }

            @Override
            public void moveX(double value, int move_type) {
                if(move_type == WlSurfaceView.MOVE_START)//滑动前 显示UI
                {
                    wlMedia.seekStart();
                }
                else if(move_type == WlSurfaceView.MOVE_ING)//滑动中
                {
                    wlSeekBar.setProgress( value / wlMedia.getDuration());
                }
                else if(move_type == WlSurfaceView.MOVE_STOP)//滑动结束 seek
                {
                    if(value >= 0 && wlMedia.getDuration() > 0)
                    {
                        wlMedia.seek(value);
                    }
                    else{
                        wlMedia.seekEnd();
                    }
                }
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

        wlSeeVolume.setOnWlSeekBarChangeListener(new WlSeekBar.OnWlSeekBarChangeListener() {
            @Override
            public void onStart(float value) {

            }

            @Override
            public void onMove(float value) {
                wlMedia.setVolume(value * 200);
                tvVolume.setText(((int)wlMedia.getVolume()) + "%");
            }

            @Override
            public void onEnd(float value) {

            }
        });

        wlSeekBar.setOnWlSeekBarChangeListener(new WlSeekBar.OnWlSeekBarChangeListener() {
            @Override
            public void onStart(float value) {
                wlMedia.seekStart();
            }

            @Override
            public void onMove(float value) {

            }

            @Override
            public void onEnd(float value) {
                if(wlMedia.getDuration() > 0)
                {
                    wlMedia.seek(value * wlMedia.getDuration());
                }
                else{
                    wlMedia.seekEnd();
                }
            }
        });
    }

    public void onClick_begin(View view) {
        wlMedia.setSource(url);
        wlMedia.prepared();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setOnMediaInfoListener(wlmediaListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onClick_stop(View view) {
        wlMedia.stop();
    }

    public void onClick_pause(View view) {
        wlMedia.pause();
    }

    public void onClick_resume(View view) {
        wlMedia.resume();
    }

    boolean change = false;
    public void onClick_next(View view) {
        change = !change;
        if(change)
        {
            wlMedia.setSource("http://otttv.bj.chinamobile.com/TVOD/88888888/224/3221226469/1.m3u8");
        }
        else{
            wlMedia.setSource("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4");
        }
        wlMedia.next();
    }

    public void onClick_4_3(View view) {
        wlMedia.scaleVideo(4, 3);
    }

    public void onClick_3_4(View view) {
        wlMedia.scaleVideo(3, 4);
    }

    public void onClick_16_9(View view) {
        wlMedia.scaleVideo(16, 9);
    }

    public void onClick_9_16(View view) {
        wlMedia.scaleVideo(9, 16);
    }

    public void onClick_takepicture(View view) {
        wlMedia.takePicture();
    }

    public void onClick_d_0(View view) {
        wlMedia.rotateVideo(WlVideoRotate.VIDEO_ROTATE_0);
    }

    public void onClick_d_90(View view) {
        wlMedia.rotateVideo(WlVideoRotate.VIDEO_ROTATE_90);
    }

    public void onClick_d_180(View view) {
        wlMedia.rotateVideo(WlVideoRotate.VIDEO_ROTATE_180);
    }

    public void onClick_d_270(View view) {
        wlMedia.rotateVideo(WlVideoRotate.VIDEO_ROTATE_270);
    }

    public void onClick_d_default(View view) {
        wlMedia.rotateVideo(WlVideoRotate.VIDEO_ROTATE_DEFAULT);
    }

    public void onClick_left(View view) {
        wlMedia.setAudioChannel(WlAudioChannel.CHANNEL_LEFT);
    }

    public void onClick_left_center(View view) {
        wlMedia.setAudioChannel(WlAudioChannel.CHANNEL_LEFT_CENTER);
    }

    public void onClick_center(View view) {
        wlMedia.setAudioChannel(WlAudioChannel.CHANNEL_CENTER);
    }

    public void onClick_right(View view) {
        wlMedia.setAudioChannel(WlAudioChannel.CHANNEL_RIGHT);
    }

    public void onClick_right_center(View view) {
        wlMedia.setAudioChannel(WlAudioChannel.CHANNEL_RIGHT_CENTER);
    }

    public void onClick_normal(View view) {
        String frgmant_shader = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "vec4 v=texture2D(sTexture, ft_Position); " +
                "gl_FragColor = v;" +
                "}";
        wlMedia.setFshader(frgmant_shader);
        wlMedia.changeFilter();
    }

    public void onClick_four_screen(View view) {
        String frgmant_shader = "\n" +
                "precision mediump float;\n" +
                "varying vec2 ft_Position;\n" +
                "uniform sampler2D sTexture;\n" +
                "void main () {\n" +
                "    vec2 uv = ft_Position;\n" +
                "    if (uv.x <= 0.5) {\n" +
                "        uv.x = uv.x * 2.0;\n" +
                "    } else {\n" +
                "        uv.x = (uv.x - 0.5) * 2.0;\n" +
                "    }\n" +
                "    if (uv.y <= 0.5) {\n" +
                "        uv.y = uv.y * 2.0;\n" +
                "    } else {\n" +
                "        uv.y = (uv.y - 0.5) * 2.0;\n" +
                "    }\n" +
                "    gl_FragColor = texture2D(sTexture, fract(uv));\n" +
                "}";
        wlMedia.setFshader(frgmant_shader);
        wlMedia.changeFilter();
    }

    public void onClick_bw(View view) {
        String frgmant_shader = "precision mediump float;" +
                "varying vec2 ft_Position;" +
                "uniform sampler2D sTexture; " +
                "void main() " +
                "{ " +
                "vec4 v=texture2D(sTexture, ft_Position); " +
                "float average = (v.r + v.g + v.b) / 3.0;" +
                "gl_FragColor = vec4(average, average, average, v.a);" +
                "}";
        wlMedia.setFshader(frgmant_shader);
        wlMedia.changeFilter();
    }

    public void onClick_full_screen(View view) {
        wlMedia.scaleVideo(wlMedia.getSurfaceWidth(), wlMedia.getSurfaceHeight());
    }

    public void onClick_speed_0_5(View view) {
        wlMedia.setSpeed(0.5);
    }

    public void onClick_speed_0_(View view) {
        wlMedia.setSpeed(0.75);
    }

    public void onClick_speed_1_0(View view) {
        wlMedia.setSpeed(1.0);
    }

    public void onClick_speed_1_25(View view) {
        wlMedia.setSpeed(1.25);
    }

    public void onClick_speed_1_5(View view) {
        wlMedia.setSpeed(1.5);
    }

    public class WlmediaListener implements WlOnMediaInfoListener
    {
        @Override
        public void onPrepared() {
            wlMedia.start();
        }

        @Override
        public void onError(int code, String msg) {
            WlLog.d("onError " + msg);
            showToast(msg);

        }

        @Override
        public void onComplete(WlComplete type, String msg) {
//            if(type == WlComplete.WL_COMPLETE_EOF)
//            {
//                showToast("播放完成");
//            }
//            else if(type == WlComplete.WL_COMPLETE_ERROR)
//            {
//                showToast("播放出错：" + msg);
//            }
//            else if(type == WlComplete.WL_COMPLETE_TIMEOUT)
//            {
//                showToast("链接超时，正在重连");
//                wlMedia.next();
//            }
        }

        @Override
        public void onTimeInfo(double currentTime, double bufferTime) {
            if(wlMedia.getDuration() > 0)
            {
                wlSeekBar.setProgress(currentTime / wlMedia.getDuration(), bufferTime / wlMedia.getDuration());
            }
            else{
                wlSeekBar.setProgress(0.5);
            }
            WlLog.d("onTimeInfo 2 " + currentTime + " " + bufferTime);
        }

        @Override
        public void onSeekFinish() {

        }

        @Override
        public void onLoopPlay(int loopCount) {

        }

        @Override
        public void onLoad(boolean load) {
            if(load)
            {
                wlCircleLoadView.setVisibility(View.VISIBLE);
            }
            else{
                wlCircleLoadView.setVisibility(View.GONE);
            }
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
    }

    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    }
}