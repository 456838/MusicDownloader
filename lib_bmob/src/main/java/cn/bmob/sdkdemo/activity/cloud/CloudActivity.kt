package cn.bmob.sdkdemo.activity.cloud

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.AsyncCustomEndpoints
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.CloudCodeListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cloud.*
import org.json.JSONObject

/**
 * Created on 2018/11/27 11:05
 * 云端代码
 *
 * @author zhangchaozhou
 */
class CloudActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_cloud, R.id.btn_cloud_params)
    }

    override fun getLayout(): Int = R.layout.activity_cloud

    /**
     * 云端代码，不带参数
     */
    private fun cloudCode() {
        val ace = AsyncCustomEndpoints()
        //不带请求参数的云端代码
        ace.callEndpoint("method", object : CloudCodeListener() {

            override fun done(`object`: Any, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_cloud!!, "执行成功：$`object`", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_cloud!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 云端代码，带参数
     */
    private fun cloudCodeParams() {
        //带请求的云端代码
        val ace = AsyncCustomEndpoints()
        val params = JSONObject()
        ace.callEndpoint("method", params, object : CloudCodeListener() {

            override fun done(`object`: Any, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_cloud_params!!, "执行成功：$`object`", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_cloud_params!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cloud -> cloudCode()
            R.id.btn_cloud_params -> cloudCodeParams()
            else -> {
            }
        }
    }
}
