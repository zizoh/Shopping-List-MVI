package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter.ProductViewHolder
import com.zizohanto.android.tobuy.shopping_list.ui.products.checkDistinct
import com.zizohanto.android.tobuy.shopping_list.ui.products.textChanges
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

typealias ProductEditListener = (Flow<ProductsViewIntent>) -> Unit

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductModel, ProductViewHolder>(diffUtilCallback) {

    var editListener: ProductEditListener? = null

    val edits: Flow<ProductsViewIntent>
        get() = callbackFlow {
            val listener: ProductEditListener = {
                safeOffer(it)
                Unit
            }
            editListener = listener
            awaitClose { editListener = null }
        }.flattenConcat().conflate()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductEditableBinding.bind(parent.inflate(R.layout.item_product_editable)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), editListener)
    }

    class ProductViewHolder(
        private val binding: ItemProductEditableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, editListener: ProductEditListener?) {
            binding.productName.setText(product.name)

            val saveProduct: Flow<ProductViewIntent.SaveProduct> =
                binding.productName.textChanges.checkDistinct.map {
                    ProductViewIntent.SaveProduct(product.copy(name = it))
                }
            editListener?.invoke(saveProduct)
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<ProductModel>
            get() = object : DiffUtil.ItemCallback<ProductModel>() {
                override fun areItemsTheSame(
                    oldItem: ProductModel,
                    newItem: ProductModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ProductModel,
                    newItem: ProductModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}