package com.ywl5320.wlmedia.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.bean.WlOutRenderBean;
import com.ywl5320.wlmedia.bean.WlTrackInfoBean;
import com.ywl5320.wlmedia.enums.WlAudioChannelType;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlMirrorType;
import com.ywl5320.wlmedia.enums.WlRotateType;
import com.ywl5320.wlmedia.enums.WlScaleType;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.util.WlTimeUtil;
import com.ywl5320.wlmedia.widget.WlCircleLoadView;
import com.ywl5320.wlmedia.widget.WlSeekBar;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/12
 */
public class NormalPlayActivity extends AppCompatActivity {

    private WlPlayer wlPlayer;
    private WlSurfaceView wlSurfaceView;
    private WlCircleLoadView wlCircleLoadView;
    private ImageView ivTakePicture;
    private TextView tvTrackInfo;
    private WlSeekBar wlSeekBar;
    private TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_normal_play);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlCircleLoadView = findViewById(R.id.wlcircleview);
        ivTakePicture = findViewById(R.id.iv_take_picture);
        tvTrackInfo = findViewById(R.id.tv_track_info);
        wlSeekBar = findViewById(R.id.wlseekbar);
        tvTime = findViewById(R.id.tv_time);
        initPlayer();
        wlSurfaceView.setWlPlayer(wlPlayer);
        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                // 视频view准备好回调
                wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/yfx.mp4");
                wlPlayer.prepare();
            }

            @Override
            public void onSurfaceChange(int i, int i1) {
                // 视频view大小改变
            }

            @Override
            public void moveX(double v, int i) {

            }

            @Override
            public void onSingleClick() {

            }

            @Override
            public void onDoubleClick() {

            }

            @Override
            public void moveLeft(double v, int i) {

            }

            @Override
            public void moveRight(double v, int i) {

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
    }

    private void initPlayer() {
        wlPlayer = new WlPlayer();
        printWlPlayerInfo("init start");
        // 设置使用OpenGL渲染的最大视频宽高
        wlPlayer.setRenderDefaultSize(3840, 2160);
        // 是否自动播放
//        wlPlayer.setAutoPlay(true);
        // 时间进度回调频率：多少秒一次，最快 0.01s 一次
//        wlPlayer.setTimeInfoInterval(1);
        wlPlayer.setClearLastVideoFrame(false);
        // 设置音量
        wlPlayer.setVolume(100);
        printWlPlayerInfo("init end");
        wlPlayer.setOnMediaInfoListener(new WlOnMediaInfoListener() {
            @Override
            public void onPrepared() {
                printWlPlayerInfo("prepared");
                StringBuffer stringBuffer = new StringBuffer();
                WlTrackInfoBean[] audioTracks = wlPlayer.getAudioTracks();
                for (WlTrackInfoBean trackInfoBean : audioTracks) {
                    stringBuffer.append(trackInfoBean.toString());
                    stringBuffer.append("\n");
                }
                WlTrackInfoBean[] videoTracks = wlPlayer.getVideoTracks();
                for (WlTrackInfoBean trackInfoBean : videoTracks) {
                    stringBuffer.append(trackInfoBean.toString());
                    stringBuffer.append("\n");
                }
                tvTrackInfo.setText(stringBuffer.toString());

                wlPlayer.start();
                WlLog.d("2 info:" + Util.getPlayerInfo(wlPlayer));
            }

            @Override
            public void onTimeInfo(double currentTime, double bufferTime) {
                if (wlPlayer.getDuration() > 0) {
                    WlLog.d("onTimeInfo:" + currentTime + "," + bufferTime);
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
                WlLog.d("onComplete:" + s);
                if (wlCompleteType == WlCompleteType.WL_COMPLETE_EOF) {

                } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_ERROR) {

                } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_HANDLE) {

                } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_NEXT) {

                } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_TIMEOUT) {

                } else if (wlCompleteType == WlCompleteType.WL_COMPLETE_RELEASE) {

                }
            }

            @Override
            public void onLoad(WlLoadStatus wlLoadStatus, int i, long l) {
                if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_START) {
                    wlCircleLoadView.setVisibility(View.VISIBLE);
                } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_PROGRESS) {

                } else if (wlLoadStatus == WlLoadStatus.WL_LOADING_STATUS_FINISH) {
                    wlCircleLoadView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTakePicture(Bitmap bitmap) {
                ivTakePicture.setImageBitmap(bitmap);
            }

            @Override
            public void onAutoPlay() {
                printWlPlayerInfo("autoPlay");
            }

            @Override
            public void onFirstFrameRendered() {
                printWlPlayerInfo("firstFrameRender");
            }

            @Override
            public void onSeekFinish() {
                printWlPlayerInfo("seekFinish");
            }

            @Override
            public byte[] decryptBuffer(byte[] encryptBuffer, long position) {
                return null;
            }

            @Override
            public byte[] readBuffer(int read_size) {
                return null;
            }

            @Override
            public void onOutRenderInfo(WlOutRenderBean outRenderBean) {

            }
        });
    }

    public void onClickPrepare(View view) {
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/yfx.mp4");
        wlPlayer.prepare();
        printWlPlayerInfo("prepare");
    }

    public void onClickResume(View view) {
        wlPlayer.resume();
        printWlPlayerInfo("resume");
    }

    public void onClickPause(View view) {
        wlPlayer.pause();
        printWlPlayerInfo("pause");
    }

    public void onClickStop(View view) {
        wlPlayer.stop();
        printWlPlayerInfo("stop");
    }

    public void onClickNext(View view) {
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/qyn2.mkv");
        wlPlayer.prepare();
        printWlPlayerInfo("next");
    }

    public void onClickScaleFit(View view) {
        wlPlayer.scaleVideo(WlScaleType.WL_SCALE_FIT);
        printWlPlayerInfo("scaleFit");
    }

    public void onClickScaleMatch(View view) {
        wlPlayer.scaleVideo(WlScaleType.WL_SCALE_MATCH);
        printWlPlayerInfo("scaleMatch");
    }

    public void onClickScale16_9(View view) {
        wlPlayer.scaleVideo(WlScaleType.WL_SCALE_16_9);
        printWlPlayerInfo("scale16_9");
    }

    public void onClickScale4_3(View view) {
        wlPlayer.scaleVideo(WlScaleType.WL_SCALE_4_3);
        printWlPlayerInfo("scale4_3");
    }

    public void onClickRotate0(View view) {
        wlPlayer.rotateVideo(WlRotateType.WL_ROTATE_0);
        printWlPlayerInfo("rotate0");
    }

    public void onClickRotate90(View view) {
        wlPlayer.rotateVideo(WlRotateType.WL_ROTATE_90);
        printWlPlayerInfo("rotate90");
    }

    public void onClickRotate180(View view) {
        wlPlayer.rotateVideo(WlRotateType.WL_ROTATE_180);
        printWlPlayerInfo("rotate180");
    }

    public void onClickRotate270(View view) {
        wlPlayer.rotateVideo(WlRotateType.WL_ROTATE_270);
        printWlPlayerInfo("rotate270");
    }

    public void onClickNoMir(View view) {
        wlPlayer.mirrorVideo(WlMirrorType.WL_MIRROR_NONE);
        printWlPlayerInfo("noMir");
    }

    public void onClickTBMir(View view) {
        wlPlayer.mirrorVideo(WlMirrorType.WL_MIRROR_TOP_BOTTOM);
        printWlPlayerInfo("TBMir");
    }

    public void onClickLRMir(View view) {
        wlPlayer.mirrorVideo(WlMirrorType.WL_MIRROR_LEFT_RIGHT);
        printWlPlayerInfo("LRMir");
    }

    public void onClickTBLRMir(View view) {
        wlPlayer.mirrorVideo(WlMirrorType.WL_MIRROR_TOP_BOTTOM_LEFT_RIGHT);
        printWlPlayerInfo("TBLRMir");
    }

    public void onClickS05(View view) {
        wlPlayer.setSpeed(0.5);
        printWlPlayerInfo("speed05");
    }

    public void onClickS10(View view) {
        wlPlayer.setSpeed(1.0);
        printWlPlayerInfo("speed10");
    }

    public void onClickS15(View view) {
        wlPlayer.setSpeed(1.5);
        printWlPlayerInfo("speed15");
    }

    public void onClickS20(View view) {
        wlPlayer.setSpeed(2.0);
        printWlPlayerInfo("speed20");
    }

    public void onClickP05(View view) {
        wlPlayer.setPitch(0.5);
        printWlPlayerInfo("pitch05");
    }

    public void onClickP10(View view) {
        wlPlayer.setPitch(1.0);
        printWlPlayerInfo("pitch10");
    }

    public void onClickP15(View view) {
        wlPlayer.setPitch(1.5);
        printWlPlayerInfo("pitch15");
    }

    public void onClickP20(View view) {
        wlPlayer.setPitch(2.0);
        printWlPlayerInfo("pitch20");
    }

    public void onClickL(View view) {
        wlPlayer.setAudioChannelType(WlAudioChannelType.WL_AUDIO_CHANNEL_LEFT);
    }

    public void onClickLC(View view) {
        wlPlayer.setAudioChannelType(WlAudioChannelType.WL_AUDIO_CHANNEL_LEFT_CENTER);
    }

    public void onClickC(View view) {
        wlPlayer.setAudioChannelType(WlAudioChannelType.WL_AUDIO_CHANNEL_CENTER);
    }

    public void onClickR(View view) {
        wlPlayer.setAudioChannelType(WlAudioChannelType.WL_AUDIO_CHANNEL_RIGHT);
    }

    public void onClickRC(View view) {
        wlPlayer.setAudioChannelType(WlAudioChannelType.WL_AUDIO_CHANNEL_RIGHT_CENTER);
    }

    public void onClickTakePicture(View view) {
        wlPlayer.takePicture();
    }

    private void printWlPlayerInfo(String action) {
        WlLog.d(action + ":\n" + Util.getPlayerInfo(wlPlayer));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}

