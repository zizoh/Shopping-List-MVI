package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.ui.products.DEBOUNCE_PERIOD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges

class ProductViewHolder(
    private val binding: ItemProductEditableBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(product: ProductModel, listener: ProductViewListener?, scope: CoroutineScope) {
        binding.productName.setOnFocusChangeListener { _, hasFocus ->
            binding.remove.isVisible = hasFocus
        }

        binding.productName.setText(product.name)
        binding.productName
            .textChanges()
            .debounce(DEBOUNCE_PERIOD)
            .drop(1)
            .map {
                listener?.onProductEdit(product.copy(name = it.trim().toString()))
            }.launchIn(scope)

        binding.remove.setOnClickListener {
            listener?.onProductDelete(product)
        }

        binding.productName.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                if (product.name.isNotEmpty()) {
                    listener?.onAddNewProduct(product.position)
                }
                true
            } else false
        }
    }

}