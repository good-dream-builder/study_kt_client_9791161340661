package com.example.simple_shop_aos.registration

import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.api.ApiGenerator
import net.codephobia.ankomvvm.databinding.bindString
import net.codephobia.ankomvvm.databinding.bindStringEntries
import net.codephobia.ankomvvm.databinding.bindUrl
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class ProductRegistrationUI(
    private val viewModel: ProductRegistrationViewModel
) : AnkoComponent<ProductRegistartionActivity> {

    override fun createView(ui: AnkoContext<ProductRegistartionActivity>) = ui.scrollView {
        verticalLayout {
            padding = dip(20)

            // 패딩영역을 넘어서도 보여줄 것인가를 결정
            clipToPadding = false

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                pickImageView(ui, 0)    // 이미지뷰를 그리는 함수
                space().lparams(dip(8))     // LinearLayout 내의 요소들 사이에 공간 추가
                pickImageView(ui, 1)
                space().lparams(dip(8))
                pickImageView(ui, 2)
                space().lparams(dip(8))
                pickImageView(ui, 3)
            }


            textView("상품명 및 설명") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            textInputLayout {
                topPadding = dip(20)
                textInputEditText {
                    hint = "상품명"
                    setSingleLine()
                    bindString(ui.owner, viewModel.productName)

                    // 1
                    textChangedListener {
                        onTextChanged { _, _, _, _ ->
                            viewModel.checkProductNameLength()
                        }
                    }
                }

                textView("0/40") {
                    leftPadding = dip(4)
                    textSize = 12f
                    textColorResource = R.color.colorPrimary
                    bindString(ui.owner, viewModel.productNameLength)
                }
            }

            textInputLayout {
                topPadding = dip(20)
                textInputEditText {
                    hint = "상품 설명"
                    maxLines = 6    // 2
                    bindString(ui.owner, viewModel.description)
                    textChangedListener {
                        onTextChanged { _, _, _, _ ->
                            viewModel.checkDesctirpitonLength()
                        }
                    }
                }

                textView("0/500") {
                    leftPadding = dip(4)
                    textSize = 12f
                    textColorResource = R.color.colorPrimary
                    bindString(ui.owner, viewModel.descriptionLength)
                }
            }

            textView("카테고리") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            verticalLayout {
                topPadding = dip(12)
                bottomPadding = dip(12)
                backgroundColor = 0xEEEEEEEE.toInt()
                // 3
                spinner {
                    bindStringEntries(ui.owner, viewModel.categories)   // 4
                    // 5
                    onItemSelectedListener {
                        onItemSelected { _, _, position, _ ->
                            viewModel.onCategorySelect(position)
                        }
                    }
                }
            }.lparams(matchParent) {
                topMargin = dip(20)
            }

            textView("판매 가격") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            textInputLayout {
                topPadding = dip(20)
                textInputEditText {
                    hint = "Ex) 1000"
                    setSingleLine()
                    inputType = InputType.TYPE_CLASS_NUMBER //  6
                    bindString(ui.owner, viewModel.price)
                }
            }

            button("상품 등록") {
                backgroundColorResource = R.color.colorPrimary
                textColor = Color.WHITE
                onClick { viewModel.register() }
            }.lparams(matchParent, wrapContent) {
                topMargin = dip(40)
            }
        }
    }

    private fun _LinearLayout.pickImageView(
        ui: AnkoContext<ProductRegistartionActivity>,
        imageNum: Int
    ) = imageView(R.drawable.ic_image) {
        scaleType = ImageView.ScaleType.CENTER
        backgroundColor = 0xFFEEEEEE.toInt()
        onClick { viewModel.pickImage(imageNum = imageNum) }
        bindUrl(ui.owner, viewModel.imageUrls[imageNum]) {
            it?.let {
                scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this)
                    .load("${ApiGenerator.HOST}$it")
                    .centerCrop()
                    .into(this)
            }
        }
    }.lparams(dip(60), dip(60))
}