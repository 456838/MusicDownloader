package cn.bmob.sdkdemo.activity.array

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
import kotlinx.android.synthetic.main.activity_array.*
import java.util.*

/**
 * Created on 2018/11/22 17:57
 *
 * @author zhangchaozhou
 */
class ArrayActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_contain, R.id.btn_not_contain, R.id.btn_contain_all)
    }

    override fun getLayout(): Int = R.layout.activity_array

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_contain -> contain()
            R.id.btn_not_contain -> notContain()
            R.id.btn_contain_all -> containAll()
            else -> {
            }
        }
    }


    /**
     * 当一个查询是另一个查询的条件时，称之为子查询。
     * 不包含
     */
    private fun notContain() {
        val userBmobQuery = BmobQuery<User>()
        val alias = arrayOf("A", "B")
        userBmobQuery.addWhereNotContainedIn("alias", Arrays.asList(*alias))
        userBmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_contain, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_contain, e?.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 包含
     */
    private fun contain() {
        val userBmobQuery = BmobQuery<User>()
        val alias = arrayOf("A", "B")
        userBmobQuery.addWhereContainedIn("alias", Arrays.asList(*alias))
        userBmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_contain, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_contain, e?.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 包含所有，查询别名为A和B的用户
     */
    private fun containAll() {
        val userBmobQuery = BmobQuery<User>()
        val alias = arrayOf("A", "B")
        userBmobQuery.addWhereContainsAll("alias", Arrays.asList(*alias))
        userBmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_contain, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_contain, e?.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
