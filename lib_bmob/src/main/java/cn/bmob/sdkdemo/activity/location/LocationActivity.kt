package cn.bmob.sdkdemo.activity.location

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.sdkdemo.R
import cn.bmob.sdkdemo.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobGeoPoint
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*

/**
 * 地理位置
 *
 * @author zhangchaozhou
 */
class LocationActivity : BaseActivity() {
    override fun initVariable(savedInstanceState: Bundle?) {
    }

    override fun initViewAndData() {
        setListener(R.id.btn_update_location, R.id.btn_get_location, R.id.btn_query_near, R.id.btn_query_miles, R.id.btn_query_kilometers, R.id.btn_query_radians, R.id.btn_query_box)
    }

    override fun getLayout(): Int = R.layout.activity_location


    /**
     * 查询最接近某个坐标的用户
     */
    private fun queryNear() {
        val query = BmobQuery<User>()
        val location = BmobGeoPoint(112.934755, 24.52065)
        query.addWhereNear("address", location)
        query.setLimit(10)
        query.findObjects(object : FindListener<User>() {

            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询指定坐标指定半径内的用户
     */
    private fun queryWithinRadians() {
        val query = BmobQuery<User>()
        val address = BmobGeoPoint(112.934755, 24.52065)
        query.addWhereWithinRadians("address", address, 10.0)
        query.findObjects(object : FindListener<User>() {

            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询指定坐标指定英里范围内的用户
     */
    private fun queryWithinMiles() {
        val query = BmobQuery<User>()
        val address = BmobGeoPoint(112.934755, 24.52065)
        query.addWhereWithinMiles("address", address, 10.0)
        query.findObjects(object : FindListener<User>() {

            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询指定坐标指定公里范围内的用户
     */
    private fun queryWithinKilometers() {
        val query = BmobQuery<User>()
        val address = BmobGeoPoint(112.934755, 24.52065)
        query.addWhereWithinKilometers("address", address, 10.0)
        query.findObjects(object : FindListener<User>() {

            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询矩形范围内的用户
     */
    private fun queryBox() {
        val query = BmobQuery<User>()
        //TODO 西南点，矩形的左下角坐标
        val southwestOfSF = BmobGeoPoint(112.934755, 24.52065)
        //TODO 东别点，矩形的右上角坐标
        val northeastOfSF = BmobGeoPoint(116.627623, 40.143687)
        query.addWhereWithinGeoBox("address", southwestOfSF, northeastOfSF)
        query.findObjects(object : FindListener<User>() {

            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_update_location -> updateLocation()
            R.id.btn_get_location -> getLocation()
            R.id.btn_query_near -> queryNear()
            R.id.btn_query_miles -> queryWithinMiles()
            R.id.btn_query_kilometers -> queryWithinKilometers()
            R.id.btn_query_radians -> queryWithinRadians()
            R.id.btn_query_box -> queryBox()
            else -> {
            }
        }
    }


    /**
     * 获取当前用户的地理位置信息
     */
    private fun getLocation() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user != null) {
            val address = user.address
            if (address == null) {
                Snackbar.make(btn_update_location!!, "地址不存在", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(btn_update_location!!, "查询成功：" + address.latitude + "-" + address.longitude, Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(btn_update_location!!, "请先登录", Snackbar.LENGTH_LONG).show()
        }
    }


    /**
     * 更新当前用户地理位置信息
     */
    private fun updateLocation() {
        //TODO 在实际应用中，此处利用实时定位替换为真实经纬度数据
        val bmobGeoPoint = BmobGeoPoint(116.39727786183357, 39.913768382429105)
        val user = BmobUser.getCurrentUser(User::class.java)
        user.address = bmobGeoPoint
        user.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_update_location!!, "更新成功：" + user.address.latitude + "-" + user.address.longitude, Snackbar.LENGTH_LONG).show()
                } else {
                    Log.e("BMOB", e.toString())
                    Snackbar.make(btn_update_location!!, e.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
