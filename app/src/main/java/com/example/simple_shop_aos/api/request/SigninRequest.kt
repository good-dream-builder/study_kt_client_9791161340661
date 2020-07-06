package com.example.simple_shop_aos.api.request

import android.util.Patterns
import com.example.simple_shop_aos.common.Prefs

class SigninRequest(
    val email: String?,
    val password: String?,
    val fcmToken: String? = Prefs.fcmToken
) {
    fun isNotValidEmail() =
        email.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isNotValidPassword() =
        password.isNullOrBlank() || password.length !in 8..20
}