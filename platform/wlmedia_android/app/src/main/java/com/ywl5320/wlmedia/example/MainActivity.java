package com.ywl5320.wlmedia.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ywl5320.wlmedia.WlPlayer;
import com.ywl5320.wlmedia.log.WlLog;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String videoPaths = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        setContentView(R.layout.activity_main);
        WlLog.setDebug(true);
        WlLog.d("WlPlayer version is :" + WlPlayer.getVersion());
        videoPaths = getFilesDir().getAbsolutePath();
        if (!isFileExists()) {
            copyFiles();
        }
    }

    public void onClickNormal(View view) {
        startActivity(new Intent(this, NormalPlayActivity.class));
    }

    public void onClickAudio(View view) {
        startActivity(new Intent(this, AudioPlayActivity.class));
    }

    public void onClickAlphaVideo(View view) {
        startActivity(new Intent(this, AlphaVideoActivity.class));
    }

    public void onClickMultiSurface(View view) {
        startActivity(new Intent(this, MultiSurfaceActivity.class));
    }

    public void onClickMultiPlayer(View view) {
        startActivity(new Intent(this, MultiPlayerActivity.class));
    }

    public void onClickEncrypt(View view) {
        startActivity(new Intent(this, BufferDataPlayActivity.class));
    }

    public void onClickPcmData(View view) {
        startActivity(new Intent(this, AudioPcmDataActivity.class));
    }

    public void onClickByteData(View view) {
        startActivity(new Intent(this, EncryptFilePlayActivity.class));
    }

    public void onClickFirstFrame(View view) {
        startActivity(new Intent(this, ShowFirstFrameActivity.class));
    }

    public void onClickWlMediaUtil(View view) {
        startActivity(new Intent(this, WlMediaUtilActivity.class));
    }

    public void onClickCustomView(View view) {
        startActivity(new Intent(this, WlCustomViewActivity.class));
    }

    private void copyFiles() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("测试视频拷贝中");
        progressDialog.setCancelable(true);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File fout = new File(videoPaths + "/testvideos");
                if (!fout.exists()) {
                    fout.mkdirs();
                }
                WlLog.d("copy file to sdcard:" + fout.getAbsolutePath());
                boolean success = Util.copyAssetsToDst(MainActivity.this, "testvideos", fout.getAbsolutePath());
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private boolean isFileExists() {
        String files[] = new String[]{
                videoPaths + "/testvideos/alpha_left5.mp4",
                videoPaths + "/testvideos/big_buck_bunny_cut.mp4",
                videoPaths + "/testvideos/fhcq-whcyygyd.mp3",
                videoPaths + "/testvideos/huoying_cut.mkv",
                videoPaths + "/testvideos/mydream.m4a"
        };
        return Util.isFilesExists(files);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressDialog.hide();
        }
    };

    @Override
    public void onBackPressed() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.hide();
                return;
            }
        }
        super.onBackPressed();
    }
}