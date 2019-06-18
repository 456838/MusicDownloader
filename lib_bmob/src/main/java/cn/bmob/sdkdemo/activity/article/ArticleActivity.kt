package cn.bmob.sdkdemo.activity.article

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobArticle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_article.*

/**
 * Created on 2018/11/27 10:25
 *
 * @author zhangchaozhou
 */
class ArticleActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(btn_query_article)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            btn_query_article -> {
                queryArticle()
            }
        }
    }

    override fun getLayout(): Int = R.layout.activity_article

    /**
     * 查询图文消息
     */
    private fun queryArticle() {
        val bmobArticleBmobQuery = BmobQuery<BmobArticle>()
        bmobArticleBmobQuery.findObjects(object : FindListener<BmobArticle>() {
            override fun done(`object`: List<BmobArticle>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_query_article, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_query_article, "查询失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
