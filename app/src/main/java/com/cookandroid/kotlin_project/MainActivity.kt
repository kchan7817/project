package com.cookandroid.kotlin_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cookandroid.kotlin_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent: Intent = Intent(this, JoinActivity::class.java)//intent 선언
        val binding = ActivityMainBinding.inflate(layoutInflater)// java의 findviewbyid 작업을 안해도됨

        setContentView(binding.root)

        binding.btnJoin.setOnClickListener{
            startActivity(intent) // binding 쓰는법
        }

    }
}