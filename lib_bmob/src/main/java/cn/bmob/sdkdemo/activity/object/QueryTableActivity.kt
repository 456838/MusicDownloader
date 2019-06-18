package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_query_table.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created on 2018/11/23 14:48
 *
 * @author zhangchaozhou
 */
class QueryTableActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_query_table_object, R.id.btn_query_table_array)
    }

    override fun getLayout(): Int = R.layout.activity_query_table


    /**
     * 根据表名查询多条数据
     */
    fun queryArrayByTable() {
        val query = BmobQuery<Any>("_User")
        query.findObjectsByTable(object : QueryListener<JSONArray>() {
            override fun done(ary: JSONArray, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_query_table_array!!, "注册成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_query_table_array!!, "尚未失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })


    }

    /**
     * 根据表名查询单条数据
     */
    fun queryObjectByTable() {
        val query = BmobQuery<Any>("_User")
        query.getObjectByTable("此处替换为objectId", object : QueryListener<JSONObject>() {
            override fun done(jsonObject: JSONObject, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_query_table_object!!, "注册成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_query_table_object!!, "尚未失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_query_table_object -> queryObjectByTable()
            R.id.btn_query_table_array -> queryArrayByTable()

            else -> {
            }
        }
    }
}
