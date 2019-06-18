package cn.bmob.sdkdemo.activity.user.sms

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_signup_password.*

/**
 * Created on 18/9/26 09:39
 * TODO 通过密码注册
 *
 * @author zhangchaozhou
 */
class UserSignUpPasswordActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_signup)
    }
    override fun getLayout(): Int = R.layout.activity_user_signup_password

    override fun onClick(view: View?) {

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
        val user = User()
        user.username = username
        user.setPassword(password)
        user.signUp(object : SaveListener<User>() {
            override fun done(user: User, e: BmobException?) {
                if (e == null) {
                    tv_info!!.append("注册成功：" + user.objectId)
                    startActivity(Intent(this@UserSignUpPasswordActivity, UserMainActivity::class.java))
                } else {
                    tv_info!!.append("注册失败：" + e.message)
                }
            }
        })
    }
}
