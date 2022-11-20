package com.cookandroid.kotlin_project

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity_recognizer : AppCompatActivity() {

    lateinit var resultText: TextView
    override fun onCreate(savedInstances: Bundle?) {
        val savedInstancesState = null
        super.onCreate(savedInstancesState)
        setContentView(R.layout.activity_recognizer)

        //초기화
        resultText=findViewById(R.id.result_text)

        //음성전환 버튼 이벤트
        val speakImage: ImageView=findViewById(R.id.speak_image)
        speakImage.setOnClickListener {
            val intent: Intent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            //음성 인식기에 사용되는 음성모델 정보 설정
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            //음성 인식기에 인식되는 언어 설정
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.KOREAN)

            if(intent.resolveActivity(packageManager) != null){
                //음성->텍스트전환
                activityResult.launch(intent)
            }else{
                Toast.makeText(this, "당신의 장비가 음성을 텍스트로 변경하지 못합니다.",
                    Toast.LENGTH_SHORT).show()
            }
        }
   }
    private val activityResult:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){


                if(it.resultCode== RESULT_OK && it.data != null){
                    var result: ArrayList<String> =
                        it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */

                    //음성->텍스트에 보여주기
                    resultText.text = result[0]
                }
    }

}