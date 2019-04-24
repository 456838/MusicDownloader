package com.salton123.musicdownloader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.salton123.musicdownloader.R;
import com.salton123.musicdownloader.bean.GridBookmarkItem;
import com.salton123.utils.MaterialColors;
import com.salton123.view.adapter.BookmarkGridAdapter;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 14:42
 * ModifyTime: 14:42
 * Description:
 */
public class BookMarkView extends FrameLayout {
    private GridView gridView;
    private BookmarkGridAdapter mAdapter;

    public BookMarkView(Context context) {
        super(context);
        initView();
    }

    public BookMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BookMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.comp_book_mark_grid, this);
        gridView = findViewById(R.id.gridView);
        mAdapter = new BookmarkGridAdapter(getContext());
        for (int i = 0; i < 30; i++) {
            mAdapter.add(new GridBookmarkItem("百度", "仅供参考", "https://www.baidu.com/", getResources().getColor(MaterialColors.random())));
        }
        gridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        if (gridView != null) {
            gridView.setOnItemClickListener(listener);
        }
    }
}
