package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter.ProductViewHolder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.view.clicks
import javax.inject.Inject

typealias ProductEditListener = (ProductModel) -> Unit

typealias ProductDeleteListener = (ProductModel) -> Unit

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductModel, ProductViewHolder>(diffUtilCallback) {

    private var editListener: ProductEditListener? = null

    val edits: Flow<ProductModel>
        get() = callbackFlow {
            val listener: ProductEditListener = { product ->
                safeOffer(product)
                Unit
            }
            editListener = listener
            awaitClose {
                editListener = null
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductEditableBinding.bind(parent.inflate(R.layout.item_product_editable)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(holder.bindingAdapterPosition), editListener, deleteListener)
    }

    class ProductViewHolder(
        private val binding: ItemProductEditableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var currentProduct: ProductModel

        fun bind(
            product: ProductModel,
            editListener: ProductEditListener?,
            deleteListener: ProductDeleteListener?
        ) {
            currentProduct = product
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (isValidTextChange(s, start, before, count)) {
                        editListener?.invoke(
                            product.copy(name = s?.trim().toString())
                        )
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }

            binding.productName.setOnFocusChangeListener { _, hasFocus ->
                binding.remove.isVisible = hasFocus
                if (hasFocus) {
                    binding.productName.addTextChangedListener(textWatcher)
                } else {
                    binding.productName.removeTextChangedListener(textWatcher)
                }
            }

            binding.productName.setText(product.name)

            binding.remove.setOnClickListener {
                deleteListener?.invoke(product)
            }
        }

        private fun isValidTextChange(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) = !(s.isNullOrEmpty() && start == 0 && before == 0 && count == 0)

        private val deleteProductIntent: Flow<ProductsViewIntent>
            get() = binding.remove.clicks().map {
                ProductsViewIntent.ProductViewIntent.DeleteProduct(currentProduct)
            }

        val intents: Flow<ProductsViewIntent>
            get() = deleteProductIntent
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