package com.example.simple_shop_aos.signup

import android.graphics.Typeface
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simple_shop_aos.R
import net.codephobia.ankomvvm.databinding.bindString
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class SignupActivityUI(
    private val viewModel: SignupViewModel
) : AnkoComponent<SignupActivity> {

    companion object {
        val TAG = "SignupActivityUI"
    }


    override fun createView(ui: AnkoContext<SignupActivity>): View =
        ui.linearLayout {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            padding = dip(20)

            textView("회원가입") {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
                textColorResource = R.color.colorPrimary
            }.lparams(width = matchParent) {
                bottomMargin = dip(50)
            }
            Log.d(TAG, "SignupActivityUI::createView::1")

            textInputLayout {
                textInputEditText {
                    hint = "Email"
                    setSingleLine()
                    bindString(ui.owner, viewModel.email)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }
            Log.d(TAG, "SignupActivityUI::createView::2")

            textInputLayout {
                textInputEditText {
                    hint = "이름"
                    setSingleLine()
                    bindString(ui.owner, viewModel.name)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }
            Log.d(TAG, "SignupActivityUI::createView::3")

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
            Log.d(TAG, "SignupActivityUI::createView::4")

            button("회원가입") {
                onClick { viewModel.signup() }
            }.lparams(width = matchParent)
            Log.d(TAG, "SignupActivityUI::createView::5")

            // Parade -Minal


        }


}