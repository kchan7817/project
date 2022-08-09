package com.cookandroid.kotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cookandroid.kotlin_project.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val binding = ActivityJoinBinding.inflate(layoutInflater) // java의 findviewbyid 작업을 안해도됨

        setContentView(binding.root)

        binding.btnCheck.setOnClickListener{
            var name = binding.edtName.text.toString()
            var birthday  = binding.edtBirthday.text.toString()
            var id = binding.edtJoinId.text.toString()
            var pw = binding.edtPasswd.text.toString()
            var pwck = binding.edtPasswdCheck.text.toString()
            var email = binding.edtEmail.text.toString()

        }

    }
}