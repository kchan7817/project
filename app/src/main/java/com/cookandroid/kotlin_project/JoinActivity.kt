package com.cookandroid.kotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.cookandroid.kotlin_project.databinding.ActivityJoinBinding
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.lang.reflect.Array.get

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val binding = ActivityJoinBinding.inflate(layoutInflater) // java의 findviewbyid 작업을 안해도됨

        setContentView(binding.root)

        var name = binding.edtName.text.toString()
        var birthday  = binding.edtBirthday.text.toString()
        var id = binding.edtJoinId.text.toString()
        var pw = binding.edtPasswd.text.toString()
        var pwck = binding.edtPasswdCheck.text.toString()
        var email = binding.edtEmail.text.toString()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://kangtong1105.codns.com:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(signservice::class.java)

        binding.btnCheck.setOnClickListener{

            service.register(name,birthday,id,pw,pwck,email).enqueue(object :Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val result = response.body()
                    Log.d("로그인","${result}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("로그인","${t.localizedMessage}")
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
    @FormUrlEncoded
    @POST("/auth/signup")
    fun register(@Field("name") name:String,
                 @Field("birthday") birthday:String,
                 @Field("id") id:String,
                 @Field("pw") pw:String,
                 @Field("pwck") pwck:String,
                 @Field("email") email:String) : Call<LoginResponse>
}