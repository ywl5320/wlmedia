package com.ywl5320.wlmedia.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ywl5320.wlmedia.sample.bean.BaseBean;

import java.util.List;

/**
 * Created by ywl5320 on 2017/11/3.
 */

public abstract class BaseRecyclerAdapter<T extends BaseBean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    private List<T> datas;
    protected Context context;
    protected LayoutInflater mlayoutInflate;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        if(this.context!=null) {
            mlayoutInflate = LayoutInflater.from(this.context);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onItemCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onItemBindViewHolder(holder, datas.get(position), position);
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     *  子类实现布局方法
     * @param parent
     * @param viewType
     * @return
     */
    public abstract VH onItemCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 子类实现绑定方法
     * @param holder
     * @param data
     * @param position
     */
    public abstract void onItemBindViewHolder(VH holder, T data, int position);

    public interface OnItemClickListener
    {
        void onItemClickListener(BaseBean data, int position);

    }

    public interface OnItemLongClickListener
    {
        void onItemLongClickListener(BaseBean data, int position);
    }

    public List<T> getDatas() {
        return datas;
    }

}
