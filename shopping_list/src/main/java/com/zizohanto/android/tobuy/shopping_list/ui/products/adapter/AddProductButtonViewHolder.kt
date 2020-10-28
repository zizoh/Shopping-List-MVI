package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem

class AddProductButtonViewHolder(
    private val binding: ItemAddProductButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: () -> ProductsViewItem,
        addNewProductListener: AddNewProductListener?
    ) {

        val lastItemPosition = try {
            val model: ProductsViewItem = item()
            if (model is ProductsViewItem.ProductModel)
                model.position
            else 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }

        binding.addNewProduct.setOnClickListener {
            addNewProductListener?.invoke(lastItemPosition, absoluteAdapterPosition - 2)
        }
    }
}