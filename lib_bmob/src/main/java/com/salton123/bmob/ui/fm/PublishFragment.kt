package com.salton123.bmob.ui.fm

import android.os.Bundle
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.listener.UploadBatchListener
import com.salton123.bmob.biz.mandala.bean.MandalaItem
import com.salton123.bmob.helper.BmobHelper
import com.salton123.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fm_publish.*
import java.io.File


/**
 * User: newSalton@outlook.com
 * Date: 2019/6/1 15:44
 * ModifyTime: 15:44
 * Description: publish file to server
 */
class PublishFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fm_publish

    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(publish)
    }

    override fun onClick(v: View?) {
        when (v) {
            publish -> {
                var folder = File("/sdcard/z01/")
                if (folder.isDirectory) {
                    var mandalaList = folder.listFiles().map { MandalaItem(it.name, it.absolutePath) }.toList()
                    var result = BmobHelper.uploadBatch(mandalaList.map { it.localFilePath },
                            object : UploadBatchListener {
                                override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                                }

                                override fun onProgress(p0: Int, p1: Int, p2: Int, p3: Int) {
                                }

                                override fun onError(p0: Int, p1: String?) {
                                }
                            })
                    result.subscribe {
                        it.forEachIndexed { _, bmobFile ->
                            mandalaList.forEachIndexed { _, mandalaItem ->
                                if (bmobFile.localFile.absolutePath == mandalaItem.localFilePath) {
                                    mandalaItem.url = bmobFile.url
                                }
                            }
                        }
                        BmobHelper.insertBatch(mandalaList).subscribe {

                        }
                    }
                }
            }
        }
    }

}
