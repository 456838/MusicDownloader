package cn.bmob.sdkdemo.activity.table

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.Bmob
import cn.bmob.v3.datatype.BmobTableSchema
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.QueryListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_table_schema.*

/**
 * Created on 2018/11/27 10:37
 *
 * @author zhangchaozhou
 */
class TableActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_table_schema, R.id.btn_all_table_schema)
    }

    override fun getLayout(): Int = R.layout.activity_table_schema

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_table_schema -> getTableSchema("_User")
            R.id.btn_all_table_schema -> getAllTableSchema()
            else -> {
            }
        }
    }


    /**
     * 获取指定表的表结构信息
     *
     * @return void
     * @throws
     * @method getTableSchema
     */
    fun getTableSchema(tableName: String) {

        Bmob.getTableSchema(tableName, object : QueryListener<BmobTableSchema>() {

            override fun done(schema: BmobTableSchema, ex: BmobException?) {
                if (ex == null) {
                    Snackbar.make(btn_table_schema!!, "查询成功：" + schema.className + "-" + schema.fields.toString(), Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", ex.toString())
                    Snackbar.make(btn_table_schema!!, ex.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 获取本应用下的所有表的表结构信息
     *
     * @return void
     * @throws
     * @method getAllTableSchema
     */
    private fun getAllTableSchema() {
        Bmob.getAllTableSchema(object : QueryListListener<BmobTableSchema>() {

            override fun done(schemas: List<BmobTableSchema>, ex: BmobException?) {
                if (ex == null) {
                    Snackbar.make(btn_table_schema!!, "查询成功：" + schemas[0].className + "---" + schemas[0].fields.toString(), Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", ex.toString())
                    Snackbar.make(btn_table_schema!!, ex.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


}
