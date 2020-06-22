package com.example.simple_shop_aos.product.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.simple_shop_aos.category.categoryList

class ProductListPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val fragments = categoryList.map {
        ProductListFragment.newInstance(it.id, it.name)
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = getItem(position).title
}