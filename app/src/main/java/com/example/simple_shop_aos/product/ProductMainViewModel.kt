package com.example.simple_shop_aos.product

import android.app.Application
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductMainViewModel(app: Application) : BaseViewModel(app) {
    fun openRegistrationActivity() {
        // todo 상품 등록 UI가 준비되면 해당 액티비티를 열도록 수정
        toast("openRegistrationActivity")
    }
}