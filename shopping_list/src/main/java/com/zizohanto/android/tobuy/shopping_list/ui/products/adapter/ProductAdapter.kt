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
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface ProductViewListener {
    fun onProductEdit(product: ProductModel)
    fun onProductDelete(product: ProductModel)
    fun onAddNewProduct(shoppingListId: String, position: Int)
    fun onShoppingListEdit(shoppingList: ShoppingListModel)
}

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductsViewItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    lateinit var coroutineScope: CoroutineScope
    private var productViewListener: ProductViewListener? = null

    val intents: Flow<ProductsViewIntent>
        get() = callbackFlow {
            val listener: ProductViewListener = object : ProductViewListener {
                override fun onProductEdit(product: ProductModel) {
                    safeOffer(ProductViewIntent.SaveProduct(product))
                }

                override fun onProductDelete(product: ProductModel) {
                    safeOffer(ProductViewIntent.DeleteProduct(product))
                }

                override fun onAddNewProduct(shoppingListId: String, position: Int) {
                    safeOffer(ProductViewIntent.AddNewProductAtPosition(shoppingListId, position))
                }

                override fun onShoppingListEdit(shoppingList: ShoppingListModel) {
                    safeOffer(
                        ProductViewIntent.SaveShoppingList(
                            shoppingList.copy(name = shoppingList.name)
                        )
                    )
                }
            }
            productViewListener = listener
            awaitClose {
                productViewListener = null
            }
        }

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
        when (holder) {
            is ShoppingListTitleViewHolder -> {
                holder.bind(
                    getItem(holder.bindingAdapterPosition) as ShoppingListModel,
                    productViewListener
                )
            }
            is ProductViewHolder -> {
                holder.bind(
                    getItem(holder.bindingAdapterPosition) as ProductModel,
                    productViewListener
                )
            }
            is AddProductButtonViewHolder -> {
                val button = getItem(holder.bindingAdapterPosition) as ButtonItem
                holder.bind(button.shoppingListId, itemCount - 3, productViewListener)
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