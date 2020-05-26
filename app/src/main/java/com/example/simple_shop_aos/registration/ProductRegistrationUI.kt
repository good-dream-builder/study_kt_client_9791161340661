package com.example.simple_shop_aos.registration

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.verticalLayout

class ProductRegistrationUI(
    private val viewModel: ProductRegistrationViewModel
) : AnkoComponent<ProductRegistartionActivity> {
    override fun createView(ui: AnkoContext<ProductRegistartionActivity>) = ui.scrollView {
        verticalLayout { }
    }
}