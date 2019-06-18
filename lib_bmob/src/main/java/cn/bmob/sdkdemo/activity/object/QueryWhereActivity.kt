package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Category
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_where.*

/**
 * Created on 2018/11/22 17:20
 *
 * @author zhangchaozhou
 */
class QueryWhereActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_equal, R.id.btn_not_equal, R.id.btn_less, R.id.btn_large, R.id.btn_equal_less, R.id.btn_equal_large)
    }

    override fun getLayout(): Int = R.layout.activity_where
    
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_equal -> equal()
            R.id.btn_not_equal -> notEqual()
            R.id.btn_less -> less()
            R.id.btn_large -> large()
            R.id.btn_equal_less -> lessEqual()
            R.id.btn_equal_large -> largeEqual()
            else -> {
            }
        }
    }

    /**
     * name为football的类别
     */
    private fun equal() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereEqualTo("name", "football")
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * name不为football的类别
     */
    private fun notEqual() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereNotEqualTo("name", "football")
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * sequence小于10的类别
     */
    private fun less() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereLessThan("sequence", 10)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * sequence小于等于10的类别
     */
    private fun lessEqual() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereLessThanOrEqualTo("sequence", 10)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * sequence大于10的类别
     */
    private fun large() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereGreaterThan("sequence", 10)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * sequence大于等于10的类别
     */
    private fun largeEqual() {
        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("sequence", 10)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_equal!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_equal!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

}
