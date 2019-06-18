package cn.bmob.sdkdemo.activity.user.sms

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import com.salton123.bmob.helper.BmobHelper
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_login_password.*

/**
 * Created on 18/9/26 09:39
 * TODO 通过密码登录，只针对已经通过密码注册的用户
 *
 * @author zhangchaozhou
 */
class UserLoginPasswordActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(btn_login)
    }

    override fun getLayout(): Int = R.layout.activity_user_login_password

    override fun onClick(view: View?) {
        when (view) {
            btn_login -> {
                val username = edt_username!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
                    return
                }
                val password = edt_password!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
                    return
                }
                BmobHelper.login(username, password)
                        .subscribe({
                            tv_info!!.append("登录成功：" + it.objectId)
                            startActivity(Intent(this@UserLoginPasswordActivity, UserMainActivity::class.java))
                        }, {
                            tv_info!!.append("登录失败：" + it.message)
                        }, {})
            }
        }
    }
}
