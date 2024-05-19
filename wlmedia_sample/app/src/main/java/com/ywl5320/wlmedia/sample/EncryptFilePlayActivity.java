package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.enums.WlCompleteType;
import com.ywl5320.wlmedia.enums.WlLoadStatus;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.listener.WlOnMediaInfoListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.widget.WlSurfaceView;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/18
 */
public class EncryptFilePlayActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlPlayer wlPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_encrypt_file);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlPlayer = new WlPlayer();
        wlSurfaceView.setWlPlayer(wlPlayer, "#ff0000");
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
            public byte[] decryptBuffer(byte[] encryptBuffer, long position) {
                if (encryptBuffer == null) {
                    return null;
                }
                for (int i = 0; i < encryptBuffer.length; i++) {
                    encryptBuffer[i] ^= '6';// 先模拟加密
                    encryptBuffer[i] ^= '6';// 再解密后返回
                }
                return encryptBuffer;
            }
        });
    }

    public void onClickPlay(View view) {
        wlPlayer.setSourceType(WlSourceType.WL_SOURCE_ENCRYPT_FILE);
        wlPlayer.setSource(getFilesDir().getAbsolutePath() + "/testvideos/yfx.mp4");
        wlPlayer.prepare();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlPlayer.release();
    }
}
