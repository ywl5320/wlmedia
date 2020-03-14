package com.ywl5320.wlmedia.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ywl5320.wlmedia.sample.R;
import com.ywl5320.wlmedia.sample.bean.TvItemBean;

import java.util.List;


/**
 * Created by wanli on 2018/8/23
 */
public class WlVideoAdapter extends BaseRecyclerAdapter<TvItemBean, WlVideoAdapter.ViewHolder> {

    public WlVideoAdapter(Context context, List<TvItemBean> datas) {
        super(context, datas);
    }

    @Override
    public ViewHolder onItemCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflate.inflate(R.layout.item_video_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onItemBindViewHolder(ViewHolder holder, final TvItemBean data, final int position) {

        if(data != null)
        {
            holder.tvName.setText(data.getName());
            holder.lyRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getOnItemClickListener() != null)
                    {
                        getOnItemClickListener().onItemClickListener(data, position);
                    }
                }
            });
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private LinearLayout lyRoot;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            lyRoot = view.findViewById(R.id.ly_root);
        }
    }
}
