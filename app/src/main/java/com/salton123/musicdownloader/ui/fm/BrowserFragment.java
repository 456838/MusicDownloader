package com.salton123.musicdownloader.ui.fm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.salton123.base.BaseFragment;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridBookmarkItem;
import com.salton123.musicdownloader.manager.BrowserEntity;
import com.salton123.musicdownloader.manager.BrowserManager;
import com.salton123.musicdownloader.manager.IWebView;
import com.salton123.musicdownloader.view.BookMarkView;

import org.xutils.common.task.SimpleTask;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 18:01
 * ModifyTime: 18:01
 * Description:
 */
public class BrowserFragment extends BaseFragment implements IWebView {
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
            loadUrl(item.url);
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

    @Override
    public boolean canGoBack() {
        if (mWebView != null) {
            return mWebView.canGoBack();
        }
        return false;
    }

    @Override
    public boolean canGoForward() {
        if (mWebView != null) {
            return mWebView.canGoForward();
        }
        return false;
    }

    @Override
    public void goBack() {
        if (canGoBack()) {
            if (mWebView != null) {
                mWebView.goBack();
            }
        } else {
            showBookmarkView();
        }

    }

    @Override
    public void goForward() {
        if (!isVisible(mWebView)) {
            showWebView();
        } else {
            if (canGoForward()) {
                if (mWebView != null) {
                    mWebView.goForward();
                }
            }
        }
    }

    @Override
    public void loadUrl(String url) {
        showWebView();
        if (mWebView != null) {
            mWebView.loadUrl(url);
        }
    }

    public boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onResume() {
        super.onResume();
        createPreviewSnap();
    }

    @Override
    public void onPause() {
        super.onPause();
        createPreviewSnap();
    }

    private Bitmap createThumbnailImage(WebView webView) {
        Bitmap bitmap = Bitmap.createBitmap(flBrowserRoot.getWidth(), flBrowserRoot.getHeight(), Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(bitmap);
        webView.draw(localCanvas);
        return bitmap;
    }

    public void createPreviewSnap() {

        x.task().start(new SimpleTask<Object>() {
            @Override
            protected Object doBackground() throws Throwable {
                Bitmap bitmap;
                if (isVisible(mWebView)) {
                    bitmap = createThumbnailImage(mWebView);
                } else {
                    mBookMarkView.buildDrawingCache(false);
                    bitmap = mBookMarkView.getDrawingCache();
                }
                if (bitmap != null) {
                    FileOutputStream fos;
                    try {
                        File root = Environment.getExternalStorageDirectory();
                        File file = new File(root, BrowserFragment.this.hashCode() + ".jpg");
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                        BrowserEntity entity = BrowserEntity.newInstance();
                        entity.fragment = BrowserFragment.this;
                        entity.previewPicPath = file.getAbsolutePath();
                        BrowserManager.INSTANCE.update(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}
