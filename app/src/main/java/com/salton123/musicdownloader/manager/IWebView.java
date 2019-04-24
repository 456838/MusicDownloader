package com.salton123.musicdownloader.manager;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 14:16
 * ModifyTime: 14:16
 * Description:
 */
public interface IWebView {
    boolean canGoBack();

    boolean canGoForward();

    void goBack();

    void goForward();

    void loadUrl(String url);
}
