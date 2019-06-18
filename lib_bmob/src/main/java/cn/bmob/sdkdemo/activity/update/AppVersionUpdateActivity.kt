package cn.bmob.sdkdemo.activity.update

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.bmob.sdkdemo.R
import cn.bmob.v3.update.BmobUpdateAgent
import cn.bmob.v3.update.UpdateStatus
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_app_version_update.*
import java.util.*

/**
 * Created on 2018/12/6 09:21
 *
 * @author zhangchaozhou
 */
class AppVersionUpdateActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {

        //TODO 初始化，当控制台表出现后，注释掉此句
        BmobUpdateAgent.initAppVersion()
        //TODO 设置仅WiFi环境更新
        BmobUpdateAgent.setUpdateOnlyWifi(false)
        //TODO 设置更新监听器
        BmobUpdateAgent.setUpdateListener { updateStatus, updateInfo ->
            val e = updateInfo.getException()
            if (e == null) {
                Snackbar.make(btn_auto_update!!, "检测更新返回：" + updateInfo.version + "-" + updateInfo.path, Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(btn_auto_update!!, "检测更新返回：" + e.message + "(" + e.errorCode + ")", Snackbar.LENGTH_LONG).show()
            }
        }
        //TODO 设置对话框监听器
        BmobUpdateAgent.setDialogListener { status ->
            when (status) {
                UpdateStatus.Update -> Snackbar.make(btn_auto_update!!, "点击了立即更新按钮", Snackbar.LENGTH_LONG).show()
                UpdateStatus.NotNow -> Snackbar.make(btn_auto_update!!, "点击了以后再说按钮", Snackbar.LENGTH_LONG).show()
                UpdateStatus.Close -> Snackbar.make(btn_auto_update!!, "点击了对话框关闭按钮", Snackbar.LENGTH_LONG).show()

                else -> {
                }
            }
        }
        setListener(R.id.btn_auto_update, R.id.btn_check_update, R.id.btn_download_silent, R.id.btn_delete_apk)
    }

    override fun getLayout(): Int = R.layout.activity_app_version_update

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_auto_update -> checkStoragePermissions(REQUEST_AUTO)
            R.id.btn_check_update -> checkStoragePermissions(REQUEST_CHECK)
            R.id.btn_download_silent -> checkStoragePermissions(REQUEST_SILENT)
            R.id.btn_delete_apk -> checkStoragePermissions(REQUEST_DELETE)
            else -> {
            }
        }
    }


    /**
     * 检查权限
     *
     * @param requestCode
     */
    fun checkStoragePermissions(requestCode: Int) {
        val permissions = ArrayList<String>()
        val permissionCheckWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        val permissionCheckRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissions.size > 0) {
            val missions = arrayOf<String>()
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), requestCode)
        } else {
            when (requestCode) {
                REQUEST_AUTO -> BmobUpdateAgent.update(this)
                REQUEST_CHECK -> BmobUpdateAgent.update(this)
                REQUEST_SILENT -> BmobUpdateAgent.update(this)
                REQUEST_DELETE -> BmobUpdateAgent.update(this)
                else -> {
                }
            }
        }
    }

    /**
     * 检查授权结果
     *
     * @param grantResults
     * @return
     */
    fun checkResults(grantResults: IntArray?): Boolean {
        if (grantResults == null || grantResults.size < 1) {
            return false
        }
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_AUTO -> if (checkResults(grantResults)) {
                BmobUpdateAgent.update(this)
            }
            REQUEST_CHECK -> if (checkResults(grantResults)) {
                BmobUpdateAgent.update(this)
            }
            REQUEST_SILENT -> if (checkResults(grantResults)) {
                BmobUpdateAgent.update(this)
            }
            REQUEST_DELETE -> if (checkResults(grantResults)) {
                BmobUpdateAgent.update(this)
            }
            else -> {
            }
        }
    }

    companion object {


        private val REQUEST_AUTO = 1001
        private val REQUEST_CHECK = 1002
        private val REQUEST_SILENT = 1003
        private val REQUEST_DELETE = 1004
    }
}
