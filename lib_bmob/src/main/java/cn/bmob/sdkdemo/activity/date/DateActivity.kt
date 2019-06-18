package cn.bmob.sdkdemo.activity.date

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Category
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobDate
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_date.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 2018/11/22 17:55
 *
 * @author zhangchaozhou
 */
class DateActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_equal, R.id.btn_not_equal, R.id.btn_less, R.id.btn_large, R.id.btn_equal_less, R.id.btn_equal_large, R.id.btn_equal_large_equal_less)
    }

    override fun getLayout(): Int = R.layout.activity_date

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_equal -> try {
                equal()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_not_equal -> try {
                notEqual()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_less -> try {
                less()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_large -> try {
                large()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_equal_less -> try {
                lessEqual()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_equal_large -> try {
                largeEqual()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            R.id.btn_equal_large_equal_less -> try {
                duration()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            else -> {
            }
        }
    }

    /**
     * 期间
     */
    @Throws(ParseException::class)
    private fun duration() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val createdAtStart = "2018-11-23 10:29:59"
        val createdAtDateStart = sdf.parse(createdAtStart)
        val bmobCreatedAtDateStart = BmobDate(createdAtDateStart)

        val createdAtEnd = "2018-11-23 10:30:01"
        val createdAtDateEnd = sdf.parse(createdAtEnd)
        val bmobCreatedAtDateEnd = BmobDate(createdAtDateEnd)


        val categoryBmobQueryStart = BmobQuery<Category>()
        categoryBmobQueryStart.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDateStart)
        val categoryBmobQueryEnd = BmobQuery<Category>()
        categoryBmobQueryEnd.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDateEnd)
        val queries = ArrayList<BmobQuery<Category>>()
        queries.add(categoryBmobQueryStart)
        queries.add(categoryBmobQueryStart)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.and(queries)
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
     * 某个时间
     */
    @Throws(ParseException::class)
    private fun equal() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereEqualTo("createdAt", bmobCreatedAtDate)
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
     * 某个时间外
     */
    @Throws(ParseException::class)
    private fun notEqual() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereNotEqualTo("createdAt", bmobCreatedAtDate)
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
     * 某个时间前
     */
    @Throws(ParseException::class)
    private fun less() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate)
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
     * 某个时间及以前
     */
    @Throws(ParseException::class)
    private fun lessEqual() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate)
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
     * 某个时间后
     */
    @Throws(ParseException::class)
    private fun large() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereGreaterThan("createdAt", bmobCreatedAtDate)
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
     * 某个时间及以后
     */
    @Throws(ParseException::class)
    private fun largeEqual() {
        val createdAt = "2018-11-23 10:30:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createdAtDate = sdf.parse(createdAt)
        val bmobCreatedAtDate = BmobDate(createdAtDate)


        val categoryBmobQuery = BmobQuery<Category>()
        categoryBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate)
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
