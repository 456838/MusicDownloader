package cn.bmob.sdkdemo.activity.user.normal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.OtherService
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FetchUserInfoListener
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity

/**
 * Created on 2018/11/27 16:11
 * 用户的正常操作
 *
 * @author zhangchaozhou
 */
class UserNormalActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_sign_up, R.id.btn_login, R.id.btn_current_user, R.id.btn_is_login, R.id.btn_logout, R.id.btn_update_user, R.id.btn_reset_password, R.id.btn_fetch_user_info, R.id.btn_fetch_user_info_json, R.id.btn_update_user_other_process)
    }

    override fun getLayout(): Int = R.layout.activity_user_normal

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_sign_up ->
                //账号密码注册
                signUp(view)
            R.id.btn_login ->
                //账号密码登录1
                //                login(view);
                //账号密码登录2
                loginByAccount(view)
            R.id.btn_current_user -> if (BmobUser.isLogin()) {
                val user = BmobUser.getCurrentUser(User::class.java)
                Snackbar.make(view, "当前用户：" + user.username + "-" + user.age, Snackbar.LENGTH_LONG).show()
                val username = BmobUser.getObjectByKey("username") as String
                val age = BmobUser.getObjectByKey("age") as Int
                Snackbar.make(view, "当前用户属性：$username-$age", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }
            R.id.btn_is_login -> if (BmobUser.isLogin()) {
                val user = BmobUser.getCurrentUser(User::class.java)
                Snackbar.make(view, "已经登录：" + user.username, Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "尚未登录", Snackbar.LENGTH_LONG).show()
            }

            R.id.btn_update_user -> if (BmobUser.isLogin()) {
                updateUser(view)
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }


            R.id.btn_update_user_other_process -> if (BmobUser.isLogin()) {
                val intent = Intent(application, OtherService::class.java)
                startService(intent)
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }

            R.id.btn_reset_password -> if (BmobUser.isLogin()) {
                resetPassword(view)
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }
            R.id.btn_fetch_user_info -> if (BmobUser.isLogin()) {
                fetchUserInfo(view)
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }
            R.id.btn_fetch_user_info_json -> if (BmobUser.isLogin()) {
                fetchUserJsonInfo(view)
            } else {
                Snackbar.make(view, "尚未登录，请先登录", Snackbar.LENGTH_LONG).show()
            }
            R.id.btn_logout -> BmobUser.logOut()

            else -> {
            }
        }
    }


    /**
     * 账号密码注册
     */
    private fun signUp(view: View) {
        val user = User()
        user.username = "" + System.currentTimeMillis()
        user.setPassword("" + System.currentTimeMillis())
        user.age = 18
        user.gender = 0
        user.signUp(object : SaveListener<User>() {
            override fun done(user: User, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "尚未失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 账号密码登录
     */
    private fun login(view: View) {
        val user = User()
        //此处替换为你的用户名
        user.username = "username"
        //此处替换为你的密码
        user.setPassword("password")
        user.login(object : SaveListener<User>() {
            override fun done(bmobUser: User, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.username, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "登录失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 账号密码登录
     */
    private fun loginByAccount(view: View) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount("username", "password", object : LogInListener<User>() {
            override fun done(user: User, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.username, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "登录失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }


    /**
     * 更新用户操作并同步更新本地的用户信息
     */
    private fun updateUser(view: View) {
        val user = BmobUser.getCurrentUser(User::class.java)
        user.age = 20
        user.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "更新用户信息成功：" + user.age!!, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "更新用户信息失败：" + e.message, Snackbar.LENGTH_LONG).show()
                    Log.e("error", e.message)
                }
            }
        })
    }


    /**
     * 修改当前用户密码
     */
    private fun resetPassword(view: View) {
        BmobUser.updateCurrentUserPassword("oldPassword", "newPassword", object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "重置密码成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "重置密码失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 同步控制台数据到缓存中
     *
     * @param view
     */
    private fun fetchUserInfo(view: View) {
        BmobUser.fetchUserInfo(object : FetchUserInfoListener<BmobUser>() {
            override fun done(bmobUser: BmobUser, e: BmobException?) {
                if (e == null) {
                    val user = BmobUser.getCurrentUser(User::class.java)
                    Snackbar.make(view, "更新用户本地缓存信息成功：" + user.username + "-" + user.age, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("error", e.message)
                    Snackbar.make(view, "更新用户本地缓存信息失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 获取控制台最新数据
     *
     * @param view
     */
    private fun fetchUserJsonInfo(view: View) {
        BmobUser.fetchUserJsonInfo(object : FetchUserInfoListener<String>() {
            override fun done(json: String, e: BmobException?) {
                if (e == null) {
                    Log.e("success", json)
                    Snackbar.make(view, "获取控制台最新数据成功：$json", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("error", e.message)
                    Snackbar.make(view, "获取控制台最新数据失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询用户表
     */
    private fun queryUser(view: View) {
        val bmobQuery = BmobQuery<User>()
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(`object`: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "查询失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 提供旧密码修改密码
     */
    private fun updatePassword(view: View) {
        //TODO 此处替换为你的旧密码和新密码
        BmobUser.updateCurrentUserPassword("oldPwd", "newPwd", object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "查询失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
