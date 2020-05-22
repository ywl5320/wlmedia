package com.ywl5320.wlmedia.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.listener.WlOnBufferListener;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnErrorListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.surface.WlTextureView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class WlBufferActivity extends AppCompatActivity {

    private WlTextureView wlTextureView;
    private WlMedia wlMedia;
    FileInputStream fi = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_layout);
        wlTextureView = findViewById(R.id.wltextureveiw);
        wlMedia = new WlMedia();
        wlMedia.setSourceType(WlSourceType.BUFFER);
        wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_ONLY_VIDEO);
        wlTextureView.setWlMedia(wlMedia);

        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete(WlComplete type) {
                WlLog.d("complete ");
                WlBufferActivity.this.finish();
            }
        });

        wlMedia.setOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int code, String msg) {
                WlLog.d(msg);
            }
        });




        wlMedia.setOnBufferListener(new WlOnBufferListener() {
            @Override
            public byte[] buffer(int read_size) {
                WlLog.d("read buffer " + read_size);
                if(fi == null)
                {
                    File file = new File(WlAssetsUtil.getAssetsFilePath(WlBufferActivity.this, "mytest.h264"));
                    try {
                        fi = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                byte[] buffer = new byte[1024];
                int buffersize = 0;
                try {
                    buffersize = fi.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(buffersize > 0)
                {
                    return buffer;
                }
                return null;
            }
        });
        wlTextureView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                WlLog.d("initSuccess 开始了");
                wlMedia.prepared();
            }

            @Override
            public void moveX(double value, int move_type) {

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia.exit();
    }
}
