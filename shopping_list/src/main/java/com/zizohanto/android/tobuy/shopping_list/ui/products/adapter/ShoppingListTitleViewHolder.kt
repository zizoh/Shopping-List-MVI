package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListTitleBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.ui.products.DEBOUNCE_PERIOD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges

class ShoppingListTitleViewHolder(
    private val binding: ItemShoppingListTitleBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(
        shoppingList: ShoppingListModel,
        listener: ProductViewListener?,
        scope: CoroutineScope
    ) {
        binding.shoppingListTitle.setText(shoppingList.name)
        binding.shoppingListTitle
            .textChanges()
            .debounce(DEBOUNCE_PERIOD)
            .drop(1)
            .map {
                listener?.onShoppingListEdit(shoppingList.copy(name = it.trim().toString()))
            }.launchIn(scope)
    }
}
