package com.salton123.musicdownloader.ui.fm;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.salton123.base.BaseDialogFragment;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.manager.BrowserManager;
import com.salton123.util.ScreenUtils;
import com.salton123.view.adapter.BrowserListAdapter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/19 14:20
 * ModifyTime: 14:20
 * Description:
 */
public class BrowserListPopupComp extends BaseDialogFragment {
    private static final String TAG = "BrowserListPopupComp";
    private GridView gvMenu;
    private BrowserListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.comp_browser_list_popup;
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog);
    }

    @Override
    public void initViewAndData() {
        gvMenu = f(R.id.gvMenu);
        mAdapter = new BrowserListAdapter(activity());
        gvMenu.setAdapter(mAdapter);
        mAdapter.addAll(BrowserManager.INSTANCE.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "[onStart]");
        Window window = getDialog().getWindow();
        ScreenUtils.hideNavigationBar(window);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.slide_popup_ani);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "[onResume]");
    }
}
