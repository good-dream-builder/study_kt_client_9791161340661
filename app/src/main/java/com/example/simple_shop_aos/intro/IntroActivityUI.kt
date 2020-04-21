package com.example.simple_shop_aos.intro

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import com.example.simple_shop_aos.R
import org.jetbrains.anko.*

class IntroActivityUI : AnkoComponent<IntroActivity>{
    override fun createView(ui: AnkoContext<IntroActivity>): View {
        return ui.relativeLayout {
            gravity = Gravity.CENTER

            textView("송꼬") {
                textSize = 24f
                textColorResource = R.color.colorPrimary
                typeface = Typeface.DEFAULT_BOLD
            }
        }
    }

}