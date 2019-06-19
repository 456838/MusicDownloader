package cn.bmob.sdkdemo.activity.file

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.activity.user.UserActivity
import cn.bmob.sdkdemo.bean.User
import cn.bmob.sdkdemo.utils.UriUtils
import cn.bmob.v3.BmobBatch
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BatchResult
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.DeleteBatchListener
import cn.bmob.v3.listener.DownloadFileListener
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadBatchListener
import cn.bmob.v3.listener.UploadFileListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.biz.BaseTitleActivity
import kotlinx.android.synthetic.main.activity_file_manager.*
import java.io.File
import java.util.*

/**
 * Created on 2018/11/20 14:06
 *
 *
 * TODO 1、兼容android6.0运行时权限
 * TODO 2、兼容android7.0文件提供器
 *
 * @author zhangchaozhou
 */
class FileManagerActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_upload_single, R.id.btn_upload_single_to_table, R.id.btn_upload_multi, R.id.btn_upload_multi_to_table, R.id.btn_delete_single)
    }

    override fun getLayout(): Int = R.layout.activity_file_manager

    internal var movies: List<BmobObject> = ArrayList()


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_upload_single -> checkStoragePermissions(REQUEST_UPLOAD_SINGLE)
            R.id.btn_upload_single_to_table -> checkStoragePermissions(REQUEST_UPLOAD_SINGLE_TO_TABLE)
            R.id.btn_upload_multi -> checkStoragePermissions(REQUEST_UPLOAD_MULTI)
            R.id.btn_upload_multi_to_table -> checkStoragePermissions(REQUEST_UPLOAD_MULTI_TO_TABLE)
            R.id.btn_download_single -> checkStoragePermissions(REQUEST_DOWNLOAD_SINGLE)
            R.id.btn_delete_single -> checkStoragePermissions(REQUEST_DELETE_SINGLE)
            else -> {
            }
        }
    }


    /**
     * TODO 1、兼容android6.0运行时权限
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
                REQUEST_UPLOAD_SINGLE -> chooseFile(requestCode)
                REQUEST_UPLOAD_SINGLE_TO_TABLE -> chooseFile(requestCode)
                REQUEST_UPLOAD_MULTI -> {
                }
                REQUEST_UPLOAD_MULTI_TO_TABLE -> {
                }
                REQUEST_DOWNLOAD_SINGLE -> downloadUserAvatar()
                REQUEST_DELETE_SINGLE -> deleteUserAvatar()
                else -> {
                }
            }
        }
    }


    /**
     * 删除用户头像
     */
    private fun deleteUserAvatar() {
        if (BmobUser.isLogin()) {
            val user = BmobUser.getCurrentUser(User::class.java)
            if (user.avatar != null) {
                deleteFile(user.avatar)
            }
        }
    }

    /**
     * 下载用户头像
     */
    private fun downloadUserAvatar() {
        if (BmobUser.isLogin()) {
            val user = BmobUser.getCurrentUser(User::class.java)
            if (user.avatar != null) {
                downloadFile(user.avatar)
            }
        }
    }

    /**
     * TODO 1、兼容android6.0运行时权限
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

    /**
     * TODO 1、兼容android6.0运行时权限
     *
     *
     * 权限授权结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_UPLOAD_SINGLE -> if (checkResults(grantResults)) {
                chooseFile(requestCode)
            }
            REQUEST_UPLOAD_SINGLE_TO_TABLE -> if (checkResults(grantResults)) {
                chooseFile(requestCode)
            }
            REQUEST_UPLOAD_MULTI -> if (checkResults(grantResults)) {
            }
            REQUEST_UPLOAD_MULTI_TO_TABLE -> if (checkResults(grantResults)) {
            }
            else -> {
            }
        }
    }


    /**
     * 选择文件
     *
     * @param requestCode
     */
    private fun chooseFile(requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(intent, "选择文件"), requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_UPLOAD_SINGLE -> if (resultCode == Activity.RESULT_OK) {
                val path = UriUtils.getRealPathFromUri(this, data!!.data)
                if (!TextUtils.isEmpty(path)) {
                    Log.e("BMOB", "url:$path")
                    uploadSingleFile(File(path))
                }
            }
            REQUEST_UPLOAD_SINGLE_TO_TABLE -> if (resultCode == Activity.RESULT_OK) {
                val path = UriUtils.getRealPathFromUri(this, data!!.data)
                if (!TextUtils.isEmpty(path)) {
                    Log.e("BMOB", "url:$path")
                    uploadSingleFile(File(path))
                }
            }
            else -> {
            }
        }
    }

    /**
     * 上传单一文件
     *
     * @param file
     */
    private fun uploadSingleFile(file: File) {
        val bmobFile = BmobFile(file)
        bmobFile.upload(object : UploadFileListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single!!, "上传成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.errorCode.toString() + "-" + e.message)
                    Snackbar.make(btn_upload_single!!, "上传失败：" + e.errorCode + "-" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 上传单一文件后设置到表中，此处是上传头像
     *
     * @param file
     */
    private fun uploadSingleFileToTable(file: File) {
        val bmobFile = BmobFile(file)
        bmobFile.uploadblock(object : UploadFileListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single_to_table!!, "上传成功", Snackbar.LENGTH_LONG).show()
                    setFileToTable(bmobFile)
                } else {
                    Snackbar.make(btn_upload_single_to_table!!, "上传失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 设置文件到表中，此处是设置头像
     *
     * @param bmobFile
     */
    private fun setFileToTable(bmobFile: BmobFile) {
        if (!BmobUser.isLogin()) {
            Snackbar.make(btn_upload_single_to_table!!, "请先登录", Snackbar.LENGTH_LONG).show()
            startActivity(Intent(this, UserActivity::class.java))
            return
        }
        val user = BmobUser.getCurrentUser(User::class.java)
        user.avatar = bmobFile
        user.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single_to_table!!, "设置文件到表中成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_upload_single_to_table!!, "设置文件到表中失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 下载文件
     *
     * @param bmobFile
     */
    private fun downloadFile(bmobFile: BmobFile) {
        val saveFile = File(Environment.getExternalStorageDirectory(), bmobFile.filename)
        bmobFile.download(saveFile, object : DownloadFileListener() {

            override fun onStart() {
                Snackbar.make(btn_upload_single!!, "开始下载", Snackbar.LENGTH_LONG).show()
            }

            override fun done(s: String, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single!!, "下载成功,保存路径:$s", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_upload_single!!, "下载失败：" + e.errorCode + "," + e.message, Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onProgress(value: Int?, networkSpeed: Long) {
                Snackbar.make(btn_upload_single!!, "下载进度：$value,$networkSpeed", Snackbar.LENGTH_LONG).show()
            }

        })
    }


    /**
     * 删除文件
     *
     * @param bmobFile
     */
    private fun deleteFile(bmobFile: BmobFile) {
        bmobFile.delete(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single!!, "删除成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_upload_single!!, "删除失败：" + e.errorCode + "," + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 批量删除文件
     *
     * @param urls
     */
    private fun deleteBatchFile(urls: Array<String>) {
        BmobFile.deleteBatch(urls, object : DeleteBatchListener() {

            override fun done(failUrls: Array<String>?, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single!!, "删除成功", Snackbar.LENGTH_LONG).show()
                } else {
                    if (failUrls != null) {
                        Snackbar.make(btn_upload_single!!, "删除失败个数：" + failUrls.size + "," + e.toString(), Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(btn_upload_single!!, "全部文件删除失败：" + e.errorCode + "," + e.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    /**
     * 此方法适用于批量更新数据且每条数据只有一个BmobFile字段
     */
    fun insertBatchDatasWithOne(filePaths: Array<String>) {
        BmobFile.uploadBatch(filePaths, object : UploadBatchListener {

            override fun onSuccess(files: List<BmobFile>, urls: List<String>) {

                //有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
                if (urls.size == filePaths.size) {
                    Log.e("success", "上传结束")
                }

                insertBatch(movies)
            }

            override fun onError(statusCode: Int, errorMsg: String) {
                Snackbar.make(btn_upload_single!!, "错误码：$statusCode,错误描述：$errorMsg", Snackbar.LENGTH_LONG).show()
            }

            override fun onProgress(curIndex: Int, curPercent: Int, total: Int, totalPercent: Int) {
                Snackbar.make(btn_upload_single!!, "insertBatchDatasWithOne -onProgress :$curIndex---$curPercent---$total----$totalPercent", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    /**
     * 创建操作
     * insertObject
     *
     * @return void
     * @throws
     */
    private fun insertObject(obj: BmobObject) {
        obj.save(object : SaveListener<String>() {

            override fun done(s: String, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_upload_single!!, "新增数据成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_upload_single!!, "新增数据失败：" + e.errorCode + "," + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 批量插入操作
     * insertBatch
     *
     * @return void
     * @throws
     */
    fun insertBatch(files: List<BmobObject>) {
        BmobBatch().insertBatch(files).doBatch(object : QueryListListener<BatchResult>() {
            override fun done(o: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in o.indices) {
                        val result = o[i]
                        val ex = result.error
                        if (ex == null) {
                            Snackbar.make(btn_upload_single!!, "新增数据成功", Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(btn_upload_single!!, "新增数据失败：" + ex.errorCode + "," + ex.message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Snackbar.make(btn_upload_single!!, "新增数据失败：" + e.errorCode + "," + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    companion object {


        private val REQUEST_UPLOAD_SINGLE = 1001
        private val REQUEST_UPLOAD_SINGLE_TO_TABLE = 1002
        private val REQUEST_UPLOAD_MULTI = 1003
        private val REQUEST_UPLOAD_MULTI_TO_TABLE = 1004

        private val REQUEST_DOWNLOAD_SINGLE = 1005

        private val REQUEST_DELETE_SINGLE = 1006
    }

}
