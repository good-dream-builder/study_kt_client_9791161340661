package com.example.simple_shop_aos.product.list

import androidx.paging.PageKeyedDataSource
import com.example.simple_shop_aos.App
import com.example.simple_shop_aos.api.ServiceApi
import com.example.simple_shop_aos.api.response.ApiResponse
import com.example.simple_shop_aos.api.response.ProductListItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.toast

// 1
class ProductListItemDataSource(
    private val categoryId: Int?
) : PageKeyedDataSource<Long, ProductListItemResponse>() {

    /**
     * 초기 데이터를 불러온다
     */
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(Long.MAX_VALUE, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id, it.last().id) //3
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {// 4
                showErrorMessage(response)
            }
        }
    }

    //5
    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.last().id)//6
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    //7
    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, PREV)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id)//8
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    private fun getProducts(id: Long, direction: String) = runBlocking {
        try {
            ServiceApi.instance.getProducts(id, categoryId, direction)
        } catch (e: Exception) {
            ApiResponse.error<List<ProductListItemResponse>>(
                "알 수 없는 오류가 발생했습니다."
            )
        }
    }


    private fun showErrorMessage(
        response: ApiResponse<List<ProductListItemResponse>>
    ) {
        App.instance.toast(
            response.message ?: "알 수 없는 오류가 발생했습니다."
        )
    }

    companion object {
        private const val NEXT = "next"
        private const val PREV = "prev"
    }
}