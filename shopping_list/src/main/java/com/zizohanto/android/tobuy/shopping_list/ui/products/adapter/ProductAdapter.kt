package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.core.utils.DiffUtilCallback
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter.ProductViewHolder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject
import kotlin.properties.Delegates

typealias ProductEditListener = (ProductModel) -> Unit

typealias ProductDeleteListener = (ProductModel) -> Unit

class ProductAdapter @Inject constructor() : RecyclerView.Adapter<ProductViewHolder>() {

    private var items by Delegates.observable<MutableList<ProductModel>>(mutableListOf()) { _, oldValue, newValue ->
        DiffUtil
            .calculateDiff(
                DiffUtilCallback(
                    oldValue,
                    newValue
                )
            )
            .dispatchUpdatesTo(this)
    }

    private var editListener: ProductEditListener? = null

    val edits: Flow<ProductModel>
        get() = callbackFlow {
            val listener: ProductEditListener = { product ->
                safeOffer(product)
                Unit
            }
            editListener = listener
            awaitClose {
                editListener = null
            }
        }.conflate()

    private var deleteListener: ProductDeleteListener? = null

    val deletes: Flow<ProductModel>
        get() = callbackFlow {
            val listener: ProductDeleteListener = { product ->
                safeOffer(product)
                Unit
            }
            deleteListener = listener
            awaitClose {
                deleteListener = null
            }
        }.conflate()

    fun addItems(data: List<ProductModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun addNewProduct(product: ProductModel) {
        items.add(items.size, product)
        notifyItemInserted(items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductEditableBinding.bind(parent.inflate(R.layout.item_product_editable)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position], editListener, deleteListener)
    }

    override fun getItemCount(): Int = items.size

    class ProductViewHolder(
        private val binding: ItemProductEditableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: ProductModel,
            editListener: ProductEditListener?,
            deleteListener: ProductDeleteListener?
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
                        editListener?.invoke(
                            product.copy(name = s?.trim().toString())
                        )
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }

            binding.productName.setText(product.name)
            binding.productName.addTextChangedListener(textWatcher)

            binding.productName.setOnFocusChangeListener { _, hasFocus ->
                binding.remove.isVisible = hasFocus
                if (!hasFocus) {
                    binding.productName.removeTextChangedListener(textWatcher)
                }
            }

            binding.remove.setOnClickListener {
                deleteListener?.invoke(product)
            }
        }

        private fun isValidTextChange(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) = !(s.isNullOrEmpty() && start == 0 && before == 0 && count == 0)
    }
}