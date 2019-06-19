package cn.bmob.sdkdemo.activity.security

import android.os.Bundle
import android.util.Log
import cn.bmob.sdkdemo.R
import cn.bmob.v3.util.AppUtils
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_sign_verify.*

/**
 * Created on 2018/11/27 10:34
 *
 * @author zhangchaozhou
 */
class SignVerifyActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        btn_get_sign.setOnClickListener {
            val sign = AppUtils.getSignature(this)
            Log.e("sign", sign)
            Snackbar.make(btn_get_sign!!, sign, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun getLayout(): Int = R.layout.activity_sign_verify

}
