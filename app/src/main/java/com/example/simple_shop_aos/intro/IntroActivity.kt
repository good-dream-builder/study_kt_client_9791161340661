package com.example.simple_shop_aos.intro

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.simple_shop_aos.api.ServiceApi
import com.example.simple_shop_aos.signup.SignupActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import kotlin.Exception

class IntroActivity : Activity() {
    companion object {
        val TAG = "IntroActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "IntroActivity::onCreate")

        val ui = IntroActivityUI()
        ui.setContentView(this)
        
        // 메인스레드에서 비동기 작업을 시작
        // 코루틴 블룩 내부에서 회원가입 화면으로 전환
        GlobalScope.launch {
            delay(1000) // 코루틴 내부에서 1초 쉼
            Log.d(TAG, "IntroActivity::onCreate::GlobalScope")
            startActivity<SignupActivity>()
            finish()    // 뒤로가기시 다시 IntroActivity로 돌아오지 못하도록 종료시킴
        }

        /*
        runBlocking {
            try {
                val response = ServiceApi.instance.hello()
                Log.d(TAG, response.data)
            } catch (e: Exception) {
                Log.e(TAG, "", e)
            }
        }
        */
    }
}