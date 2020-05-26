package com.example.simple_shop_aos.registration

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simple_shop_aos.api.response.ApiResponse
import com.example.simple_shop_aos.api.response.ProductImageUploadResponse
import com.example.simple_shop_aos.category.categoryList
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

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
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        intent.resolveActivity(app.packageManager)?.let {
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
        }
        currentImageNum = imageNum
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (resultCode) {
            REQUEST_PICK_IMAGES -> data?.let { uploadImage(it) }
        }

    }

    private fun uploadImage(intent: Intent) {
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
        const val REQUEST_PICK_IMAGES = 0
    }
}