package com.salton123.bmob.biz.mandala

import android.annotation.SuppressLint
import cn.bmob.v3.BmobBatch
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BatchResult
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.UploadBatchListener
import com.salton123.bmob.biz.mandala.bean.MandalaItem
import com.salton123.bmob.helper.BmobHelper
import com.salton123.log.XLog
import io.reactivex.Observable
import java.io.File

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/3 13:52
 * ModifyTime: 13:52
 * Description:
 */
object MandalaCore {
    fun uploadFile(folder: File) {
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
                    }).subscribe {
                it.forEachIndexed { _, bmobFile ->
                    mandalaList.forEachIndexed { _, mandalaItem ->
                        if (bmobFile.localFile.absolutePath == mandalaItem.localFilePath) {
                            mandalaItem.url = bmobFile.url
                        }
                    }
                }
                BmobBatch().insertBatch(mandalaList).doBatch(object : QueryListListener<BatchResult>() {
                    override fun done(list: List<BatchResult>, e: BmobException?) {
                        if (e != null) {
                            XLog.i(BmobHelper::class.java, "e:$e")
                        }
                    }
                })
            }
        }
    }

    @SuppressLint("CheckResult")
    fun loadItem(page: Int): Observable<List<MandalaItem>> {
        return Observable.create<List<MandalaItem>> { emitter ->
            var query = BmobQuery<MandalaItem>()
            query.setPage(page, 10)
            query.order("updateAt")
            query.findObjects(object : FindListener<MandalaItem>() {
                override fun done(p0: List<MandalaItem>?, p1: BmobException?) {
                    if (p0 != null && p1 == null) {
                        emitter.onNext(p0)
                        emitter.onComplete()
                    } else {
                        p1?.let { emitter.onError(it) }
                    }
                }
            })
        }
    }
}
