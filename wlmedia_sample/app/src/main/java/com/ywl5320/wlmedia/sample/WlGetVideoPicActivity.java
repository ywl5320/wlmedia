package com.ywl5320.wlmedia.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMediaUtil;

public class WlGetVideoPicActivity extends AppCompatActivity {
    private ImageView ivImg;
    private ImageView ivImg2;
    private ImageView ivImg3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getvideopic_layout);
        ivImg = findViewById(R.id.iv_img);
        ivImg2 = findViewById(R.id.iv_img2);
        ivImg3 = findViewById(R.id.iv_img3);
    }

    public void getvideopic(View view) {
        getbitmap(ivImg, WlAssetsUtil.getAssetsFilePath(this, "mytest.h264"), 10);
        getbitmap(ivImg2, WlAssetsUtil.getAssetsFilePath(this, "fcrs.1080p.mp4"), 1);
        getbitmap(ivImg3, WlAssetsUtil.getAssetsFilePath(this, "xjzw_cut.mkv"), 1);
    }

    class BitmapData
    {
        ImageView imageView;
        Bitmap bitmap;

        public BitmapData(ImageView imageView, Bitmap bitmap) {
            this.imageView = imageView;
            this.bitmap = bitmap;
        }
    }

    private void getbitmap(final ImageView imageView, final String url, final int index)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WlMediaUtil wlMediaUtil = new WlMediaUtil();
                Bitmap bitmap = wlMediaUtil.getVideoPic(url, index);
                if(bitmap != null)
                {
                    Log.d("ywl5320", url + " : bitmap w:" + bitmap.getWidth() + ",h:" + bitmap.getHeight());
                    BitmapData bitmapData = new BitmapData(imageView, bitmap);
                    Message message = Message.obtain();
                    message.obj = bitmapData;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BitmapData bitmapData = (BitmapData) msg.obj;
            bitmapData.imageView.setImageBitmap(bitmapData.bitmap);
        }
    };
}
