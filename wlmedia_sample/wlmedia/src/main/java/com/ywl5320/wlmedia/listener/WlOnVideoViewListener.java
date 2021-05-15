package com.ywl5320.wlmedia.listener;

/**
 * Created by ywl5320 on 2020/4/15
 */
public interface WlOnVideoViewListener {

    /**
     * surface初始化完成回调
     */
    void initSuccess();

    /**
     * surface大小改变
     * @param width
     * @param height
     */
    void onSurfaceChange(int width, int height);


    /**
     * 左右滑动
     * @param value
     * @param move_type
     */
    void moveX(double value, int move_type);

    /**
     * 单击
     */
    void onSingleClick();

    /**
     * 双击
     */
    void onDoubleClick();

    /**
     * 左边上下滑动
     * @param value
     * @param move_type
     */
    void moveLeft(double value, int move_type);

    /**
     * 右边上下滑动
     * @param value
     * @param move_type
     */
    void moveRight(double value, int move_type);

}
