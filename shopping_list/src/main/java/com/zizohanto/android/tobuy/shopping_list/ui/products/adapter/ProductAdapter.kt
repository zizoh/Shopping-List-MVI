package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListTitleBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.*
import javax.inject.Inject

interface ProductViewListener {
    fun onProductEdit(product: ProductModel)
    fun onProductDelete(product: ProductModel)
    fun onAddNewProduct(shoppingListId: String, newProductPosition: Int)
    fun onShoppingListEdit(shoppingList: ShoppingListModel)
}

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductsViewItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    var productViewListener: ProductViewListener? = null

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ShoppingListModel -> R.layout.item_shopping_list_title
            is ProductModel -> R.layout.item_product_editable
            is ButtonItem -> R.layout.item_add_product_button
            else -> throw IllegalArgumentException("Unknown view type at position: $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_shopping_list_title -> ShoppingListTitleViewHolder(
                ItemShoppingListTitleBinding.bind(
                    parent.inflate(R.layout.item_shopping_list_title)
                ),
            )
            R.layout.item_product_editable -> ProductViewHolder(
                ItemProductEditableBinding.bind(
                    parent.inflate(R.layout.item_product_editable)
                )
            )
            R.layout.item_add_product_button -> AddProductButtonViewHolder(
                ItemAddProductButtonBinding.bind(
                    parent.inflate(R.layout.item_add_product_button)
                )
            )
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(holder.bindingAdapterPosition)
        when (holder) {
            is ShoppingListTitleViewHolder -> {
                holder.bind(item as ShoppingListModel, productViewListener)
            }
            is ProductViewHolder -> {
                holder.bind(item as ProductModel, productViewListener)
            }
            is AddProductButtonViewHolder -> {
                val button = item as ButtonItem
                holder.bind(button.shoppingListId, button.newProductPosition, productViewListener)
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
                is ShoppingListModel -> {
                    when (newItem) {
                        is ShoppingListModel -> oldItem.id == newItem.id
                        else -> false
                    }
                }
                is ProductModel -> {
                    when (newItem) {
                        is ProductModel -> oldItem.id == newItem.id
                        else -> false
                    }
                }
                is ButtonItem -> true
            }
        }
    }
}