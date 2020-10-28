package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel

class ProductViewHolder(
    private val binding: ItemProductEditableBinding
) : TextChangeViewHolder(binding.root) {

    fun bind(
        product: ProductModel,
        editListener: ProductEditListener?,
        deleteListener: ProductDeleteListener?,
        addNewProductListener: AddNewProductListener?
    ) {

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
                    editListener?.invoke(product.copy(name = s?.trim().toString()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.productName.setOnFocusChangeListener { _, hasFocus ->
            binding.remove.isVisible = hasFocus
            if (hasFocus) {
                binding.productName.addTextChangedListener(textWatcher)
            } else {
                binding.productName.removeTextChangedListener(textWatcher)
            }
        }

        binding.productName.setText(product.name)

        binding.remove.setOnClickListener {
            deleteListener?.invoke(product)
        }

        binding.productName.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                addNewProductListener?.invoke(product.position, absoluteAdapterPosition - 1)
                true
            } else false
        }
    }

}