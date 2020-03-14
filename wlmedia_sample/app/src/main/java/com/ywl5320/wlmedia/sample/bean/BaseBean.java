package com.ywl5320.wlmedia.sample.bean;
import java.io.Serializable;

/**
 * Created by ywl on 2016/1/30.
 */
public class BaseBean implements Serializable {

    public static final long serialVersionUID = -316172390920775219L;

    private String resultStr;

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

}
