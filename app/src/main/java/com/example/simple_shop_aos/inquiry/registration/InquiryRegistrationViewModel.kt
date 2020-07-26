package com.example.simple_shop_aos.inquiry.registration

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.simple_shop_aos.api.ServiceApi
import com.example.simple_shop_aos.api.request.InquiryRequest
import com.example.simple_shop_aos.api.response.ApiResponse
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import retrofit2.Response
import java.lang.IllegalStateException

class InquiryRegistrationViewModel(app: Application) : BaseViewModel(app) {
    var productId = -1L
    var inquiryId: Long = -1L
    var inquiryType: String? = null

    val content = MutableLiveData("")

    suspend fun register() {
        val response = registerInquiry()
        if (!response.success) {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        } else {
            toast("등록되었습니다.")
            finishActivity(RESULT_CODE_REGISTER_INQUIRY) {} // 문의 등록 후 상황에 따라 대처하기 위해 결과 코드를 저장해 액티비티 종료
        }
    }

    private suspend fun registerInquiry() = try {
        val request = validateParamsAndMakeRequest()
        if (request.isContentEmpty) ApiResponse.error("내용을 입력해주세요.")
        else ServiceApi.instance.registerInquiry(request)
    } catch (e: Exception) {
        ApiResponse.error<Response<Void>>("알 수 없는 오류가 발생했습니다.")
    }

    private fun validateParamsAndMakeRequest(): InquiryRequest {
        val type = inquiryType ?: throw IllegalStateException("inquiryType null")

        val pid = productId.let {
            if (it == -1L) throw IllegalStateException("잘못된 productId.")
            else it
        }

        val questionId = if (inquiryId == -1L) null else inquiryId

        return InquiryRequest(type, questionId, pid, content.value)
    }

    companion object {
        const val RESULT_CODE_REGISTER_INQUIRY = 1
    }
}