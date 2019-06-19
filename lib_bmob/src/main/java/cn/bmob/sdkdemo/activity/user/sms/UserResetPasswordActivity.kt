package cn.bmob.sdkdemo.activity.user.sms

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_user_reset_password.*

/**
 * Created on 18/9/26 09:39
 * TODO 通过原始密码重置密码，只针对已经通过密码注册登录的用户
 *
 * @author zhangchaozhou
 */
class UserResetPasswordActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_reset)
    }

    override fun getLayout(): Int = R.layout.activity_user_reset_password

    override fun onClick(view: View?) {
        val originPassword = edt_origin_password!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(originPassword)) {
            Toast.makeText(this, "请输入原始密码", Toast.LENGTH_SHORT).show()
            return
        }
        val newPassword = edt_new_password!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show()
            return
        }
        BmobUser.updateCurrentUserPassword(originPassword, newPassword, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    tv_info!!.append("密码重置成功")
                } else {
                    tv_info!!.append("密码重置失败：" + e.errorCode + "-" + e.message)
                }
            }
        })
    }
}
