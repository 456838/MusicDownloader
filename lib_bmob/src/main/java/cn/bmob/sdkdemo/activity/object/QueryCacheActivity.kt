package cn.bmob.sdkdemo.activity.`object`

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * Created on 2018/11/26 16:14
 *
 * @author zhangchaozhou
 */
class QueryCacheActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_cache_query, R.id.btn_cache_clear, R.id.btn_cache_clear_all, R.id.btn_cache_set_max_age)
    }

    override fun getLayout(): Int = R.layout.activity_cache
    @SuppressLint("InvalidRUsage")
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_cache_query -> queryCache()
            R.id.btn_cache_clear -> {
            }
            R.id.btn_cache_clear_all -> {
            }
            R.id.btn_cache_set_max_age -> {
            }

            else -> {
            }
        }
    }


    /**
     * 缓存查询
     */
    private fun queryCache() {
        val query = BmobQuery<User>()
        val isCache = query.hasCachedResult(User::class.java)
        if (isCache) {
            // 先从缓存取数据，如果没有的话，再从网络取。
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK)
        } else {
            // 如果没有缓存的话，则先从网络中取
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE)
        }
        query.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_cache_query!!, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_cache_query!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


}
