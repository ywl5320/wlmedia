package com.ywl5320.wlmedia.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;

public class WlMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }
    }

    public void play_normal(View view) {
        Intent intent = new Intent(this, WlNormalPlayerActivity.class);
        startActivity(intent);
    }

    public void play_encrypt(View view) {
        Intent intent = new Intent(this, WlEncryptActivity.class);
        startActivity(intent);
    }

    public void play_buffert(View view) {
        Intent intent = new Intent(this, WlBufferActivity.class);
        startActivity(intent);
    }

    public void play_audio(View view) {
        Intent intent = new Intent(this, WlAudioActivity.class);
        startActivity(intent);
    }

    public void get_video_pic(View view) {
        Intent intent = new Intent(this, WlGetVideoPicActivity.class);
        startActivity(intent);
    }

    public void mut_play(View view) {
        Intent intent = new Intent(this, WlMutPlayActivity.class);
        startActivity(intent);
    }

    public void play_live(View view) {
        Intent intent = new Intent(this, WlLiveActivity.class);
        startActivity(intent);
    }
}
