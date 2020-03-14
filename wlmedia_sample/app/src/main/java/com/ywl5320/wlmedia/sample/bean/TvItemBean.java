package com.ywl5320.wlmedia.sample.bean;

/**
 * Created by wanli on 2020/3/10
 */
public class TvItemBean extends BaseBean{

    private String name;
    private String url;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
