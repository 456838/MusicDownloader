package cn.bmob.sdkdemo.activity.user.sms

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_signup_password_and_sms.*

/**
 * Created on 18/9/25 16:23
 * TODO 通过密码注册并通过短信验证绑定手机号码
 * @author zhangchaozhou
 */
class UserSignUpPasswordAndSmsActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_send, R.id.btn_signup)
    }

    override fun getLayout(): Int = R.layout.activity_user_signup_password_and_sms

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_send -> {
                val phone = edt_phone!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show()
                    return
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobSMS.requestSMSCode(phone, "DataSDK", object : QueryListener<Int>() {
                    override fun done(smsId: Int?, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("发送验证码成功，短信ID：$smsId\n")
                        } else {
                            tv_info!!.append("发送验证码失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })
            }
            R.id.btn_signup -> {
                val username = edt_username!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
                    return
                }
                val nickname = edt_nickname!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show()
                    return
                }
                val password = edt_password!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
                    return
                }
                val phone = edt_phone!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show()
                    return
                }
                val code = edt_code!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show()
                    return
                }
                val user = User()
                //TODO 设置手机号码（必填）
                user.mobilePhoneNumber = phone
                //TODO 设置用户名，如果没有传用户名，则默认为手机号码
                user.username = username
                //TODO 设置用户密码
                user.setPassword(password)
                //TODO 设置额外信息：此处为昵称
                user.nickname = nickname
                user.signOrLogin(code, object : SaveListener<BmobUser>() {
                    override fun done(bmobUser: BmobUser, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("短信注册成功：" + bmobUser.username)
                            startActivity(Intent(this@UserSignUpPasswordAndSmsActivity, UserMainActivity::class.java))
                        } else {
                            tv_info!!.append("短信注册失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })
            }
            else -> {
            }
        }
    }
}
