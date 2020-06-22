package com.example.simple_shop_aos.product.list

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import net.codephobia.ankomvvm.databinding.bindPagedList
import net.codephobia.ankomvvm.databinding.bindVisibility
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ProductListUI(
    private val viewModel: ProductListViewModel
) : AnkoComponent<ProductListFragment> {
    override fun createView(ui: AnkoContext<ProductListFragment>) =

        ui.verticalLayout {

            recyclerView {
                // 아이템들을 어떻게 배치 할 것인가
                // LinearLayoutManager = 일렬, GridLayoutManager = 격자
                layoutManager = LinearLayoutManager(ui.ctx)
                
                // 데이터를 바인딩 시킬 어뎁터
                adapter = ProductListPagedAdapter(viewModel)

                
                lparams(matchParent, matchParent)
                
                // 상품이 없을 때 RecyclerView를 숨김
                bindVisibility(ui.owner, viewModel.products) {  //4
                    it.isNotEmpty()
                }
                
                // LiveData<PagedList<T>> 타입의 객체를 바인딩 하는 함수
                bindPagedList(
                    ui.owner,
                    ProductListPagedAdapter(viewModel),
                    viewModel.products
                )
            }

            textView("상품이 없습니다.") {
                gravity = Gravity.CENTER
                bindVisibility(ui.owner, viewModel.products) {
                    it.isEmpty()
                }
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.CENTER
            }

        }
}