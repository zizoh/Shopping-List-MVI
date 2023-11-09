package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.compose.material.MaterialTheme
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.RowProduct

class ProductViewHolder(
    private val binding: ItemProductEditableBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(product: ProductModel, listener: ProductViewListener?) {
        binding.products.setContent {
            MaterialTheme {
                RowProduct(product, listener)
            }
        }
    }

}