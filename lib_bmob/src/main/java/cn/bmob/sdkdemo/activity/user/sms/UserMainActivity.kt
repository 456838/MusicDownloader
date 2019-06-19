package cn.bmob.sdkdemo.activity.user.sms

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobUser
import com.salton123.ui.biz.BaseTitleActivity

/**
 * Created on 18/9/26 09:52
 * TODO 用户登录后首页
 * @author zhangchaozhou
 */
class UserMainActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_reset_password, R.id.btn_reset_sms, R.id.btn_bind, R.id.btn_unbind, R.id.btn_exit)
    }

    override fun getLayout(): Int = R.layout.activity_user_main

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_reset_password -> startActivity(Intent(this, UserResetPasswordActivity::class.java))
            R.id.btn_reset_sms -> startActivity(Intent(this, UserResetSmsActivity::class.java))
            R.id.btn_bind -> startActivity(Intent(this, UserBindActivity::class.java))
            R.id.btn_unbind -> startActivity(Intent(this, UserUnBindActivity::class.java))
            R.id.btn_exit -> {
                BmobUser.logOut()
                finish()
            }
            else -> {
            }
        }
    }
}
