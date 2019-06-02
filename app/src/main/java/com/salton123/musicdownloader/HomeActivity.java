package com.salton123.musicdownloader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.salton123.feature.PermissionFeature;

import xyz.yhsj.kmusic.KMusic;


/**
 * User: newSalton@outlook.com
 * Date: 2019/2/16 18:18
 * ModifyTime: 18:18
 * Description:
 */
public class HomeActivity extends BookBaseActivity {
    private EditText etInput;
    private ListView lvSongSearch;

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
        lvSongSearch = findViewById(R.id.lvSongSearch);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitleMore:
                KMusic.search()
                break;
            default:
                break;
        }
    }
}
