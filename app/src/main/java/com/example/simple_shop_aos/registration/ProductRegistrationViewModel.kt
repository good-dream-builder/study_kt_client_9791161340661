package com.example.simple_shop_aos.registration

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simple_shop_aos.api.request.ProductRegistrationRequest
import com.example.simple_shop_aos.api.response.ApiResponse
import com.example.simple_shop_aos.api.response.ProductImageUploadResponse
import com.example.simple_shop_aos.category.categoryList
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import retrofit2.Response

class ProductRegistrationViewModel(app: Application) : BaseViewModel(app) {

    val imageUrls: List<MutableLiveData<String?>> = listOf(
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?)
    )

    val imageIds: MutableList<Long?> =
        mutableListOf(null, null, null, null)

    val productName = MutableLiveData("")
    val description = MutableLiveData("")
    val price = MutableLiveData("")
    val categories = MutableLiveData(categoryList.map { it.name })
    var categoryIdSelected: Int? = categoryList[0].id

    val descriptionLimit = 500
    val productNameLimit = 40

    val productNameLength = MutableLiveData("0/$productNameLimit")
    val descriptionLength = MutableLiveData("0/$descriptionLimit")

    var currentImageNum = 0


    /**
     * 등록 버튼을 눌렀을 때 실행
     * - 상품등록 api를 호출하기 때문에 suspend function으로 정의
     * - anko의 onClick 콜백은 코루틴 스코프 내에서 실행되므로 suspend 여도 상관 없음.
     */
    suspend fun register() {
        val request = extractRequest()
        val response = ProductRegistrar().register(request)
        onRegistrationResponse(response)
    }

    private fun extractRequest(): ProductRegistrationRequest =
        ProductRegistrationRequest(
            productName.value,
            description.value,
            price.value?.toIntOrNull(),
            categoryIdSelected,
            imageIds
        )

    private fun onRegistrationResponse(response: ApiResponse<Response<Void>>) {
        if (response.success) {
            // confirm =  BaseViewModel에 정의된 Alert 다이얼로그를 띄워주는 함수
            confirm("상품이 등록되었습니다.") {
                finishActivity()
            }
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    fun checkProductNameLength() {
        productName.value?.let {
            if (it.length > productNameLimit) {
                productName.value = it.take(productNameLimit)
            }
            productNameLength.value = "${productName.value?.length}/$productNameLimit"
        }
    }

    fun checkDesctirpitonLength() {
        description.value?.let {
            if (it.length > descriptionLimit) {
                description.value = it.take(descriptionLimit)
            }
            descriptionLength.value = "${description.value?.length}/$descriptionLimit"
        }
    }

    fun onCategorySelect(position: Int) {
        categoryIdSelected = categoryList[position].id
    }


    fun pickImage(imageNum: Int) {
        Log.d(TAG, "pickImage::imageNum = ${imageNum}")

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        intent.resolveActivity(app.packageManager)?.let {
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
        }
        currentImageNum = imageNum
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult::requestCode = ${requestCode}")
        Log.d(TAG, "onActivityResult::resultCode = ${resultCode}")
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_PICK_IMAGES -> data?.let { uploadImage(it) }
        }

    }

    private fun uploadImage(intent: Intent) {
        Log.d(TAG, "uploadImage")
        getContent(intent.data)?.let { imageFile ->
            viewModelScope.launch {
                val response = ProductImageUploader().upload(imageFile)
                onImageUploadResponse(response)
            }
        }

    }

    private fun onImageUploadResponse(response: ApiResponse<ProductImageUploadResponse>) {
        if (response.success && response.data != null) {
            imageUrls[currentImageNum].value = response.data.filePath
            imageIds[currentImageNum] = response.data.productImageId
        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    companion object {
        val TAG = "***********"
        const val REQUEST_PICK_IMAGES = 12345
    }

}