package cn.bmob.sdkdemo.activity.other

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import cn.bmob.sdkdemo.R
import cn.bmob.v3.Bmob
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_other_function.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 2018/11/16 11:37
 *
 * @author zhangchaozhou
 */
class OtherFunctionActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_reset_domain, R.id.btn_overseas_speedup, R.id.btn_server_time)
    }

    override fun getLayout(): Int = R.layout.activity_other_function
    internal var mBtnServerTime: AppCompatButton? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset_domain -> Snackbar.make(btn_reset_domain!!, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show()
            R.id.btn_overseas_speedup -> Snackbar.make(btn_reset_domain!!, "见SDK初始化前操作", Snackbar.LENGTH_LONG).show()
            R.id.btn_server_time -> getServerTime()
            else -> {
            }
        }
    }


    /**
     * 获取服务器时间
     */
    private fun getServerTime() {
        Bmob.getServerTime(object : QueryListener<Long>() {
            override fun done(time: Long?, e: BmobException?) {
                if (e == null) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val times = formatter.format(Date(time!! * 1000L))
                    Snackbar.make(mBtnServerTime!!, "当前服务器时间为：$times", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(mBtnServerTime!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
