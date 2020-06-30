package com.example.simple_shop_aos.product.detail

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView
import kotlin.reflect.KClass

class ProductDetailActivity : BaseActivity<ProductDetailViewModel>() {
    override val viewModelType = ProductDetailViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val viewModel = getViewModel()
        val productId = intent.getLongExtra(PRODUCT_ID, -1) // 2

        viewModel.loadDetail(productId) // 3
        ProductDetailUI(getViewModel()).setContentView(this)
    }

    // 4
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (item.itemId) {
                android.R.id.home -> onBackPressed()
                else -> {
                }
            }
        }
        return true
    }

    companion object {
        val PRODUCT_ID = "productId"
    }
}