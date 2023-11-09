package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import androidx.compose.material.MaterialTheme
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListTitleBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.ShoppingListTitle

class ShoppingListTitleViewHolder(
    private val binding: ItemShoppingListTitleBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(
        shoppingList: ShoppingListModel,
        listener: ProductViewListener?
    ) {
        binding.shoppingListTitle.setContent {
            MaterialTheme {
                ShoppingListTitle(shoppingList, listener)
            }
        }
    }
}
