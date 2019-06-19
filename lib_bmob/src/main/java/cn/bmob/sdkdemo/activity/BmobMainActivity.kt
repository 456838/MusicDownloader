package cn.bmob.sdkdemo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.activity.`object`.DataOperationActivity
import cn.bmob.sdkdemo.activity.array.ArrayActivity
import cn.bmob.sdkdemo.activity.article.ArticleActivity
import cn.bmob.sdkdemo.activity.cloud.CloudActivity
import cn.bmob.sdkdemo.activity.date.DateActivity
import cn.bmob.sdkdemo.activity.file.FileManagerActivity
import cn.bmob.sdkdemo.activity.installation.InstallationActivity
import cn.bmob.sdkdemo.activity.location.LocationActivity
import cn.bmob.sdkdemo.activity.other.OtherFunctionActivity
import cn.bmob.sdkdemo.activity.realtime.RealTimeDataActivity
import cn.bmob.sdkdemo.activity.relevance.PostsActivity
import cn.bmob.sdkdemo.activity.security.SecurityActivity
import cn.bmob.sdkdemo.activity.sms.SmsActivity
import cn.bmob.sdkdemo.activity.table.TableActivity
import cn.bmob.sdkdemo.activity.update.AppVersionUpdateActivity
import cn.bmob.sdkdemo.activity.user.UserActivity
import com.salton123.ui.biz.BaseTitleActivity

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/2 22:07
 * ModifyTime: 22:07
 * Description:
 */
class BmobMainActivity : BaseTitleActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
    }

    override fun initListener() {
        setListener(R.id.btn_data_operation, R.id.btn_user, R.id.btn_installation, R.id.btn_file, R.id.btn_relevance,
                R.id.btn_security, R.id.btn_realtime, R.id.btn_cloud, R.id.btn_update, R.id.btn_article,
                R.id.btn_location, R.id.btn_array, R.id.btn_date, R.id.btn_table, R.id.btn_other, R.id.btn_sms)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_data_operation -> startActivity(Intent(this, DataOperationActivity::class.java))
            R.id.btn_user -> startActivity(Intent(this, UserActivity::class.java))
            R.id.btn_installation -> startActivity(Intent(this, InstallationActivity::class.java))
            R.id.btn_file -> startActivity(Intent(this, FileManagerActivity::class.java))
            R.id.btn_relevance -> startActivity(Intent(this, PostsActivity::class.java))
            R.id.btn_security -> startActivity(Intent(this, SecurityActivity::class.java))
            R.id.btn_realtime -> startActivity(Intent(this, RealTimeDataActivity::class.java))
            R.id.btn_cloud -> startActivity(Intent(this, CloudActivity::class.java))
            R.id.btn_update -> startActivity(Intent(this, AppVersionUpdateActivity::class.java))
            R.id.btn_article -> startActivity(Intent(this, ArticleActivity::class.java))
            R.id.btn_location -> startActivity(Intent(this, LocationActivity::class.java))
            R.id.btn_array -> startActivity(Intent(this, ArrayActivity::class.java))
            R.id.btn_date -> startActivity(Intent(this, DateActivity::class.java))
            R.id.btn_table -> startActivity(Intent(this, TableActivity::class.java))
            R.id.btn_sms -> startActivity(Intent(this, SmsActivity::class.java))
            R.id.btn_other -> startActivity(Intent(this, OtherFunctionActivity::class.java))
            else -> {
            }
        }
    }

    override fun getLayout(): Int = R.layout.activity_main
}