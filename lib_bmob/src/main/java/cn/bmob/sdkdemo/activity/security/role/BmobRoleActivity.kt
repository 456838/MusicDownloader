package cn.bmob.sdkdemo.activity.security.role

import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobRole
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_role.*

/**
 * Created on 2018/11/26 15:13
 *
 * @author zhangchaozhou
 */
class BmobRoleActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_query_role, R.id.btn_add_role, R.id.btn_remove_role)
    }

    override fun getLayout(): Int = R.layout.activity_role

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_query_role -> queryRole("female")
            R.id.btn_add_role -> {
                val bmobRole = BmobRole("female")
                saveRoleAndAddUseRRole(bmobRole)
            }
            R.id.btn_remove_role -> {
                val bmobRole = BmobRole("female")
                removeUserFromRole(bmobRole)
            }

            else -> {
            }
        }
    }


    /**
     * 查询某角色是否存在
     *
     * @param roleName
     */
    private fun queryRole(roleName: String) {
        val bmobRoleBmobQuery = BmobQuery<BmobRole>()
        bmobRoleBmobQuery.addWhereEqualTo("name", roleName)
        bmobRoleBmobQuery.findObjects(object : FindListener<BmobRole>() {
            override fun done(list: List<BmobRole>, e: BmobException?) {
                if (e == null) {
                    if (list.size > 0) {
                        //已存在该角色
                        addUseRRole(list[0])
                    } else {
                        //不存在该角色
                        val bmobRole = BmobRole(roleName)
                        saveRoleAndAddUseRRole(bmobRole)
                    }
                } else {
                    Snackbar.make(btn_query_role!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }


    /**
     * 保存某个角色并保存用户到该角色中
     *
     * @param bmobRole
     */
    private fun saveRoleAndAddUseRRole(bmobRole: BmobRole) {

        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_query_role!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            bmobRole.users.add(user)
            bmobRole.save(object : SaveListener<String>() {
                override fun done(s: String, e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(this@BmobRoleActivity, "角色用户添加成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(btn_query_role!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }

    }


    /**
     * 添加用户到某个角色中
     *
     * @param bmobRole
     */
    private fun addUseRRole(bmobRole: BmobRole) {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_query_role!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            bmobRole.users.add(user)
            bmobRole.update(object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(this@BmobRoleActivity, "角色用户添加成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@BmobRoleActivity, e.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }


    /**
     * 把用户从某个角色中移除
     *
     * @param bmobRole
     */
    private fun removeUserFromRole(bmobRole: BmobRole) {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_query_role!!, "请先登录", Snackbar.LENGTH_LONG).show()
        } else {
            bmobRole.users.remove(user)
            bmobRole.update(object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(this@BmobRoleActivity, "角色用户添加成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@BmobRoleActivity, e.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
