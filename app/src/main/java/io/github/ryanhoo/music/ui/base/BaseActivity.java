package io.github.ryanhoo.music.ui.base;

import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;

import com.salton123.base.BaseSupportActivity;

import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.utils.GradientUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/15/16
 * Time: 8:12 PM
 * Desc: BaseActivity
 */
public abstract class BaseActivity extends BaseSupportActivity {

    private CompositeDisposable mSubscriptions;

    // @Override
    // protected void attachBaseContext(Context newBase) {
    //     super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    // }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // https://crazygui.wordpress.com/2010/09/05/high-quality-radial-gradient-in-android/
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        // int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        Window window = getWindow();
        GradientDrawable gradientBackgroundDrawable = GradientUtils.create(
                ContextCompat.getColor(this, R.color.mp_theme_dark_blue_gradientColor),
                ContextCompat.getColor(this, R.color.mp_theme_dark_blue_background),
                screenHeight / 2, // (int) Math.hypot(screenWidth / 2, screenHeight / 2),
                0.5f,
                0.5f
        );
        window.setBackgroundDrawable(gradientBackgroundDrawable);
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSubscription(subscribeEvents());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * An easy way to set up non-home(no back button on the toolbar) activity to enable
     * go back action.
     *
     * @param toolbar The toolbar with go back button
     * @return ActionBar
     */
    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        return actionBar;
    }

    protected void addSubscription(Disposable subscription) {
        if (subscription == null) {
            return;
        }
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeDisposable();
        }
        mSubscriptions.add(subscription);
    }

    protected Disposable subscribeEvents() {
        return null;
    }
}
