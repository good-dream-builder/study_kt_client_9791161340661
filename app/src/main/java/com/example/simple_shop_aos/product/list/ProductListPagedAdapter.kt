package com.example.simple_shop_aos.product.list

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simple_shop_aos.api.ApiGenerator
import com.example.simple_shop_aos.api.response.ProductListItemResponse
import com.example.simple_shop_aos.common.paging.LiveDataPagedListBuilder
import com.example.simple_shop_aos.product.ProductStatus
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.NumberFormat

// 1
class ProductListPagedAdapter(
    private val listener: OnItemClickListener
) : PagedListAdapter<ProductListItemResponse, ProductListPagedAdapter.ProductItemViewHolder>(
    DIFF_CALLBACK   // 2
) {

    // 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductItemViewHolder(parent, listener)

    // 4
    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 5
    @SuppressLint("NewApi")
    class ProductItemViewHolder(
        parent: ViewGroup,
        private val listener: OnItemClickListener,
        private val ui: ProductListItemUI = ProductListItemUI()
    ) : RecyclerView.ViewHolder(
        ui.createView(AnkoContext.create(parent.context, parent))
    ) {

        var productId: Long? = null

        init {
            itemView.onClick { listener.onClickProduct(productId) } // 6
        }

        // 7
        fun bind(item: ProductListItemResponse?) = item?.let {
            this.productId = item.id
            val soldOutString =
                if (ProductStatus.SOLD_OUT == item.status) "(품절)" else ""
            val commaSeparatedPrice = NumberFormat.getNumberInstance().format(item.price)

            ui.productName.text = item.name
            ui.price.text = "₩$commaSeparatedPrice $soldOutString"

            Glide.with(ui.imageView)
                .load("${ApiGenerator.HOST}${item.imagePaths.firstOrNull()}")
                .centerCrop()
                .into(ui.imageView)
        }
    }

    companion object {
        // 8
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ProductListItemResponse>() {
                override fun areItemsTheSame(
                    oldItem: ProductListItemResponse,
                    newItem: ProductListItemResponse
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ProductListItemResponse,
                    newItem: ProductListItemResponse
                ) = oldItem.toString() == newItem.toString()
            }
    }

    // 9
    interface OnItemClickListener {
        fun onClickProduct(productId: Long?)
    }

    // 10
    interface ProductLiveDataBuilder : LiveDataPagedListBuilder<Long, ProductListItemResponse>
}