package cn.bmob.sdkdemo.activity.user.sms

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
import cn.bmob.v3.listener.UpdateListener
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_user_unbind.*

/**
 * Created on 18/9/26 09:40
 * TODO 通过短信绑定或解绑手机号码，只针对已经通过其他方式注册的用户
 *
 * @author zhangchaozhou
 */
class UserUnBindActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_send, R.id.btn_unbind)
    }

    override fun getLayout(): Int = R.layout.activity_user_unbind

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_send -> {
                val user = BmobUser.getCurrentUser(User::class.java)
                val phone = user.mobilePhoneNumber
                val verify = user.mobilePhoneNumberVerified
                if (TextUtils.isEmpty(phone) || verify == null || !verify) {
                    Toast.makeText(this, "当前账号尚未绑定手机号码", Toast.LENGTH_SHORT).show()
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
            R.id.btn_unbind -> {
                val user = BmobUser.getCurrentUser(User::class.java)
                val phone = user.mobilePhoneNumber
                val code = edt_code!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show()
                    return
                }
                BmobSMS.verifySmsCode(phone, code, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("验证码验证成功，您可以在此时进行解绑操作！\n")
                            val user = BmobUser.getCurrentUser(User::class.java)
                            user.mobilePhoneNumber = ""
                            user.mobilePhoneNumberVerified = false
                            user.update(object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                    if (e == null) {
                                        tv_info!!.append("解绑手机号码成功")
                                    } else {
                                        tv_info!!.append("解绑手机号码失败：" + e.errorCode + "-" + e.message)
                                    }
                                }
                            })
                        } else {
                            tv_info!!.append("验证码验证失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })
            }

            else -> {
            }
        }
    }
}
