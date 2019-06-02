package com.salton123.musicdownloader.ui.fm;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridMenuItem;
import com.salton123.ui.base.BaseDialogFragment;
import com.salton123.util.ScreenUtils;
import com.salton123.view.adapter.TitleMoreGridAdapter;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/19 14:20
 * ModifyTime: 14:20
 * Description:
 */
public class TitleMorePopupComp extends BaseDialogFragment {
    private static final String TAG = "MenuPopupComp";
    private GridView gvMenu;
    private TitleMoreGridAdapter mMenuGridAdapter;

    @Override
    public int getLayout() {
        return R.layout.comp_title_more_popup;
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog);
    }

    @Override
    public void initViewAndData() {
        gvMenu = f(R.id.gvMenu);
        mMenuGridAdapter = new TitleMoreGridAdapter(activity());
        // gvMenu.setAdapter(mMenuGridAdapter);
        // mMenuGridAdapter.add(new GridMenuItem("扫码", getString(R.string.if_retangel)));
        // mMenuGridAdapter.add(new GridMenuItem("分享", getString(R.string.if_retangel)));
        // mMenuGridAdapter.add(new GridMenuItem("阅读模式", getString(R.string.if_retangel)));
        // mMenuGridAdapter.add(new GridMenuItem("看图模式", getString(R.string.if_retangel)));
        // mMenuGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.TOP;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.y = (int) getResources().getDimension(R.dimen.immersionBarHeight) - ScreenUtils.getStatusHeight(getActivity());
        window.setAttributes(params);
        // window.setWindowAnimations(R.style.slide_popup_ani_down);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setDimAmount(0f);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "[onResume]");
    }
}
