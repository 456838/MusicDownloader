package com.salton123.musicdownloader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.salton123.app.crash.ThreadUtils;
import com.salton123.feature.PermissionFeature;
import com.salton123.musicdownloader.view.adapter.SearchResultAdapter;
import com.salton123.util.PreferencesUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import xyz.yhsj.kmusic.KMusic;
import xyz.yhsj.kmusic.entity.MusicResp;
import xyz.yhsj.kmusic.entity.Song;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class HomeActivity extends BookBaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private EditText etInput;
    private RecyclerView recyclerView;
    private SearchResultAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private int currentPage = 1;
    private int pageSize = 20;

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
        etInput = findViewById(R.id.etInput);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new SearchResultAdapter(recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        String keyword = PreferencesUtils.getString(activity(), "keyword", "");
        etInput.setText(keyword.trim());
        mRefreshLayout = f(R.id.refreshLayout);
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        getData(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitleMore:
                mAdapter.clear();
                getData(true);
                break;
            default:
                break;
        }
    }

    private void getData(boolean clear) {
        String keyword = etInput.getText().toString().trim();
        PreferencesUtils.putString(activity(), "keyword", keyword);
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<MusicResp<List<Song>>>() {
            @NotNull
            @Override
            public MusicResp<List<Song>> doInBackground() {
                return KMusic.search(keyword, currentPage, pageSize);
            }

            @Override
            public void onSuccess(@Nullable MusicResp<List<Song>> result) {
                List<Song> lists = result.getData();
                etInput.post(() -> {
                            mAdapter.clear();
                            mAdapter.addMoreData(lists);
                            mRefreshLayout.endLoadingMore();
                        }
                );
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        mAdapter.clear();
        getData(true);
        mRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        getData(false);
        mRefreshLayout.endLoadingMore();
        return false;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }
}
