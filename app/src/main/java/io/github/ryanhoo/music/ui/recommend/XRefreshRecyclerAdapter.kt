package io.github.ryanhoo.music.ui.recommend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter
import com.hazz.kotlinmvp.view.recyclerview.MultipleType
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemClickListener
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemLongClickListener

/**
 * User: newSalton@outlook.com
 * Date: 2018/6/8 上午4:18
 * ModifyTime: 上午4:18
 * Description:
 */
abstract class XRefreshRecyclerAdapter<T>(var context: Context, var layoutId: Int) : BaseRecyclerAdapter<ViewHolder>() {

    override fun getAdapterItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int, isItem: Boolean): ViewHolder {
        if (mTypeSupport != null) {
            //需要多布局
            layoutId = viewType
        }
        //创建view
        val view = mInflater?.inflate(layoutId, parent, false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, isItem: Boolean) {
        bindData(holder, mData[position], position)
        //条目点击事件
        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }

        //长按点击事件
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }
    }

    override fun getViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun getAdapterItemViewType(position: Int): Int {
        return mTypeSupport?.getLayoutId(mData[position], position) ?: super.getAdapterItemViewType(position)
    }

    private var mData: MutableList<T> = mutableListOf()
    private val mInflater by lazy { LayoutInflater.from(context) }
    private var mTypeSupport: MultipleType<T>? = null
    //使用接口回调点击事件
    private var mItemClickListener: OnItemClickListener? = null
    //使用接口回调点击事件
    private var mItemLongClickListener: OnItemLongClickListener? = null

    //需要多布局
    constructor(context: Context, typeSupport: MultipleType<T>) : this(context, -1) {
        this.mTypeSupport = typeSupport
    }

    /**
     * 将必要参数传递出去
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)


    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener
    }


    open fun add(item: T) {
        mData.add(mData.size, item)
    }

    open fun add(position: Int, item: T) {
        mData.add(position, item)
    }

    open fun addAll(collection: MutableList<T>) {
        addAll(mData.size, collection)
    }

    open fun addAllNotify(collection: MutableList<T>) {
        mData.addAll(collection)
        notifyDataSetChanged()
    }

    open fun addAll(position: Int, collection: MutableList<T>) {
        mData.addAll(position, collection)
        notifyItemRangeInserted(position, collection.size)
    }

    open fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    open fun getData(): MutableList<T> {
        return mData
    }
}