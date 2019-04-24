package com.salton123.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.salton123.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/1/19 10:11
 * Time: 10:11
 * Description:
 * Update:增加泛型支持
 */

public abstract class AdapterBase<T> extends BaseAdapter {
    private List<T> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int layoutId;

    public AdapterBase(Context pContext, int pLayoutId) {
        mContext = pContext;
        this.layoutId = pLayoutId;
        mList = new ArrayList();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getList() {
        return mList;
    }

    public void addAll(List<T> p_List) {
        getList().clear();
        getList().addAll(p_List);
        notifyDataSetChanged();
    }

    public void addToList(List<T> p_List) {
        getList().addAll(p_List);
        notifyDataSetChanged();
    }

    public void addNotify(T p_Object) {
        getList().add(p_Object);
        notifyDataSetChanged();
    }

    public void add(T p_Object) {
        getList().add(p_Object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int pPosition) {
        return mList.get(pPosition);
    }

    @Override
    public long getItemId(int pPosition) {
        return pPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = mLayoutInflater.inflate(layoutId, parent,
                    false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
