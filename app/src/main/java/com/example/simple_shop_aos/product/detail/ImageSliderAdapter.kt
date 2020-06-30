package com.example.simple_shop_aos.product.detail

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.simple_shop_aos.api.ApiGenerator
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageView

class ImageSliderAdapter : PagerAdapter() { // 1
    var imageUrls: List<String> = listOf()

    // PagerAdapter가 정상독작인지 확인하는 함수
    // 현재 위치한 페이지가 instantiateItem()으로 부터 반환된 뷰인지 비교
    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = imageUrls.size

    // 원하는 뷰를 만들어 주는 함수
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // 이미지 한 개가 표현 되는 뷰
        val view = AnkoContext.create(container.context, container)
            .imageView().apply {
                Glide.with(this)
                    .load("${ApiGenerator.HOST}${imageUrls[position]}")
                    .into(this)
            }
        container.addView(view)
        return view
    }

    // 빈 리스트를 API에서 전달된 이미지들로 교체 해주는 함수
    fun updateItems(items: MutableList<String>) {
        imageUrls = items
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}