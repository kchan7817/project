package com.cookandroid.kotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

            var builder = AlertDialog.Builder(this)
            builder.setTitle("이메일 인증")

            var v1 = layoutInflater.inflate(R.layout.activity_dialog_custom, null)
            builder.setView(v1)
            builder.show()

        }

    }
}