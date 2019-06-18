package cn.bmob.sdkdemo.activity.user.sms

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import com.salton123.bmob.helper.BmobHelper
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

    @SuppressLint("CheckResult")
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

        BmobHelper.signUp(username, password).subscribe({
            tv_info!!.append("注册成功：" + it.objectId)
            startActivity(Intent(this@UserSignUpPasswordActivity, UserMainActivity::class.java))
        }, {
            tv_info!!.append("注册失败：" + it)
        }, {})
    }
}
