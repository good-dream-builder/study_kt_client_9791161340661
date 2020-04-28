package com.example.simple_shop_aos.signup

import android.os.Parcel
import android.os.Parcelable
import net.codephobia.ankomvvm.components.BaseActivity

class SignupActivity() : BaseActivity<SignupViewModel>() {
    override val viewModelType = SignupViewModel::class
}