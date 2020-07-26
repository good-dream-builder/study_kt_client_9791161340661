package com.example.simple_shop_aos.inquiry.myinquiry

import android.view.View
import android.view.ViewParent
import androidx.viewpager.widget.ViewPager
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.view.borderBottom
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.support.v4.viewPager

class MyInquiryUI : AnkoComponent<MyInquiryActivity> {
    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager

    override fun createView(ui: AnkoContext<MyInquiryActivity>) =
        ui.verticalLayout {
            tablayout = themedTabLayout(
                R.style.Widget_MaterialComponents_TabLayout
            ) {
                bottomPadding = dip(1)
                tabGravity = TabLayout.GRAVITY_FILL
                background = borderBottom(width = dip(1))
            }.lparams(matchParent, wrapContent) {
                weight = 0f
            }

            viewpager = viewPager {
                id = View.generateViewId()
            }.lparams(matchParent, matchParent) {
                weight = 1f
            }
        }
}