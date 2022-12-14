package com.cookandroid.kotlin_project

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContentProviderCompat.requireContext

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            //일정 시간이 지나면 MainActivity로 이동
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.hold)

            /*fun startActivity(activity: Activity) {
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            } */
            /*
            fun endActivity(activity: Activity) {
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            } */
            //이전 키를 눌렀을 때 스플래쉬 스크린 화면으로 이동을 방지하기 위해
            //이동한 다음 사용안함으로 finish 처리
            finish()

        }, 800) //시간 0.5초 이후 실행
    }
}