package cn.bmob.sdkdemo.activity.relevance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.Post

/**
 * Created on 2018/11/28 17:01
 *
 * @author zhangchaozhou
 */
class CommentActivity : AppCompatActivity() {


    private var mPost: Post? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comment)

        mPost = intent.getSerializableExtra("post") as Post


    }


}
