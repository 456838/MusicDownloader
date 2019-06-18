package cn.bmob.sdkdemo.activity.realtime

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Chat
import cn.bmob.v3.BmobRealTimeData
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.ValueEventListener
import com.google.gson.Gson
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_realtime_data.*
import org.json.JSONObject

/**
 * 数据监听是付费功能，请确保您appkey对应的应用已开通数据监听功能
 *
 * @author zhangchaozhou
 */
class RealTimeDataActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_connect_listen, R.id.btn_update_chat)
    }

    override fun getLayout(): Int = R.layout.activity_realtime_data

    private fun startBmobRealTimeData() {

        val bmobRealTimeData = BmobRealTimeData()
        bmobRealTimeData.start(object : ValueEventListener {
            override fun onConnectCompleted(e: Exception?) {
                if (e == null) {
                    tv_info!!.text = "连接情况：" + (if (bmobRealTimeData.isConnected) "已连接" else "未连接") + "\n"
                    if (bmobRealTimeData.isConnected) {
                        //TODO 如果已连接，设置监听动作为：监听Chat表的更新
                        bmobRealTimeData.subTableUpdate("Chat")
                    }
                } else {
                    tv_info!!.text = "连接出错：" + e.message + "\n"
                }
            }

            override fun onDataChange(jsonObject: JSONObject) {
                /**
                 * {"nameValuePairs":{"appKey":"f25fe6dad5bca9d0bb090072ea1e3c65","tableName":"Chat","objectId":"","action":"updateTable",
                 * "data":{"nameValuePairs":{"content":"更新Chat表测试+1537324483401","createdAt":"2018-09-19 10:35:00","name":"RDT","objectId":"d5fffe82e9","updatedAt":"2018-09-19 10:35:00"}}}}
                 */
                val gson = Gson()
                val action = jsonObject.optString("action")
                val jsonString = gson.toJson(jsonObject)
                tv_info!!.text = "更新返回内容是：$jsonString\n"
                tv_info!!.text = "当前更新动作是：$action\n"
                if (action == BmobRealTimeData.ACTION_UPDATETABLE) {
                    //TODO 如果监听表更新
                    val data = jsonObject.optJSONObject("data")
                    tv_info!!.text = "name：" + data.optString("name") + "\n"
                    tv_info!!.text = "content：" + data.optString("content") + "\n"
                    tv_info!!.text = "监听到更新：" + data.optString("name") + "-" + data.optString("content") + "\n"
                }
            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_update_chat -> {
                val chat = Chat()
                chat.name = "RDT"
                chat.content = "更新Chat表测试" + System.currentTimeMillis()
                chat.save(object : SaveListener<String>() {
                    override fun done(objectId: String, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.text = "新增表数据成功：$objectId\n"
                        } else {
                            tv_info!!.text = "新增表数据出错：" + e.errorCode + "-" + e.message + "\n"
                        }
                    }
                })
            }
            R.id.btn_connect_listen -> startBmobRealTimeData()
            else -> {
            }
        }
    }
}
