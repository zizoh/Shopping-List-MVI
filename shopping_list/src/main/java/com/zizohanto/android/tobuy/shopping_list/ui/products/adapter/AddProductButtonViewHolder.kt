package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemAddProductButtonBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.AddProductButton

class AddProductButtonViewHolder(
    private val binding: ItemAddProductButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lastProductPosition: Int, listener: ProductViewListener?) {
        binding.addNewProduct.setContent {
            AddProductButton(
                lastProductPosition,
                listener,
                modifier = Modifier
                    .padding(start = 8.dp, top = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }
    }
}