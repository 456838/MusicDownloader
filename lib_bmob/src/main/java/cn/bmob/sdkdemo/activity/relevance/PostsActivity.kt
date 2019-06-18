package cn.bmob.sdkdemo.activity.relevance

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.adapter.PostAdapter
import cn.bmob.sdkdemo.bean.Post
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pointer.*

/**
 * Created on 2018/11/26 09:33
 *
 * @author zhangchaozhou
 */
class PostsActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_post!!.layoutManager = linearLayoutManager
        rv_post!!.setHasFixedSize(true)
        queryPosts()
        setListener(R.id.fab_query_post, R.id.fab_add_post, R.id.fab_update_post, R.id.fab_remove_post)
    }

    override fun getLayout(): Int =R.layout.activity_pointer

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_query_post -> queryPostAuthor()
            R.id.fab_add_post -> savePost()
            R.id.fab_update_post -> updatePostAuthor()
            R.id.fab_remove_post -> removePostAuthor()

            else -> {
            }
        }
    }
    
    private var mPostAdapter: PostAdapter? = null

    private fun queryPosts() {

        val bmobQuery = BmobQuery<Post>()
        bmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(posts: List<Post>, e: BmobException) {
                mPostAdapter = PostAdapter(posts)
                rv_post!!.adapter = mPostAdapter
            }
        })
    }



    /**
     * 添加一对一关联，当前用户发布帖子
     */
    private fun savePost() {
        if (BmobUser.isLogin()) {
            val post = Post()
            post.title = "帖子标题"
            post.content = "帖子内容"
            //添加一对一关联，用户关联帖子
            post.author = BmobUser.getCurrentUser(User::class.java)
            post.save(object : SaveListener<String>() {
                override fun done(s: String, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(fab_add_post!!, "发布帖子成功：$s", Snackbar.LENGTH_LONG).show()
                    } else {
                        Log.e("BMOB", e.toString())
                        Snackbar.make(fab_add_post!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        } else {
            Snackbar.make(fab_add_post!!, "请先登录", Snackbar.LENGTH_LONG).show()
        }
    }


    /**
     * 修改一对一关联，修改帖子和用户的关系
     */
    private fun updatePostAuthor() {
        val user = User()
        //需要修改，否则会有internal error
        user.objectId = "此处填写你需要关联的用户"
        val post = Post()
        //需要修改，否则会有internal error
        post.objectId = "此处填写需要修改的帖子"
        //修改一对一关联，修改帖子和用户的关系
        post.author = user
        post.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(fab_add_post!!, "修改帖子成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(fab_add_post!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 删除一对一关联，解除帖子和用户的关系
     */
    private fun removePostAuthor() {
        val post = Post()
        //需要修改，否则会有internal error
        post.objectId = "此处填写需要修改的帖子"
        //删除一对一关联，解除帖子和用户的关系
        post.remove("author")
        post.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(fab_add_post!!, "修改帖子成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(fab_add_post!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询一对一关联，查询当前用户发表的所有帖子
     */
    private fun queryPostAuthor() {
        if (BmobUser.isLogin()) {
            val query = BmobQuery<Post>()
            query.addWhereEqualTo("author", BmobUser.getCurrentUser(User::class.java))
            query.order("-updatedAt")
            //包含作者信息
            query.include("author")
            query.findObjects(object : FindListener<Post>() {

                override fun done(`object`: List<Post>, e: BmobException?) {
                    if (e == null) {
                        Snackbar.make(fab_add_post!!, "查询成功", Snackbar.LENGTH_LONG).show()
                    } else {
                        Log.e("BMOB", e.toString())
                        Snackbar.make(fab_add_post!!, e.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }

            })
        } else {
            Snackbar.make(fab_add_post!!, "请先登录", Snackbar.LENGTH_LONG).show()
        }
    }


}
