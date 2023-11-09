package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.AddProductButton

class AddProductButtonViewHolder(
    private val binding: ItemAddProductButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lastProductPosition: Int, listener: ProductViewListener?) {
        binding.addNewProduct.setContent {
            MaterialTheme {
                AddProductButton(lastProductPosition, listener)
            }
        }
    }
}