package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.text.Editable
import android.text.TextWatcher
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListTitleBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel

class ShoppingListTitleViewHolder(
    private val binding: ItemShoppingListTitleBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(shoppingList: ShoppingListModel, editListener: ShoppingListEditListener?) {
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
                    editListener?.invoke(shoppingList.copy(name = s?.trim().toString()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.shoppingListTitle.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.shoppingListTitle.addTextChangedListener(textWatcher)
            } else {
                binding.shoppingListTitle.removeTextChangedListener(textWatcher)
            }
        }

        binding.shoppingListTitle.setText(shoppingList.name)
    }
}
