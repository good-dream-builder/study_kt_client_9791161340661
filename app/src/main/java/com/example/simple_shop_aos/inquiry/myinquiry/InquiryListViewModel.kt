package com.example.simple_shop_aos.inquiry.myinquiry

import android.app.Application
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.simple_shop_aos.api.response.InquiryResponse
import com.example.simple_shop_aos.common.Prefs
import com.example.simple_shop_aos.inquiry.InquiryDataSource
import com.example.simple_shop_aos.inquiry.InquiryPagedAdapter
import com.example.simple_shop_aos.inquiry.registration.InquiryRegistrationActivity
import com.example.simple_shop_aos.product.detail.ProductDetailActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class InquiryListViewModel(
    app: Application
) : BaseViewModel(app),
    InquiryPagedAdapter.InquiryItemClickListener {

    var page: InquiryPage? = null
    var requestUserId: Long? = null
    var productOwnerId: Long? = null

    val inquiries by lazy {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        // 탭에 따라 다른 담당 변수에 userId를 대입해준다.
        val factory =
            object : DataSource.Factory<Long, InquiryResponse>() {
                override fun create(): DataSource<Long, InquiryResponse> {
                    when (page) {
                        InquiryPage.MY_INQUIRY -> requestUserId = Prefs.userId
                        InquiryPage.PRODUCT_INQUIRY -> productOwnerId = Prefs.userId
                    }
                    return InquiryDataSource(
                        requestUserId = requestUserId,
                        productOwnerId = productOwnerId
                    )
                }
            }
        LivePagedListBuilder(factory, config).build()
    }

    // 상품 상세로 이동
    override fun onClickInquiry(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run {
            startActivity<ProductDetailActivity>(){
                putExtra(
                    ProductDetailActivity.PRODUCT_ID,
                    productId
                )
            }
        }
    }

    // 답변 작성으로 이동
    override fun onClickAnswer(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run { 
            startActivity<InquiryRegistrationActivity>{
                putExtra(InquiryRegistrationActivity.PRODUCT_ID, productId)
                putExtra(InquiryRegistrationActivity.INQUIRY_ID, id)
                putExtra(InquiryRegistrationActivity.INQUIRY_TYPE, InquiryRegistrationActivity.TYPE_ANSWER)
            }
        }
    }
}