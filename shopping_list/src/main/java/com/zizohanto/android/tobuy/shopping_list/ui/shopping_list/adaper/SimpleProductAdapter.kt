package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper

import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list.ProductItem
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.SimpleProductAdapter.SimpleProductViewHolder
import javax.inject.Inject

class SimpleProductAdapter @Inject constructor(
    products: List<ProductModel>,
    private val clickListener: (v: View) -> Unit
) : ListAdapter<ProductsViewItem, SimpleProductViewHolder>(diffUtilCallback) {

    init {
        submitList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleProductViewHolder {
        return SimpleProductViewHolder(
            ItemProductBinding.bind(parent.inflate(R.layout.item_product)),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: SimpleProductViewHolder, position: Int) {
        holder.bind(
            getItem(holder.bindingAdapterPosition) as ProductModel
        )
    }

    class SimpleProductViewHolder(
        private val binding: ItemProductBinding,
        clickListener: (v: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(clickListener)
        }

        fun bind(product: ProductModel) {
            binding.product.setContent {
                MaterialTheme {
                    ProductItem(product.name)
                }
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<ProductsViewItem>
            get() = object : DiffUtil.ItemCallback<ProductsViewItem>() {
                override fun areItemsTheSame(
                    oldItem: ProductsViewItem,
                    newItem: ProductsViewItem
                ): Boolean {
                    return areTheSame(oldItem, newItem)
                }

                override fun areContentsTheSame(
                    oldItem: ProductsViewItem,
                    newItem: ProductsViewItem
                ): Boolean {
                    return areTheSame(oldItem, newItem)
                }
            }

        private fun areTheSame(
            oldItem: ProductsViewItem,
            newItem: ProductsViewItem
        ): Boolean {
            return when (oldItem) {
                is ProductsViewItem.ShoppingListModel -> {
                    when (newItem) {
                        is ProductsViewItem.ShoppingListModel -> oldItem.id == newItem.id
                        else -> false
                    }
                }
                is ProductModel -> {
                    when (newItem) {
                        is ProductModel -> oldItem.id == newItem.id
                        else -> false
                    }
                }
            }
        }
    }
}