package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Category
import cn.bmob.v3.BmobBatch
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BatchResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_crud_multi.*
import java.util.*

/**
 * 批量增删改查
 *
 * @author zhangchaozhou
 */
class MultiCrudActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_crud_multi
    }

    override fun initVariable(savedInstanceState: Bundle) {

    }

    override fun initViewAndData() {
        setListener(R.id.btn_save, R.id.btn_query, R.id.btn_update, R.id.btn_delete, R.id.btn_save_update_delete)
    }

    /**
     * 新增多条数据
     */
    private fun save() {
        val categories = ArrayList<BmobObject>()
        for (i in 0..2) {
            val category = Category()
            category.name = "category$i"
            category.desc = "类别$i"
            category.sequence = i
            categories.add(category)
        }
        BmobBatch().insertBatch(categories).doBatch(object : QueryListListener<BatchResult>() {

            override fun done(results: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in results.indices) {
                        val result = results[i]
                        val ex = result.error
                        if (ex == null) {
                            Snackbar.make(btn_save!!, "第" + i + "个数据批量添加成功：" + result.createdAt + "," + result.objectId + "," + result.updatedAt, Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(btn_save!!, "第" + i + "个数据批量添加失败：" + ex.message + "," + ex.errorCode, Snackbar.LENGTH_LONG).show()

                        }
                    }
                } else {
                    Snackbar.make(btn_save!!, "失败：" + e.message + "," + e.errorCode, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 更新多条数据
     */
    private fun update() {

        val categories = ArrayList<BmobObject>()

        val category = Category()
        category.objectId = "此处填写对应的需要修改数据的objectId"
        category.name = "name" + System.currentTimeMillis()
        category.desc = "类别" + System.currentTimeMillis()

        val category1 = Category()
        category1.objectId = "此处填写对应的需要修改数据的objectId"
        category1.name = "name" + System.currentTimeMillis()
        category1.desc = "类别" + System.currentTimeMillis()

        val category2 = Category()
        category2.objectId = "此处填写对应的需要修改数据的objectId"
        category2.name = "name" + System.currentTimeMillis()
        category2.desc = "类别" + System.currentTimeMillis()

        categories.add(category)
        categories.add(category1)
        categories.add(category2)

        BmobBatch().updateBatch(categories).doBatch(object : QueryListListener<BatchResult>() {

            override fun done(results: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in results.indices) {
                        val result = results[i]
                        val ex = result.error
                        if (ex == null) {
                            Snackbar.make(btn_update!!, "第" + i + "个数据批量更新成功：" + result.createdAt + "," + result.objectId + "," + result.updatedAt, Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(btn_update!!, "第" + i + "个数据批量更新失败：" + ex.message + "," + ex.errorCode, Snackbar.LENGTH_LONG).show()

                        }
                    }
                } else {
                    Snackbar.make(btn_update!!, "失败：" + e.message + "," + e.errorCode, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 删除多条数据
     */
    private fun delete() {
        val categories = ArrayList<BmobObject>()

        val category = Category()
        category.objectId = "此处填写对应的需要删除数据的objectId"

        val category1 = Category()
        category1.objectId = "此处填写对应的需要删除数据的objectId"

        val category2 = Category()
        category2.objectId = "此处填写对应的需要删除数据的objectId"

        categories.add(category)
        categories.add(category1)
        categories.add(category2)

        BmobBatch().deleteBatch(categories).doBatch(object : QueryListListener<BatchResult>() {

            override fun done(results: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in results.indices) {
                        val result = results[i]
                        val ex = result.error
                        if (ex == null) {
                            Snackbar.make(btn_delete!!, "第" + i + "个数据批量删除成功：" + result.createdAt + "," + result.objectId + "," + result.updatedAt, Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(btn_delete!!, "第" + i + "个数据批量删除失败：" + ex.message + "," + ex.errorCode, Snackbar.LENGTH_LONG).show()

                        }
                    }
                } else {
                    Snackbar.make(btn_delete!!, "失败：" + e.message + "," + e.errorCode, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询多条数据
     */
    private fun query() {
        val bmobQuery = BmobQuery<Category>()
        bmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(categories: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_query!!, "查询成功：" + categories.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_query!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_save -> save()
            R.id.btn_query -> query()
            R.id.btn_update -> update()
            R.id.btn_delete -> delete()
            R.id.btn_save_update_delete -> saveUpdateDelete()
            else -> {
            }
        }
    }


    /**
     * 同时新增、更新、删除多条数据
     */
    private fun saveUpdateDelete() {
        val batch = BmobBatch()

        //批量添加
        val categoriesSave = ArrayList<BmobObject>()
        for (i in 0..2) {
            val category = Category()
            category.name = "category$i"
            category.desc = "类别$i"
            category.sequence = i
            categoriesSave.add(category)
        }


        //批量更新
        val categoriesUpdate = ArrayList<BmobObject>()
        val categoryUpdate = Category()
        categoryUpdate.objectId = "此处填写对应的需要修改数据的objectId"
        categoryUpdate.name = "name" + System.currentTimeMillis()
        categoryUpdate.desc = "类别" + System.currentTimeMillis()
        val categoryUpdate1 = Category()
        categoryUpdate1.objectId = "此处填写对应的需要修改数据的objectId"
        categoryUpdate1.name = "name" + System.currentTimeMillis()
        categoryUpdate1.desc = "类别" + System.currentTimeMillis()
        val categoryUpdate2 = Category()
        categoryUpdate2.objectId = "此处填写对应的需要修改数据的objectId"
        categoryUpdate2.name = "name" + System.currentTimeMillis()
        categoryUpdate2.desc = "类别" + System.currentTimeMillis()
        categoriesUpdate.add(categoryUpdate)
        categoriesUpdate.add(categoryUpdate1)
        categoriesUpdate.add(categoryUpdate2)


        //批量删除
        val categoriesDelete = ArrayList<BmobObject>()
        val categoryDelete = Category()
        categoryDelete.objectId = "此处填写对应的需要删除数据的objectId"
        val categoryDelete1 = Category()
        categoryDelete1.objectId = "此处填写对应的需要删除数据的objectId"
        val categoryDelete2 = Category()
        categoryDelete2.objectId = "此处填写对应的需要删除数据的objectId"
        categoriesDelete.add(categoryDelete)
        categoriesDelete.add(categoryDelete1)
        categoriesDelete.add(categoryDelete2)


        //执行批量操作
        batch.insertBatch(categoriesSave)
        batch.updateBatch(categoriesUpdate)
        batch.deleteBatch(categoriesDelete)
        batch.doBatch(object : QueryListListener<BatchResult>() {

            override fun done(results: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    //返回结果的results和上面提交的顺序是一样的，请一一对应
                    for (i in results.indices) {
                        val result = results[i]
                        val ex = result.error
                        //只有批量添加才返回objectId
                        if (ex == null) {
                            Snackbar.make(btn_save_update_delete!!, "第" + i + "个数据批量操作成功：" + result.createdAt + "," + result.objectId + "," + result.updatedAt, Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(btn_save_update_delete!!, "第" + i + "个数据批量操作失败：" + ex.message + "," + ex.errorCode, Snackbar.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Snackbar.make(btn_save_update_delete!!, "失败：" + e.message + "," + e.errorCode, Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }


}
