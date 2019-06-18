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
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_login_sms.*

/**
 * Created on 18/9/26 09:39
 * TODO 通过短信登录，只针对已经通过短信验证注册的用户
 *
 * @author zhangchaozhou
 */
class UserLoginSmsActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_send, R.id.btn_login)
    }

    override fun getLayout(): Int = R.layout.activity_user_login_sms

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
            R.id.btn_login -> {
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
                /**
                 * TODO 此API需要在用户已经注册并验证的前提下才能使用
                 */
                BmobUser.loginBySMSCode(phone, code, object : LogInListener<BmobUser>() {
                    override fun done(bmobUser: BmobUser, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("短信登录成功：" + bmobUser.objectId + "-" + bmobUser.username)
                            startActivity(Intent(this@UserLoginSmsActivity, UserMainActivity::class.java))
                        } else {
                            tv_info!!.append("短信登录失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })
            }
            else -> {
            }
        }
    }


    /**
     * 手机号码密码登录
     */
    private fun loginByPhone() {
        //TODO 此处替换为你的手机号码和密码
        BmobUser.loginByAccount("phone", "password", object : LogInListener<User>() {

            override fun done(user: User?, e: BmobException?) {
                if (user != null) {
                    if (e == null) {
                        tv_info!!.append("短信登录成功：" + user.objectId + "-" + user.username)
                    } else {
                        tv_info!!.append("短信登录失败：" + e.errorCode + "-" + e.message + "\n")
                    }
                }
            }
        })
    }

}
