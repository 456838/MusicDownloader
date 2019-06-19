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
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_user_signup_or_login_sms.*

/**
 * Created on 18/9/25 16:12
 * TODO 通过短信验证注册或登录
 *
 * @author zhangchaozhou
 */
class UserSignUpOrLoginSmsActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_send, R.id.btn_signup_or_login)
    }

    override fun getLayout(): Int = R.layout.activity_user_signup_or_login_sms

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
            R.id.btn_signup_or_login -> {
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
                BmobUser.signOrLoginByMobilePhone(phone, code, object : LogInListener<BmobUser>() {
                    override fun done(bmobUser: BmobUser, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("短信注册或登录成功：" + bmobUser.username)
                            startActivity(Intent(this@UserSignUpOrLoginSmsActivity, UserMainActivity::class.java))
                        } else {
                            tv_info!!.append("短信注册或登录失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })

                signOrLogin(phone, code)
            }

            else -> {
            }
        }
    }

    /**
     * 一键注册或登录的同时保存其他字段的数据
     * @param phone
     * @param code
     */
    private fun signOrLogin(phone: String, code: String) {
        val user = User()
        //设置手机号码（必填）
        user.mobilePhoneNumber = phone
        //设置用户名，如果没有传用户名，则默认为手机号码
        user.username = phone
        //设置用户密码
        user.setPassword("")
        //设置额外信息：此处为年龄
        user.age = 18
        user.signOrLogin(code, object : SaveListener<User>() {

            override fun done(user: User, e: BmobException?) {
                if (e == null) {
                    tv_info!!.append("短信注册或登录成功：" + user.username)
                    startActivity(Intent(this@UserSignUpOrLoginSmsActivity, UserMainActivity::class.java))
                } else {
                    tv_info!!.append("短信注册或登录失败：" + e.errorCode + "-" + e.message + "\n")
                }
            }
        })
    }
}
