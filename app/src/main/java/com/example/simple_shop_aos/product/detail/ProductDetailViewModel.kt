package com.example.simple_shop_aos.product.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simple_shop_aos.api.ServiceApi
import com.example.simple_shop_aos.api.response.ApiResponse
import com.example.simple_shop_aos.api.response.ProductResponse
import com.example.simple_shop_aos.product.ProductStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.databinding.addAll
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error
import java.text.NumberFormat
import kotlin.Exception

class ProductDetailViewModel(app: Application) : BaseViewModel(app) {
    val productId: Long? = null

    val productName = MutableLiveData("-")
    val description = MutableLiveData("")
    val price = MutableLiveData("-")
    val imageUrls: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    // suspend 함수를 호출하기 때문에 코루틴으로 처리
    fun loadDetail(id: Long) = viewModelScope.launch(Dispatchers.Main) {
        try {
            val response = getProduct(id)
            if (response.success && response.data != null) {
                updateViewData(response.data)
            } else {
                toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    private suspend fun getProduct(id: Long) = try {
        ServiceApi.instance.getProduct(id)
    } catch (e: Exception) {
        error("상품 정보를 가져오는 중 오류 발생.", e)
        ApiResponse.error<ProductResponse>("상품 정보를 가져오는 중 오류가 발생했습니다.")
    }

    // 상품 정보에 따라 뷰를 그려줌
    private fun updateViewData(product: ProductResponse) {
        val commaSeparatedPrice = NumberFormat.getInstance().format(product.price)
        val soldOutString = if (ProductStatus.SOLD_OUT == product.status) "(품절)" else ""

        productName.value = product.name
        description.value = product.description
        price.value = "₩$commaSeparatedPrice $soldOutString"
        imageUrls.addAll(product.imagePaths)
    }

    // 상품 문의
    fun openInquiryActivity() {
        toast("상품문의 - productId = $productId")
    }

}