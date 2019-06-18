package cn.bmob.sdkdemo.activity.installation

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobInstallationManager
import cn.bmob.v3.InstallationListener
import cn.bmob.v3.exception.BmobException
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_installation.*

/**
 * Created on 2018/11/26 15:34
 *
 * @author zhangchaozhou
 */
class InstallationActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_installation_id, R.id.btn_installation_init)
    }

    override fun getLayout(): Int = R.layout.activity_installation
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_installation_id ->

                Snackbar.make(btn_installation_id!!, "设备唯一号 " + BmobInstallationManager.getInstallationId(), Snackbar.LENGTH_LONG).show()
            R.id.btn_installation_init -> BmobInstallationManager.getInstance().initialize(object : InstallationListener<BmobInstallation>() {
                override fun done(bmobInstallation: BmobInstallation, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(btn_installation_init!!, "设备初始化成功：" + bmobInstallation.objectId, Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_installation_init!!, "设备初始化失败：" + e.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
            else -> {
            }
        }
    }
}
