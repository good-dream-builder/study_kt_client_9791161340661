package com.example.simple_shop_aos.intro

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.simple_shop_aos.api.ServiceApi
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.setContentView
import kotlin.Exception

class IntroActivity : Activity() {
    companion object {
        val TAG = "IntroActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = IntroActivityUI()
        ui.setContentView(this)

        runBlocking {
            try {
                val response = ServiceApi.instance.hello()
                Log.d(TAG, response.data)
            } catch (e: Exception) {
                Log.e(TAG, "", e)
            }
        }
    }
}