package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnErrorListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.sample.adapter.BaseRecyclerAdapter;
import com.ywl5320.wlmedia.sample.adapter.WlVideoAdapter;
import com.ywl5320.wlmedia.sample.bean.BaseBean;
import com.ywl5320.wlmedia.sample.bean.TvItemBean;
import com.ywl5320.wlmedia.sample.http.HttpUtil;
import com.ywl5320.wlmedia.sample.util.FormatUtil;
import com.ywl5320.wlmedia.sample.util.GsonFormatUtil;
import com.ywl5320.wlmedia.surface.WlSurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单测试页面
 *
 *  Created by ywl5320 on 2020/03/14
 *
 */
public class WlLiveActivity extends AppCompatActivity {


    private RecyclerView rvMenu;
    private List<TvItemBean> datas;
    private WlVideoAdapter videoAdapter;
    private String host = "http://ivi.bupt.edu.cn";

    private WlSurfaceView wlSurfaceView;
    private WlMedia wlMedia;

    private View vShow;
    private View vHide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_live_layout);

        rvMenu = findViewById(R.id.rv_menu);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        vShow = findViewById(R.id.v_show);
        vHide = findViewById(R.id.v_hide);
        setAdapter();
        wlMedia = new WlMedia();
        wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
        wlMedia.setOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int code, String msg) {
            }
        });
        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete(WlComplete type) {
            }
        });
        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                getHomeData(host);
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

        vHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vHide.setVisibility(View.GONE);
                rvMenu.setVisibility(View.GONE);
                vShow.setVisibility(View.VISIBLE);
            }
        });
        vShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vHide.setVisibility(View.VISIBLE);
                rvMenu.setVisibility(View.VISIBLE);
                vShow.setVisibility(View.GONE);
            }
        });

    }

    private void setAdapter()
    {
        datas = new ArrayList<>();
        videoAdapter = new WlVideoAdapter(this, datas);
        videoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(BaseBean data, int position) {
                TvItemBean tvTypeBean = (TvItemBean) data;
                if(tvTypeBean.getStatus() == 0)
                {
                    wlMedia.setSource(tvTypeBean.getUrl());
                    wlMedia.next();
                }

            }
        });
        setRecyclerViewGridManager(rvMenu, RecyclerView.VERTICAL, 2);
        rvMenu.setAdapter(videoAdapter);
    }

    /**
     * grid类型的列表
     * @param recyclerView
     * @param orientation 布局方向
     * @param column 列表数
     */
    public void setRecyclerViewGridManager(RecyclerView recyclerView, int orientation, int column) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, column);
        gridLayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void getHomeData(final String host)
    {
        HttpUtil.getInstance().doHttpGet(host, "utf-8", "", new HttpUtil.HttpResultListener() {
            @Override
            public void onSuccess(String result) {
                String homeJson = FormatUtil.formatIPTV(result, host);
                List<TvItemBean> t = (List<TvItemBean>) GsonFormatUtil.formatJson(homeJson, new TypeToken<List<TvItemBean>>(){}.getType());
                datas.clear();
                if(t != null && t.size() > 0)
                {
                    datas.addAll(t);
                    wlMedia.setSource(datas.get(0).getUrl());
                    wlMedia.next();
                }
                videoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia.exit();
    }
}
