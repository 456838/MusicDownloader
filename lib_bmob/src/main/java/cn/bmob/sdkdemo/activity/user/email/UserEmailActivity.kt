package cn.bmob.sdkdemo.activity.user.email

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_email.*

/**
 * Created on 2018/11/27 14:39
 *
 * @author zhangchaozhou
 */
class UserEmailActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_reset_password, R.id.btn_verify, R.id.btn_login)
    }

    override fun getLayout(): Int = R.layout.activity_user_email

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset_password -> resetPasswordByEmail()
            R.id.btn_verify -> emailVerify()
            R.id.btn_login -> loginByEmailPwd()
            else -> {
            }
        }
    }

    /**
     * 邮箱重置密码
     */
    private fun resetPasswordByEmail() {
        //TODO 此处替换为你的邮箱
        val email = "email"
        BmobUser.resetPasswordByEmail(email, object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(iv_avatar!!, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(iv_avatar!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 发送验证邮件
     */
    private fun emailVerify() {
        //TODO 此处替换为你的邮箱
        val email = "email"
        BmobUser.requestEmailVerify(email, object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(iv_avatar!!, "请求验证邮件成功，请到" + email + "邮箱中进行激活账户。", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(iv_avatar!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 邮箱+密码登录
     */
    private fun loginByEmailPwd() {
        //TODO 此处替换为你的邮箱和密码
        BmobUser.loginByAccount("email", "password", object : LogInListener<User>() {

            override fun done(user: User, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(iv_avatar!!, user.username + "-" + user.age + "-" + user.objectId + "-" + user.email, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(iv_avatar!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


}
