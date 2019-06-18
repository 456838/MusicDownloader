package cn.bmob.sdkdemo.activity.user.third

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_third.*
import org.json.JSONObject

/**
 * Created on 2018/11/27 14:40
 *
 * @author zhangchaozhou
 */
class UserThirdActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_third_signup_login, R.id.btn_third_bind, R.id.btn_third_unbind)
    }

    override fun getLayout(): Int = R.layout.activity_user_third

    override fun onClick(view: View?)  {
        when (view?.id) {
            R.id.btn_third_signup_login ->
                //TODO 此处替换为你进行第三方登录后得到的信息
                thirdSingupLogin("", "", "", "")
            R.id.btn_third_bind ->
                //TODO 此处替换为你进行第三方登录后得到的信息
                associate("", "", "", "")
            R.id.btn_third_unbind -> unAssociate("")
            else -> {
            }
        }
    }

    /**
     * 第三方平台一键注册或登录
     *
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private fun thirdSingupLogin(snsType: String, accessToken: String, expiresIn: String, userId: String) {
        val authInfo = BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId)
        BmobUser.loginWithAuthData(authInfo, object : LogInListener<JSONObject>() {
            override fun done(user: JSONObject, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_third_signup_login!!, "第三方平台一键注册或登录成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_third_signup_login!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 第三方平台关联
     * @param snsType
     * @param accessToken
     * @param expiresIn
     * @param userId
     */
    private fun associate(snsType: String, accessToken: String, expiresIn: String, userId: String) {
        val authInfo = BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId)
        BmobUser.associateWithAuthData(authInfo, object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_third_signup_login!!, "第三方平台关联成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_third_signup_login!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 解除第三方平台关联
     * @param snsType
     */
    private fun unAssociate(snsType: String) {
        BmobUser.dissociateAuthData(snsType, object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_third_signup_login!!, "第三方平台关联解除成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    if (e.errorCode == 208) {
                        Snackbar.make(btn_third_signup_login!!, "你没有关联该账号", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_third_signup_login!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })
    }




}
