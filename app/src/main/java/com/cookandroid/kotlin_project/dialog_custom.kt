package com.cookandroid.kotlin_project

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cookandroid.kotlin_project.databinding.ActivityDialogCustomBinding
import kotlin.concurrent.thread

class dialog_custom : AppCompatActivity() {

    var total = 180
    var started = false
    val binding = ActivityDialogCustomBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_custom)

        setContentView(binding.root)

        start()
    }

    fun start(){
        started = true
        thread(start = true){
            while(started){
                Thread.sleep(10000)
                total=total-1
                binding.textTimer.text="$total 초"

                if (total == 0){
                    started = false
                }

            }
        }

    }
}