package com.salton123.adapter.abslistview.base;


import com.salton123.adapter.abslistview.ViewHolder;

public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);

}
