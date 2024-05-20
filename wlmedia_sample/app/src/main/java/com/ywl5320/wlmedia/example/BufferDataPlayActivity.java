package com.ywl5320.wlmedia.example;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlSourceType;
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

    private FileInputStream fio = null;

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
            public byte[] readBuffer(int read_size) {
                // 这里只是模拟读取buffer,实际开发中，大多数是从队列中获取。
                if (fio == null) {
                    try {
                        fio = new FileInputStream(new File(getFilesDir().getAbsolutePath() + "/testvideos/qyn2.mkv"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                byte[] buffer = new byte[read_size];
                try {
                    int size = fio.read(buffer);
                    if (size > 0) {
                        return buffer;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public void onClickPlay(View view){
        wlPlayer.setSourceType(WlSourceType.WL_SOURCE_BUFFER);
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
