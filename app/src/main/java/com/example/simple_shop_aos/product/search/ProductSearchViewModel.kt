package com.example.simple_shop_aos.product.search

import android.app.Application
import android.content.Intent
import androidx.paging.DataSource
import com.example.simple_shop_aos.api.response.ProductListItemResponse
import com.example.simple_shop_aos.product.detail.ProductDetailActivity
import com.example.simple_shop_aos.product.list.ProductListItemDataSource
import com.example.simple_shop_aos.product.list.ProductListPagedAdapter
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

class ProductSearchViewModel(
    app: Application
) : BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemClickListener {

    var keyword: String? = null // 1
    var products = buildPagedList() // 2

    override fun createDataSource() = ProductListItemDataSource(null, keyword)

    // 상품 목록에서 상품을 클릭했을 때 동작
    override fun onClickProduct(productId: Long?) {
        startActivity<ProductDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }
}
