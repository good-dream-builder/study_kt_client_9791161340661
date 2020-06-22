package com.example.simple_shop_aos.product.list

import android.graphics.Typeface
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.view.borderBottom
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.w3c.dom.Text

class ProductListItemUI : AnkoComponent<ViewGroup> {
    // 1
    lateinit var imageView: ImageView
    lateinit var productName: TextView
    lateinit var price: TextView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun createView(ui: AnkoContext<ViewGroup>): View =
        ui.constraintLayout {//2
            topPadding = dip(20)
            bottomPadding = dip(20)
            clipToOutline = false
            background = borderBottom(width = 1)
            lparams(matchParent, wrapContent)

            imageView = imageView {
                id = View.generateViewId() //3
                scaleType = ImageView.ScaleType.CENTER_CROP
            }.lparams(dip(80), dip(80))

            productName = textView("-") {
                id = View.generateViewId()
                textSize = 16f
                typeface = Typeface.DEFAULT_BOLD
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
            }.lparams(0, wrapContent)

            price = textView("-") {
                id = View.generateViewId()
                textColorResource = R.color.colorAccent
                textSize = 14f
            }

            // 4
            applyConstraintSet {
                imageView.id {
                    connect(
                        TOP to TOP of PARENT_ID,
                        START to START of PARENT_ID margin dip(20),
                        BOTTOM to BOTTOM of PARENT_ID
                    )
                }

                productName.id {
                    connect(
                        TOP to TOP of imageView.id margin dip(4),
                        END to END of PARENT_ID margin dip(20),
                        START to END of imageView.id margin dip(10)
                    )
                }

                price.id {
                    connect(
                        TOP to BOTTOM of productName.id margin dip(4),
                        START to END of imageView.id margin dip(10)
                    )
                }
            }
        }
}