package cn.bmob.sdkdemo.activity.security.acl

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Post
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobACL
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_acl.*

/**
 * ACL：Access Control List 访问控制列表
 *
 *
 * 每条数据对于每个用户或角色都可以设置相应的访问权限
 *
 * @author zhangchaozhou
 */
class AclActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_acl_public, R.id.btn_acl_user, R.id.btn_acl_role)
    }

    override fun getLayout(): Int = R.layout.activity_acl
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_acl_public -> publicAcl()
            R.id.btn_acl_user -> userAcl()
            R.id.btn_acl_role -> roleAcl()
            else -> {
            }
        }
    }

    /**
     * 设置发布的帖子对所有用户的访问控制权限
     */
    private fun publicAcl() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_acl_public!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            val post = Post()
            post.author = user
            post.content = "content" + System.currentTimeMillis()
            post.title = "title" + System.currentTimeMillis()
            val bmobACL = BmobACL()
            //设置此帖子为所有用户不可写
            bmobACL.setPublicWriteAccess(false)
            //设置此帖子为所有用户可读
            bmobACL.setPublicReadAccess(true)
            post.acl = bmobACL
            post.save(object : SaveListener<String>() {
                override fun done(s: String, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(btn_acl_public!!, "发布帖子成功", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_acl_public!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

    }


    /**
     * 设置发布的帖子对当前用户的访问控制权限
     */
    private fun userAcl() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_acl_public!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            val post = Post()
            post.author = user
            post.content = "content" + System.currentTimeMillis()
            post.title = "title" + System.currentTimeMillis()
            val bmobACL = BmobACL()
            //设置此帖子为当前用户可写
            bmobACL.setReadAccess(user, true)
            //设置此帖子为所有用户可读
            bmobACL.setPublicReadAccess(true)
            post.acl = bmobACL
            post.save(object : SaveListener<String>() {
                override fun done(s: String, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(btn_acl_public!!, "发布帖子成功", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_acl_public!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }


    /**
     * 设置发布的帖子对某种角色的访问控制权限
     */
    private fun roleAcl() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_acl_public!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            val post = Post()
            post.author = user
            post.content = "content" + System.currentTimeMillis()
            post.title = "title" + System.currentTimeMillis()
            val bmobACL = BmobACL()
            //设置此帖子为当前用户可写
            bmobACL.setWriteAccess(user, true)
            //设置此帖子为某种角色可读
            bmobACL.setRoleReadAccess("female", true)
            post.acl = bmobACL
            post.save(object : SaveListener<String>() {
                override fun done(s: String, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(btn_acl_public!!, "发布帖子成功", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_acl_public!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

}
