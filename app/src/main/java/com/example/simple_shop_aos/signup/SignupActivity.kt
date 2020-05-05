package com.example.simple_shop_aos.signup

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class SignupActivity() : BaseActivity<SignupViewModel>() {
    companion object {
        val TAG = "SignupActivity"
    }

    override val viewModelType = SignupViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "SignupActivity::onCreate")
        SignupActivityUI(getViewModel())
            .setContentView(this)
    }
}