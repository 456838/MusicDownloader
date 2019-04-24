package com.salton123.musicdownloader.ui.fm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.salton123.base.BaseFragment;
import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridBookmarkItem;
import com.salton123.util.EventBusUtil;
import com.salton123.utils.MaterialColors;
import com.salton123.view.adapter.BookmarkGridAdapter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 15:44
 * ModifyTime: 15:44
 * Description:
 */
public class BookMarkGridFragment extends BaseFragment
        implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private BookmarkGridAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.comp_book_mark_grid;
    }

    @Override
    public void initVariable(Bundle savedInstanceState) {
        mAdapter = new BookmarkGridAdapter(activity());
        for (int i = 0; i < 30; i++) {
            mAdapter.add(new GridBookmarkItem("百度", "仅供参考", "https://www.baidu.com/", getResources().getColor(MaterialColors.random())));
        }
    }

    @Override
    public void initViewAndData() {
        gridView = f(R.id.gridView);
        gridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventBusUtil.post(mAdapter.getItem(position));
    }
}
