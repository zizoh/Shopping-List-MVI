package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding

class AddProductButtonViewHolder(
    private val binding: ItemAddProductButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lastProductPosition: Int, listener: ProductViewListener?) {
        binding.addNewProduct.setOnClickListener {
            listener?.onAddNewProduct(lastProductPosition)
        }
    }
}