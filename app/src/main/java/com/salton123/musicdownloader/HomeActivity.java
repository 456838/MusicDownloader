package com.salton123.musicdownloader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.salton123.app.crash.ThreadUtils;
import com.salton123.feature.PermissionFeature;
import com.salton123.musicdownloader.view.adapter.SearchResultAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import xyz.yhsj.kmusic.KMusic;
import xyz.yhsj.kmusic.entity.MusicResp;
import xyz.yhsj.kmusic.entity.Song;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class HomeActivity extends BookBaseActivity {
    private EditText etInput;
    private RecyclerView recyclerView;
    private SearchResultAdapter mAdapter;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitleMore:
                ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<MusicResp<List<Song>>>() {
                    @NotNull
                    @Override
                    public MusicResp<List<Song>> doInBackground() {
                        return KMusic.search(etInput.getText().toString().trim());
                    }

                    @Override
                    public void onSuccess(@Nullable MusicResp<List<Song>> result) {
                        String msg = result.getMsg();
                        List<Song> lists = result.getData();
                        etInput.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addMoreData(lists);
                            }
                        });
                    }
                });

                break;
            default:
                break;
        }
    }
}
