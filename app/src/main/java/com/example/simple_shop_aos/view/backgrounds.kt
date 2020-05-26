package com.example.simple_shop_aos.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

private fun borderBG(
    borderColor: String = "#1F000000",
    bgColor: String = "#FFFFFF",
    borderLeft: Int = 0,
    borderTop: Int = 0,
    borderRight: Int = 0,
    borderBottom: Int = 0
): LayerDrawable {
    val layerDrawable = arrayOf<Drawable>(
        ColorDrawable(Color.parseColor(borderColor)),
        ColorDrawable(Color.parseColor(bgColor))
    ).let(::LayerDrawable)

    layerDrawable.setLayerInset(
        1,
        borderLeft,
        borderTop,
        borderRight,
        borderBottom
    )

    return layerDrawable
}

fun borderLeft(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderLeft = width
)

fun borderTop(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderTop = width
)

fun borderRight(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderRight = width
)
fun borderBottom(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderBottom = width
)