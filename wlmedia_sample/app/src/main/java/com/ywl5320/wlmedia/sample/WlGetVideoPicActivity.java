package com.ywl5320.wlmedia.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.ywl5320.wlmedia.WlMediaUtil;
import com.ywl5320.wlmedia.bean.WlVideoImgBean;

public class WlGetVideoPicActivity extends AppCompatActivity {
    private ImageView ivImg;
    private ImageView ivImg2;
    private ImageView ivImg3;
    private AppCompatSeekBar seekBar;
    private boolean durationGetImg = false;

    private WlMediaUtil wlMediaUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getvideopic_layout);
        ivImg = findViewById(R.id.iv_img);
        ivImg2 = findViewById(R.id.iv_img2);
        ivImg3 = findViewById(R.id.iv_img3);
        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double time = 180.0 * i / 100;//模拟播放视频时seek获取图片
                if(durationGetImg)
                {
                    return;
                }
                durationGetImg = true;
                getbitmap(ivImg2, WlAssetsUtil.getAssetsFilePath(WlGetVideoPicActivity.this, "fcrs.1080p.mp4"), time, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getvideopic(View view) {
        getbitmap(ivImg, WlAssetsUtil.getAssetsFilePath(this, "mytest.h264"), 2, true);
        getbitmap(ivImg2, WlAssetsUtil.getAssetsFilePath(this, "fcrs.1080p.mp4"), 12, false);
        getbitmap(ivImg3, WlAssetsUtil.getAssetsFilePath(this, "xjzw_cut.mkv"), 30, false);
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

    private void getbitmap(final ImageView imageView, final String url, final double index, final boolean keyframe)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(wlMediaUtil == null)
                {
                    wlMediaUtil = new WlMediaUtil();
                    wlMediaUtil.setSource(url);
                    wlMediaUtil.init();
                    wlMediaUtil.openCodec();
                }
                WlVideoImgBean wlVideoImgBean = wlMediaUtil.getVideoImg(index, keyframe);
                if(wlVideoImgBean != null)
                {
                    Log.d("ywl5320", url + " : bitmap w:" + wlVideoImgBean.getBitmap().getWidth() + ",h:" + wlVideoImgBean.getBitmap().getHeight());
                    BitmapData bitmapData = new BitmapData(imageView, wlVideoImgBean.getBitmap());
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
            durationGetImg = false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(wlMediaUtil != null)
        {
            wlMediaUtil.release();
        }
    }
}
