package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding

class AddProductButtonViewHolder(
    private val binding: ItemAddProductButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        lastItemPosition: Int,
        addNewProductListener: AddNewProductListener?
    ) {
        binding.addNewProduct.setOnClickListener {
            addNewProductListener?.invoke(lastItemPosition, absoluteAdapterPosition - 2)
        }
    }
}