package com.example.simple_shop_aos.product.list

import android.app.Application
import androidx.paging.DataSource
import com.example.simple_shop_aos.api.response.ProductListItemResponse
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

class ProductListViewModel(
    app: Application
) : BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemClickListener {

    var categoryId: Int = -1 // 1
    var products = buildPagedList() // 2

    override fun createDataSource(): DataSource<Long, ProductListItemResponse> {
        if (categoryId == -1)
            error(
                "categoryId가 설정되지 않았습니다.",
                IllegalStateException("categoryId is -1")
            )
        return ProductListItemDataSource(categoryId)
    }

    // 상품 목록에서 상품을 클릭했을 때 동작
    override fun onClickProduct(productId: Long?) {
//        startActivity<ProductDe> {  }
    }
}
