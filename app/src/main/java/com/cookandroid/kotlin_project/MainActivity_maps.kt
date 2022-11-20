package com.cookandroid.kotlin_project

import SocketApplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.cookandroid.kotlin_project.BuildConfig.api_key
import com.cookandroid.kotlin_project.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity_maps : AppCompatActivity(), OnMapReadyCallback {

    lateinit var drawerLayout: DrawerLayout

    var TAG: String = "로그"
    val client = OkHttpClient()
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maps)

        val toolbar: Toolbar =findViewById(R.id.toolbar)
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawerLayout)

        //액션바에 toolbar 셋팅
        setSupportActionBar(toolbar)

        //액션바 생성
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar

        //뒤로가기 버튼 생성
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //뒤로가기 버튼 이미지 변경
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)




        //네비게이션뷰 아이템 선택 이벤트
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId){
                        R.id.senior_home -> {
                            item.isChecked = true
                            var intent = Intent(this@MainActivity_maps,SeniorActivity::class.java)
                            startActivity(intent)

                            return true
                        }

                        R.id.nav_gallery -> {
                            item.isChecked = true
                            displayMessage("selected gallery")
                            drawerLayout.closeDrawers()
                            return true
                        }

                        R.id.nav_slideshow -> {
                            item.isChecked = true
                            displayMessage("selected slideshow")
                            drawerLayout.closeDrawers()
                            return true
                        }
                        else -> {
                            return true
                        }
                    } //when
                } //onNavigationItemSelected
            } //NavigationView.OnNavigationItemSelectedListener
        ) //setNavigationItemSelectedListener


        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(api_key)

        // 뷰 역할을 하는 프래그먼트 객체 얻기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        // 인터페이스 역할을 하는 NaverMap 객체 얻기
        // 프래그먼트(MapFragment)의 getMapAsync() 메서드로 OnMapReadyCallback 을 등록하면 비동기로 NaverMap 객체를 얻을 수 있다고 한다.
        // NaverMap 객체가 준비되면 OnMapReady() 콜백 메서드 호출
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)


    }

    private fun displayMessage(message:String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return true

    }

    //메뉴 선택 이벤트onOptionsItemSelected
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.condition -> {
                return true
            }
            android.R.id.home -> {
                //drawerLayout 펼치기
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }

            R.id.mypage -> {
                var intent=Intent(this,MypageActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.location -> {
                val request: Request = Request
                    .Builder()
                    .url("ws://kangtong1105.codns.com:8080/ws/chat")
                    .build()
                val listener = SocketApplication()
                val wss: WebSocket = client.newWebSocket(request, listener)

                return true
            }

            R.id.logout -> {
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
                MySharedPreferences.clearUser(this)
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }
            R.id.group -> {
                return true
            }
            else ->  return super.onOptionsItemSelected(item)
        }
    }


    override fun onMapReady(naverMap: NaverMap) {
        Log.d(TAG, "MainActivity - onMapReady")
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.d(TAG, "MainActivity - onRequestPermissionsResult")
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val API_KEY = BuildConfig.API_KEY
    }

}