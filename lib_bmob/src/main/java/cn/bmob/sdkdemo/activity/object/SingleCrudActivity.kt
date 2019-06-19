package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Category
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_crud_single.*

/**
 * 单条增删改查
 *
 * @author zhangchaozhou
 */
class SingleCrudActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_save, R.id.btn_update, R.id.btn_delete, R.id.btn_query)
    }

    override fun getLayout(): Int = R.layout.activity_crud_single
    private var mObjectId: String? = null

    /**
     * 新增一个对象
     */
    private fun save() {
        val category = Category()
        category.name = "football"
        category.desc = "足球"
        category.sequence = 1
        category.save(object : SaveListener<String>() {
            override fun done(objectId: String, e: BmobException?) {
                if (e == null) {
                    mObjectId = objectId
                    Snackbar.make(btn_save!!, "新增成功：" + mObjectId!!, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_save!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 更新一个对象
     */
    private fun update() {
        val category = Category()
        category.sequence = 2
        category.update(mObjectId, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update!!, "更新成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 删除一个对象
     */
    private fun delete() {
        val category = Category()
        category.delete(mObjectId, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_delete!!, "删除成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_delete!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询一个对象
     */
    private fun query() {
        val bmobQuery = BmobQuery<Category>()
        bmobQuery.getObject(mObjectId, object : QueryListener<Category>() {
            override fun done(category: Category, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_query!!, "查询成功：" + category.name, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_query!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> save()
            R.id.btn_update -> update()
            R.id.btn_delete -> delete()
            R.id.btn_query -> query()
            else -> {
            }
        }
    }
}
