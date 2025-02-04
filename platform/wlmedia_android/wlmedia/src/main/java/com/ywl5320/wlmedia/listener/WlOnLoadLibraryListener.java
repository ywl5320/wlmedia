package com.ywl5320.wlmedia.listener;

/**
 * author : ywl5320
 * e-mail : ywl5320@163.com
 * desc   : wlmedia
 * date   : 2024/8/11
 */
public interface WlOnLoadLibraryListener {
    /**
     * 自定义加载动态库
     *
     * @return
     */
    boolean onLoadedLibrary();
}
