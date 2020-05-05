package com.example.simple_shop_aos.signin

import android.graphics.Color
import android.graphics.Typeface
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.signup.SignupActivity
import net.codephobia.ankomvvm.databinding.bindString
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class SigninActivityUI(
    private val viewModel: SigninViewModel
) : AnkoComponent<SigninActivity> {

    companion object {
        val TAG = "SigninActivityUI"
    }

    override fun createView(ui: AnkoContext<SigninActivity>): View =
        ui.linearLayout {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            padding = dip(20)

            textView("로그인") {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
                textColorResource = R.color.colorPrimary
            }.lparams(width = matchParent) {
                bottomMargin = dip(50)
            }
            Log.d(TAG, "SigninActivityUI::createView::1")

            textInputLayout {
                textInputEditText {
                    hint = "Email"
                    setSingleLine()
                    bindString(ui.owner, viewModel.email)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }
            Log.d(TAG, "SigninActivityUI::createView::2")

            textInputLayout {
                textInputEditText {
                    hint = "비밀번호"
                    setSingleLine()
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    bindString(ui.owner, viewModel.password)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }
            Log.d(TAG, "SigninActivityUI::createView::3")

            button("로그인") {
                onClick { viewModel.signin() }
            }.lparams(width = matchParent)
            Log.d(TAG, "SigninActivityUI::createView::4")

            button("회원가입") {
                backgroundColor = Color.TRANSPARENT
                textColorResource = R.color.colorPrimary
                onClick { ui.startActivity<SignupActivity>() }
            }.lparams(width = matchParent)
            Log.d(TAG, "SigninActivityUI::createView::4")


        }


}