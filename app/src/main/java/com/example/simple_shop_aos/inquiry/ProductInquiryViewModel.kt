package com.example.simple_shop_aos.inquiry

import android.app.Application
import android.content.Intent
import androidx.paging.DataSource
import com.example.simple_shop_aos.App
import com.example.simple_shop_aos.api.response.InquiryResponse
import com.example.simple_shop_aos.inquiry.registration.InquiryRegistrationActivity
import com.example.simple_shop_aos.inquiry.registration.InquiryRegistrationViewModel
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

class ProductInquiryViewModel(app: Application) :
    BaseViewModel(app),
    InquiryPagedAdapter.InquiryLiveDataBuilder,
    InquiryPagedAdapter.InquiryItemClickListener {

    var productId: Long = -1
    var inquiries = buildPagedList()

    override fun createDataSource(): DataSource<Long, InquiryResponse> {
        if (productId == -1L) {
            error("productId가 설정되지 않았습니다.", IllegalStateException("productId is -1"))
        }
        return InquiryDataSource(productId)
    }

    // 4
    override fun onClickInquiry(inquiryResponse: InquiryResponse?) {
        // do nothing
    }

    override fun onClickAnswer(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run { inquiry("ANSWER", id) }
    }

    // 5
    fun inquiry(type: String, inquiryId: Long? = null) {
        val intent = Intent(
            App.instance,
            InquiryRegistrationActivity::class.java
        ).apply {
            putExtra(InquiryRegistrationActivity.PRODUCT_ID, productId)
            putExtra(InquiryRegistrationActivity.INQUIRY_ID, inquiryId)
            putExtra(InquiryRegistrationActivity.INQUIRY_TYPE, type)
        }
        startActivityForResult(intent, REQUEST_CODE_REGISTER_INQUIRY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_REGISTER_INQUIRY -> {
                if (resultCode == InquiryRegistrationViewModel.RESULT_CODE_REGISTER_INQUIRY) {

                    // 문의가 등록된 후 문의목록을 닫아준다.
                    finishActivity()
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_REGISTER_INQUIRY = 1
    }
}