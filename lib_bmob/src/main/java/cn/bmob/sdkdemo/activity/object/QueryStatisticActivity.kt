package cn.bmob.sdkdemo.activity.`object`

import android.os.Bundle
import android.util.Log
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_query_statistics.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


/**
 *
 * 统计查询
 * @author zhangchaozhou
 */
class QueryStatisticActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        btn_statistics.setOnClickListener {
            try {
                statistics()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun getLayout(): Int = R.layout.activity_query_statistics

    /**
     * TODO 不带groupby的查询结果，统计全部。
     * [{
     * "_avgFault": 1.625,
     * "_avgFoul": 3.75,
     * "_avgScore": 25.75,
     * "_avgSteal": 2,
     * "_count": 79,
     * "_maxFault": 3,
     * "_maxFoul": 6,
     * "_maxScore": 53,
     * "_maxSteal": 4,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 11,
     * "_minSteal": 1,
     * "_sumFault": 13,
     * "_sumFoul": 30,
     * "_sumScore": 206,
     * "_sumSteal": 16
     * }]
     */
    /**
     * TODO 带groupby的查询结果，根据country分组统计。
     * [{
     * "_avgFault": 1.6666666666666667,
     * "_avgFoul": 2.3333333333333335,
     * "_avgScore": 25.666666666666668,
     * "_avgSteal": 1.3333333333333333,
     * "_count": 3,
     * "_maxFault": 2,
     * "_maxFoul": 3,
     * "_maxScore": 53,
     * "_maxSteal": 2,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 12,
     * "_minSteal": 1,
     * "_sumFault": 5,
     * "_sumFoul": 7,
     * "_sumScore": 77,
     * "_sumSteal": 4,
     * "country": "china"
     * }, {
     * "_avgFault": 2,
     * "_avgFoul": 4.5,
     * "_avgScore": 22,
     * "_avgSteal": 2.5,
     * "_count": 2,
     * "_maxFault": 3,
     * "_maxFoul": 5,
     * "_maxScore": 23,
     * "_maxSteal": 3,
     * "_minFault": 1,
     * "_minFoul": 4,
     * "_minScore": 21,
     * "_minSteal": 2,
     * "_sumFault": 4,
     * "_sumFoul": 9,
     * "_sumScore": 44,
     * "_sumSteal": 5,
     * "country": "usa"
     * }, {
     * "_avgFault": 1.3333333333333333,
     * "_avgFoul": 4.666666666666667,
     * "_avgScore": 28.333333333333332,
     * "_avgSteal": 2.3333333333333335,
     * "_count": 3,
     * "_maxFault": 2,
     * "_maxFoul": 6,
     * "_maxScore": 43,
     * "_maxSteal": 4,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 11,
     * "_minSteal": 1,
     * "_sumFault": 4,
     * "_sumFoul": 14,
     * "_sumScore": 85,
     * "_sumSteal": 7,
     * "country": "uk"
     * }, {
     * "_avgFault": null,
     * "_avgFoul": null,
     * "_avgScore": null,
     * "_avgSteal": null,
     * "_count": 71,
     * "_maxFault": null,
     * "_maxFoul": null,
     * "_maxScore": null,
     * "_maxSteal": null,
     * "_minFault": null,
     * "_minFoul": null,
     * "_minScore": null,
     * "_minSteal": null,
     * "_sumFault": 0,
     * "_sumFoul": 0,
     * "_sumScore": 0,
     * "_sumSteal": 0,
     * "country": null
     * }]
     */

    /**
     * TODO 带groupby和having的查询结果
     * [{
     * "_avgFault": 1.3333333333333333,
     * "_avgFoul": 4.666666666666667,
     * "_avgScore": 28.333333333333332,
     * "_avgSteal": 2.3333333333333335,
     * "_count": 3,
     * "_maxFault": 2,
     * "_maxFoul": 6,
     * "_maxScore": 43,
     * "_maxSteal": 4,
     * "_minFault": 1,
     * "_minFoul": 2,
     * "_minScore": 11,
     * "_minSteal": 1,
     * "_sumFault": 4,
     * "_sumFoul": 14,
     * "_sumScore": 85,
     * "_sumSteal": 7,
     * "country": "uk"
     * }]
     */

    /**
     * “group by”从字面意义上理解就是根据“by”指定的规则对数据进行分组，所谓的分组就是将一个“数据集”划分成若干个“小区域”，然后针对若干个“小区域”进行数据处理。
     * where 子句的作用是在对查询结果进行分组前，将不符合where条件的行去掉，即在分组之前过滤数据，where条件中不能包含聚组函数，使用where条件过滤出特定的行。
     * having 子句的作用是筛选满足条件的组，即在分组之后过滤数据，条件中经常包含聚组函数，使用having 条件过滤出特定的组，也可以使用多个分组标准进行分组。
     *
     * @throws JSONException
     */
    @Throws(JSONException::class)
    private fun statistics() {
        val bmobQuery = BmobQuery<User>()
        //总和
        bmobQuery.sum(arrayOf("score", "steal", "foul", "fault"))
        //平均值
        bmobQuery.average(arrayOf("score", "steal", "foul", "fault"))
        //最大值
        bmobQuery.max(arrayOf("score", "steal", "foul", "fault"))
        //最小值
        bmobQuery.min(arrayOf("score", "steal", "foul", "fault"))
        //是否返回所统计的总条数
        bmobQuery.setHasGroupCount(true)
        //根据所给列分组统计
        bmobQuery.groupby(arrayOf("country"))
        //对统计结果进行过滤
        val map = HashMap<String, Any>(1)
        val jsonObject = JSONObject()
        jsonObject.put("\$gt", 28)
        map["_avgScore"] = jsonObject
        bmobQuery.having(map)
        //开始统计查询
        bmobQuery.findStatistics(User::class.java, object : QueryListener<JSONArray>() {
            override fun done(jsonArray: JSONArray, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_statistics!!, "查询成功：" + jsonArray.length(), Snackbar.LENGTH_LONG).show()
                    try {
                        val jsonObject = jsonArray.getJSONObject(0)
                        val sum = jsonObject.getInt("_sumScore")
                        Snackbar.make(btn_statistics!!, "sum：$sum", Snackbar.LENGTH_LONG).show()
                    } catch (e1: JSONException) {
                        e1.printStackTrace()
                    }

                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_statistics!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
