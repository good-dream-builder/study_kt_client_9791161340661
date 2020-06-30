package com.example.simple_shop_aos.product

import android.app.Application
import android.content.Intent
import com.example.simple_shop_aos.product.search.ProductSearchActivity
import com.example.simple_shop_aos.registration.ProductRegistartionActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductMainViewModel(app: Application) : BaseViewModel(app) {
    fun openSearchActivity(keyword: String?) {
        keyword?.let {
            startActivity<ProductSearchActivity> {
                putExtra(ProductSearchActivity.KEYWORD, keyword)
            }
        } ?: toast("검색 키워드를 입력해주세요.")
    }

    fun openRegistrationActivity() {
        startActivity<ProductRegistartionActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }
}