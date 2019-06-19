package cn.bmob.sdkdemo.activity.sms

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobInstallationManager
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.InstallationListener
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_sms.*

/**
 * Created on 18/9/25 16:05
 *
 * @author zhangchaozhou
 */
class SmsActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        Bmob.resetDomain("http://open-vip.bmob.cn/8/")

        BmobInstallationManager.getInstance().initialize(object : InstallationListener<BmobInstallation>() {
            override fun done(bmobInstallation: BmobInstallation, e: BmobException) {

            }
        })
        setListener(R.id.btn_send, R.id.btn_verify)
    }

    override fun getLayout(): Int = R.layout.activity_sms
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send -> {
                val phone = edt_phone!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show()
                    return
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板，默认模板名称为空字符串""。
                 *
                 * TODO 应用名称以及自定义短信内容，请使用不会被和谐的文字，防止发送短信验证码失败。
                 *
                 */
                BmobSMS.requestSMSCode(phone, "", object : QueryListener<Int>() {
                    override fun done(smsId: Int?, e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("发送验证码成功，短信ID：$smsId\n")
                        } else {
                            tv_info!!.append("发送验证码失败：" + e.errorCode + "-" + e.message + "\n")
                        }
                    }
                })
            }
            R.id.btn_verify -> {
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
                BmobSMS.verifySmsCode(phone, code, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            tv_info!!.append("验证码验证成功，您可以在此时进行重要操作！\n")
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
