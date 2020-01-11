package com.ywl5320.wlmedia.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.surface.WlTextureView;

import java.io.File;
import java.io.FileInputStream;

import static java.lang.Thread.sleep;

public class WlBufferActivity extends AppCompatActivity {

    private WlTextureView wlTextureView;
    private WlMedia wlMedia;
    private boolean exit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_layout);
        wlTextureView = findViewById(R.id.wltextureveiw);
        wlMedia = WlMedia.getInstance();
        wlMedia.setBufferSource(true, false);
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
            public void onComplete() {
                WlLog.d("complete ");
                WlBufferActivity.this.finish();
            }
        });
        wlTextureView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                new Thread(new Runnable() {
                    long length = 0;
                    @Override
                    public void run() {
                        try {
                            File file = new File(WlAssetsUtil.getAssetsFilePath(WlBufferActivity.this, "mytest.h264"));
                            FileInputStream fi = new FileInputStream(file);
                            byte[] buffer = new byte[1024 * 64];
                            int buffersize = 0;
                            int bufferQueueSize = 0;
                            exit = false;
                            while(true)
                            {
                                if(exit)
                                {
                                    break;
                                }
                                if(wlMedia.isPlay())
                                {
                                    WlLog.d("read thread " + bufferQueueSize);
                                    if(bufferQueueSize < 20)
                                    {
                                        buffersize = fi.read(buffer);
                                        if(buffersize <= 0)
                                        {
                                            WlLog.d("read thread   ==============================================  read buffer exit ...");
                                            wlMedia.putBufferSource(null, -1);
                                            break;
                                        }
                                        bufferQueueSize = wlMedia.putBufferSource(buffer, buffersize);
                                        while(bufferQueueSize < 0)
                                        {
                                            bufferQueueSize = wlMedia.putBufferSource(buffer, buffersize);
                                        }
                                    }
                                    else
                                    {
                                        bufferQueueSize = wlMedia.putBufferSource(null, 0);
                                    }
                                    sleep(10);
                                }
                                else
                                {
                                    WlLog.d("buffer exit");
                                    break;
                                }

                            }
                            wlMedia.stop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                wlMedia.prepared();
            }

            @Override
            public void moveSlide(double value) {

            }

            @Override
            public void movdFinish(double value) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        exit = true;
    }
}
