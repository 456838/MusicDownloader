package cn.bmob.sdkdemo.activity.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.activity.user.email.UserEmailActivity
import cn.bmob.sdkdemo.activity.user.normal.UserNormalActivity
import cn.bmob.sdkdemo.activity.user.sms.UserMainActivity
import cn.bmob.sdkdemo.activity.user.third.UserThirdActivity
import com.salton123.ui.base.BaseActivity

/**
 *
 * 用户管理
 * @author zhangchaozhou
 */
class UserActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_normal, R.id.btn_sms, R.id.btn_email, R.id.btn_third)
    }

    override fun getLayout(): Int = R.layout.activity_user

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_normal ->
                //正常用户操作
                startActivity(Intent(this, UserNormalActivity::class.java))
            R.id.btn_sms ->
                //用户的短信操作
                startActivity(Intent(this, UserMainActivity::class.java))
            R.id.btn_email ->
                //用户的邮件操作
                startActivity(Intent(this, UserEmailActivity::class.java))
            R.id.btn_third ->
                //用户的第三方操作
                startActivity(Intent(this, UserThirdActivity::class.java))

            else -> {
            }
        }
    }
}
