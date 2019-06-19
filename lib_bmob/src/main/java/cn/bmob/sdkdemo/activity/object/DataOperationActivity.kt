package cn.bmob.sdkdemo.activity.`object`

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import com.salton123.ui.biz.BaseTitleActivity

/**
 * Created on 2018/12/5 14:51
 *
 * @author zhangchaozhou
 */
class DataOperationActivity : BaseTitleActivity() {
    override fun initViewAndData() {
        setListener(R.id.btn_single_curd, R.id.btn_multi_crud, R.id.btn_where, R.id.btn_regex, R.id.btn_statistics, R.id.btn_bql, R.id.btn_table, R.id.btn_cache)
    }

    override fun getLayout(): Int = R.layout.activity_data_operation

    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_single_curd -> startActivity(Intent(this, SingleCrudActivity::class.java))
            R.id.btn_multi_crud -> startActivity(Intent(this, MultiCrudActivity::class.java))
            R.id.btn_where -> startActivity(Intent(this, QueryWhereActivity::class.java))
            R.id.btn_regex -> startActivity(Intent(this, QueryRegexActivity::class.java))
            R.id.btn_statistics -> startActivity(Intent(this, QueryStatisticActivity::class.java))
            R.id.btn_bql -> startActivity(Intent(this, QueryBqlActivity::class.java))
            R.id.btn_table -> startActivity(Intent(this, QueryTableActivity::class.java))
            R.id.btn_cache -> startActivity(Intent(this, QueryCacheActivity::class.java))

            else -> {
            }
        }
    }
}
