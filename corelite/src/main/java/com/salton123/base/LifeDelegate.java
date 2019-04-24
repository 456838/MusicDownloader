package com.salton123.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.salton123.corelite.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/19 10:13
 * ModifyTime: 10:13
 * Description:
 */
public abstract class LifeDelegate {
    public IComponentLife mComponentLife;
    private ViewGroup rootView;

    public LifeDelegate(IComponentLife componentLife) {
        this.mComponentLife = componentLife;
    }

    void onCreate(Bundle saveInstanceState) {
        mComponentLife.initVariable(saveInstanceState);
    }

    View onCreateView() {
        rootView = buildRootView();
        return rootView;
    }

    private ViewGroup buildRootView() {
        View mainContentView = inflater().inflate(mComponentLife.getLayout(), null);
        if (getTitleBar() != null) {
            LinearLayout topLayout = new LinearLayout(activity());
            topLayout.setId(R.id.salton_id_top_layout);
            topLayout.setOrientation(LinearLayout.VERTICAL);
            topLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            LinearLayout titleLayout = new LinearLayout(activity());
            titleLayout.setId(R.id.salton_id_title_layout);
            titleLayout.setOrientation(LinearLayout.VERTICAL);
            titleLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            titleLayout.addView(getTitleBar());
            topLayout.addView(titleLayout);

            FrameLayout contentLayout = new FrameLayout(activity());
            contentLayout.setId(R.id.salton_id_content_layout);
            contentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            contentLayout.addView(mainContentView);
            topLayout.addView(contentLayout);
            return topLayout;
        } else {
            return (ViewGroup) mainContentView;
        }
    }

    View getTitleBar() {
        return mComponentLife.getTitleBar();
    }

    void onViewCreated() {
        mComponentLife.initViewAndData();
        mComponentLife.initListener();
    }

    void log(String msg) {
        Log.i(this.getClass().getSimpleName(), msg);
    }

    void longToast(String toast) {
        Toast.makeText(mComponentLife.activity(), toast, Toast.LENGTH_LONG).show();
    }

    void shortToast(String toast) {
        Toast.makeText(mComponentLife.activity(), toast, Toast.LENGTH_SHORT).show();
    }

    <T extends View> T f(int resId) {
        return (T) rootView.findViewById(resId);
    }

    View getRootView() {
        return rootView;
    }

    LayoutInflater inflater() {
        return LayoutInflater.from(mComponentLife.activity());
    }

    abstract Activity activity();

    public void openActivity(Class<?> clz, Bundle bundle) {
        if (activity() != null) {
            Intent intent = new Intent(activity(), clz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity().startActivity(intent);
        }
    }

    public void openActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        if (activity() != null) {
            Intent intent = new Intent(activity(), clz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity().startActivityForResult(intent, requestCode);
        }
    }

    public void setListener(int... ids) {
        for (int id : ids) {
            f(id).setOnClickListener(mComponentLife);
        }
    }

    public void setListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(mComponentLife);
        }
    }

    public void show(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hide(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
