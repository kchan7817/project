package com.cookandroid.kotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.cookandroid.kotlin_project.databinding.ActivityJoinBinding
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.cookandroid.kotlin_project.LoginResponse
import java.lang.reflect.Array.get

class JoinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val binding = ActivityJoinBinding.inflate(layoutInflater) // java의 findviewbyid 작업을 안해도됨

        setContentView(binding.root)

        var realname = binding.edtName.text.toString()
        var birthday  = binding.edtBirthday.text.toString()
        var username = binding.edtJoinId.text.toString()
        var password = binding.edtPasswd.text.toString()
        var email = binding.edtEmail.text.toString()


        val retrofit = Retrofit.Builder()
            .baseUrl("http://kangtong1105.codns.com:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(signservice::class.java)
        val userInfo: Call<LoginResponse> = service.register(LoginResponse(birthday="$birthday", email="$email", username="$username", realname="$realname", password = "$password"))

        binding.btnCheck.setOnClickListener{

            userInfo.enqueue(object :Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val result = response.body()
                    Log.d("회원가입성공","$result")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("회원가입실패","${t.localizedMessage}")
                }
            })



            /*var builder = AlertDialog.Builder(this)
            builder.setTitle("이메일 인증")

            var v1 = layoutInflater.inflate(R.layout.activity_dialog_custom, null)
            builder.setView(v1)
            builder.setPositiveButton("확인",null)
            builder.setNegativeButton("취소",null)

            builder.show()*/

        }

    }
}

interface signservice{
    @Headers("content-type: application/json",
             "accept: application/json")
    @POST("/auth/signup")
    fun register(@Body userInfo: LoginResponse) : Call<LoginResponse>
}