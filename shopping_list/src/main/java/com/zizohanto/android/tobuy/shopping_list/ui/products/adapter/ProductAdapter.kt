package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListTitleBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

typealias ProductEditListener = (ProductModel) -> Unit
typealias ProductDeleteListener = (ProductModel) -> Unit
typealias AddNewProductListener = (Int, Int) -> Unit
typealias ShoppingListEditListener = (ShoppingListModel) -> Unit

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductsViewItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var productEditListener: ProductEditListener? = null
    var addNewProductListener: AddNewProductListener? = null
    private var shoppingListEditListener: ShoppingListEditListener? = null

    val productEdits: Flow<ProductModel>
        get() = callbackFlow {
            val listener: ProductEditListener = { product ->
                safeOffer(product)
                Unit
            }
            productEditListener = listener
            awaitClose {
                productEditListener = null
            }
        }.conflate()

    private var deleteListener: ProductDeleteListener? = null

    val deletes: Flow<ProductModel>
        get() = callbackFlow {
            val listener: ProductDeleteListener = { product ->
                safeOffer(product)
                Unit
            }
            deleteListener = listener
            awaitClose {
                deleteListener = null
            }
        }.conflate()

    val shoppingListEdits: Flow<ShoppingListModel>
        get() = callbackFlow {
            val listener: ShoppingListEditListener = { shoppingList ->
                safeOffer(shoppingList)
                Unit
            }
            shoppingListEditListener = listener
            awaitClose {
                shoppingListEditListener = null
            }
        }.conflate()

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
                )
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
        when (holder) {
            is ShoppingListTitleViewHolder -> {
                holder.bind(
                    getItem(holder.bindingAdapterPosition) as ShoppingListModel,
                    shoppingListEditListener
                )
            }
            is ProductViewHolder -> {
                holder.bind(
                    getItem(holder.bindingAdapterPosition) as ProductModel,
                    productEditListener,
                    deleteListener,
                    addNewProductListener
                )
            }
            is AddProductButtonViewHolder -> {
                holder.bind(
                    { getItem(holder.bindingAdapterPosition - 1) },
                    addNewProductListener
                )
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
                ButtonItem -> true
            }
        }
    }
}