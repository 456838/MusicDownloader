package cn.bmob.sdkdemo.activity.security

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.activity.security.acl.AclActivity
import cn.bmob.sdkdemo.activity.security.role.BmobRoleActivity
import com.salton123.ui.biz.BaseTitleActivity

/**
 * Created on 2018/12/5 15:59
 *
 * @author zhangchaozhou
 */
class SecurityActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_acl, R.id.btn_role, R.id.btn_app_sign)
    }

    override fun getLayout(): Int = R.layout.activity_security

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_acl -> startActivity(Intent(this, AclActivity::class.java))
            R.id.btn_role -> startActivity(Intent(this, BmobRoleActivity::class.java))
            R.id.btn_app_sign -> startActivity(Intent(this, SignVerifyActivity::class.java))
            else -> {
            }
        }
    }

}
