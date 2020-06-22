package com.example.simple_shop_aos.common.paging

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * PageKeyedDataSource를 이용해 LiveData<PagedList>를 쉽게 만둘 수 있도록 정의한
 * 인터페이스
 */
interface LiveDataPagedListBuilder<K, T> {
    fun createDataSource(): DataSource<K, T>

    private fun factory() = object : DataSource.Factory<K, T>() {
        override fun create(): DataSource<K, T> = createDataSource()
    }

    private fun config() = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    fun buildPagedList() = LivePagedListBuilder(factory(), config()).build()
}