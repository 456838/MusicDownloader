package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_query_regex.*

/**
 * Created on 2018/11/23 17:43
 * TODO 模糊搜索/正则表达式搜索 此功能需要付费才可以正常使用
 *
 * @author zhangchaozhou
 */
class QueryRegexActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_regex, R.id.btn_contains, R.id.btn_starts, R.id.btn_ends)
    }

    override fun getLayout(): Int = R.layout.activity_query_regex

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_regex -> regex()
            R.id.btn_contains -> contains()
            R.id.btn_starts -> starts()
            R.id.btn_ends -> ends()

            else -> {
            }
        }
    }

    private fun ends() {
        val bmobQuery = BmobQuery<User>()
        bmobQuery.addWhereEndsWith("name", "eg")
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_regex!!, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_regex!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun starts() {
        val bmobQuery = BmobQuery<User>()
        bmobQuery.addWhereStartsWith("name", "eg")
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_regex!!, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_regex!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun contains() {
        val bmobQuery = BmobQuery<User>()
        bmobQuery.addWhereContains("name", "eg")
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_regex!!, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_regex!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun regex() {
        val bmobQuery = BmobQuery<User>()
        bmobQuery.addWhereMatches("name", "^[A-Z]\\d")
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_regex!!, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_regex!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
