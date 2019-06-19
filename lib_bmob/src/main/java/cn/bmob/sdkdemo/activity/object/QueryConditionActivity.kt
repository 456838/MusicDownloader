package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Category
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.CountListener
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_query.*
import java.util.*

/**
 * 查询数据
 *
 * @author zhangchaozhou
 */
class QueryConditionActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_count, R.id.btn_compound_and, R.id.btn_compound_or)
    }

    override fun getLayout(): Int = R.layout.activity_query

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_count -> count()
            R.id.btn_compound_and -> compoundAnd()
            R.id.btn_compound_or -> compoundOr()
            else -> {
            }
        }
    }


    /**
     * 复合查询：与查询，查询序列大于等于5并且小于等于10的类别
     */
    private fun compoundAnd() {
        val categoryBmobQueryStart = BmobQuery<Category>()
        categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("sequence", 5)
        val categoryBmobQueryEnd = BmobQuery<Category>()
        categoryBmobQueryEnd.addWhereLessThanOrEqualTo("sequence", 10)
        val queries = ArrayList<BmobQuery<Category>>()
        queries.add(categoryBmobQueryStart)
        queries.add(categoryBmobQueryStart)

        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.and(queries)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_count!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_count!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 复合查询：或查询，查询序列小于等于5或者大于等于10的类别
     */
    private fun compoundOr() {
        val categoryBmobQueryStart = BmobQuery<Category>()
        categoryBmobQueryStart.addWhereLessThanOrEqualTo("sequence", 5)
        val categoryBmobQueryEnd = BmobQuery<Category>()
        categoryBmobQueryEnd.addWhereGreaterThanOrEqualTo("sequence", 10)
        val queries = ArrayList<BmobQuery<Category>>()
        queries.add(categoryBmobQueryStart)
        queries.add(categoryBmobQueryStart)

        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.or(queries)
        categoryBmobQuery.findObjects(object : FindListener<Category>() {
            override fun done(`object`: List<Category>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_count!!, "查询成功：" + `object`.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_count!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询结果计数
     */
    private fun count() {
        val bmobQuery = BmobQuery<Category>()
        bmobQuery.count(Category::class.java, object : CountListener() {
            override fun done(count: Int?, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_count!!, "查询成功：" + count!!, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_count!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
