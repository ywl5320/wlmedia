package com.ywl5320.wlmedia.example;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.listener.WlOnBufferDataListener;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/18
 */
public class BufferDataPlayActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlPlayer wlPlayer;
    private BufferByteStream bufferByteStream;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_data);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlPlayer = new WlPlayer();
        wlSurfaceView.setWlPlayer(wlPlayer);
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
                WlLog.d("complete:" + wlCompleteType.getKey() + "," + s);
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

        wlPlayer.setOnBufferDataListener(new WlOnBufferDataListener() {
            @Override
            public long onBufferByteLength() {
                if (bufferByteStream == null) {
                    bufferByteStream = new BufferByteStream(getFilesDir().getAbsolutePath() + "/testvideos/huoying_cut.mkv");
                }
                return bufferByteStream.getLength();
            }

            @Override
            public byte[] onBufferByteData(long position, long bufferSize) {
                if (bufferByteStream != null) {
                    byte[] b = bufferByteStream.read((int) bufferSize, position);
                    if (b != null) {
                        for (int i = 0; i < b.length; i++) {
//                            b[i] = (byte) ((int) b[i] ^ 88);
                        }
                    }
                    return b;
                }
                return null;
            }
        });
    }

    public void onClickPlay(View view) {
        wlPlayer.setSourceType(WlSourceType.WL_SOURCE_BUFFER);
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
