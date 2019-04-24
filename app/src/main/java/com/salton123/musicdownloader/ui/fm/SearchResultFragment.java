package com.salton123.musicdownloader.ui.fm;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.salton123.base.BaseFragment;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridBookmarkItem;
import com.salton123.musicdownloader.view.BookMarkView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 18:01
 * ModifyTime: 18:01
 * Description:
 */
public class SearchResultFragment extends BaseFragment {
    private WebView mWebView;
    private BookMarkView mBookMarkView;
    private FrameLayout flBrowserRoot;

    @Override
    public int getLayout() {
        return R.layout.comp_browser;
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
    }

    @Override
    public void initViewAndData() {
        mWebView = f(R.id.mWebView);
        mBookMarkView = f(R.id.mBookMarkView);
        flBrowserRoot = f(R.id.flBrowserRoot);
        mBookMarkView.setOnItemClickListener((parent, view, position, id) -> {
            GridBookmarkItem item = (GridBookmarkItem) parent.getItemAtPosition(position);

        });
    }

    public void showWebView() {
        show(mWebView);
        hide(mBookMarkView);
    }

    public void showBookmarkView() {
        hide(mWebView);
        show(mBookMarkView);
    }

    public void startSearch(int searchType, String keyword) {

    }
}
