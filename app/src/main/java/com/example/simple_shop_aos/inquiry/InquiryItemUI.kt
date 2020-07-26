package com.example.simple_shop_aos.inquiry

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.simple_shop_aos.common.Prefs
import com.example.simple_shop_aos.view.borderBottom
import org.jetbrains.anko.*

class InquiryItemUI : AnkoComponent<ViewGroup> {

    var productOwnerId: Long? = null

    lateinit var requestUserName: TextView
    lateinit var productName: TextView
    lateinit var productOwnerName: TextView
    lateinit var question: TextView
    lateinit var answerButton: Button
    lateinit var answer: TextView
    lateinit var answerWrapper: LinearLayout

    override fun createView(ui: AnkoContext<ViewGroup>) =
        ui.verticalLayout {
            lparams(matchParent)
            background = borderBottom(width = 1)
            padding = dip(20)

            requestUserName = textView {
                typeface = Typeface.DEFAULT_BOLD
                textSize = 16f
            }

            linearLayout {
                topPadding = dip(5)
                textView("Q.") {
                    rightPadding = dip(5)
                    typeface = Typeface.DEFAULT_BOLD
                    textSize = 16f
                }
                question = textView("") {
                    typeface = Typeface.DEFAULT_BOLD
                    textSize = 16f
                }
            }

            productName = textView {
                typeface = Typeface.DEFAULT_BOLD
                textSize = 14f
                textColor = Color.LTGRAY
            }

            answerButton = button("답변") {
                visibility = View.GONE
            }.lparams {
                gravity = Gravity.END
            }

            answerWrapper = verticalLayout {
                topPadding = dip(15)
                productOwnerName = textView {
                    typeface = Typeface.DEFAULT_BOLD
                    textSize = 16f
                }
                linearLayout {
                    topPadding = dip(5)
                    textView("A.") {
                        rightPadding = dip(5)
                        textSize = 16f
                    }
                    answer = textView("") {
                        textSize = 16f
                    }
                }
            }
        }

    // 뷰모델 기반의 데이터바인딩을 이용하지 않고
    // UI값을 직접 변경
    fun invalidate() {
        if (productOwnerId == Prefs.userId && answer.text.isNullOrEmpty()) {
            answerButton.visibility = View.VISIBLE
        }
        if (answer.text.isNullOrEmpty()) {
            answerWrapper.visibility = View.GONE
        }
    }
}