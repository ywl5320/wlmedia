package com.ywl5320.wlmedia.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMediaUtil;
import com.ywl5320.wlmedia.bean.WlMediaInfoBean;
import com.ywl5320.wlmedia.log.WlLog;

public class WlMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }
    }

    public void get_test(View view) {
        WlMediaUtil wlMediaUtil = new WlMediaUtil();
        wlMediaUtil.setSource("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4");
        int ret = wlMediaUtil.openSource();
        if(ret == 0)
        {
            WlMediaInfoBean[] avInfos = wlMediaUtil.getMediaInfo();
            StringBuffer stringBuffer = new StringBuffer("");
            if(avInfos != null)
            {
                for(WlMediaInfoBean wlMediaInfoBean : avInfos)
                {
                    stringBuffer.append(wlMediaInfoBean.toString());
                    stringBuffer.append("\n\n");
                }
                Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_LONG).show();
            }
        }
        wlMediaUtil.release();
    }

    public void custom_view(View view) {
        Intent intent = new Intent(this, WlCustomViewActivity.class);
        startActivity(intent);
    }

    public void play_normal(View view) {
        Intent intent = new Intent(this, WlNormalActivity.class);
        startActivity(intent);
    }

    public void play_video(View view) {
        Intent intent = new Intent(this, WlVideoPlayActivity.class);
        startActivity(intent);
    }
}
