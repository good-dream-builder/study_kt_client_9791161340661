package com.example.simple_shop_aos.inquiry.registration

import android.os.Bundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView
import kotlin.reflect.KClass

class InquiryRegistrationActivity : BaseActivity<InquiryRegistrationViewModel>() {
    override val viewModelType = InquiryRegistrationViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getLongExtra(PRODUCT_ID, -1)
        val inquiryId = intent.getLongExtra(INQUIRY_ID, -1)
        val inquiryType = intent.getStringExtra(INQUIRY_TYPE)

        val viewModel = getViewModel().apply {
            this.productId = productId
            this.inquiryId = inquiryId
            this.inquiryType = inquiryType
        }

        InquiryRegistrationUI(viewModel).setContentView(this)
    }


    companion object{
        // [상품 문의폼]에 진입할 때 사용할 타입들.
        const val TYPE_QUESTION = "QUESTION"
        const val TYPE_ANSWER = "ANSWER"

        // [상품 문의폼]에 진입할 때 Intent를 통해 전달해주어야 할 데이터 키들.
        const val PRODUCT_ID = "productId"
        const val INQUIRY_ID = "inquiryId"
        const val INQUIRY_TYPE = "inquiryType"
    }
}