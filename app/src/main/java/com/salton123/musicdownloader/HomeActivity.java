package com.salton123.musicdownloader;

import android.os.Bundle;
import android.view.View;

import com.salton123.base.feature.PermissionFeature;
import com.salton123.musicdownloader.ui.fm.SearchResultFragment;
import com.salton123.utils.FragmentUtil;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class HomeActivity extends BookBaseActivity {

    private SearchResultFragment mCurrentSearchResultFragment;
    private int searchType;
    private String keyword;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public View getTitleBar() {
        return inflater().inflate(R.layout.default_search_title, null);
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        addFeature(new PermissionFeature(this));
    }

    @Override
    public void initViewAndData() {
        setListener(R.id.tvTitleMore);
        addBrowserInstance();
    }

    private void addBrowserInstance() {
        mCurrentSearchResultFragment = new SearchResultFragment();
        FragmentUtil.add(getFragmentManager(), mCurrentSearchResultFragment, R.id.flContainer, "SearchResultFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvTitleMore:
                keyword = "";
                mCurrentSearchResultFragment.startSearch(searchType, keyword);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}