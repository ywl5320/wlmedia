package com.ywl5320.wlmedia.bean;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/5/11
 */
public class WlOutRenderBean {
    private int id;
    private int w;
    private int h;
    private int r;

    public WlOutRenderBean(int id, int w, int h, int r) {
        this.id = id;
        this.w = w;
        this.h = h;
        this.r = r;
    }

    public int getId() {
        return id;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getR() {
        return r;
    }
}
