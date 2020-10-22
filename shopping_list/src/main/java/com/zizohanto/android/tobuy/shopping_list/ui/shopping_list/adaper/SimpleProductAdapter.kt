package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter.Companion.diffUtilCallback
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.SimpleProductAdapter.SimpleProductViewHolder
import javax.inject.Inject

class SimpleProductAdapter @Inject constructor(
    products: List<ProductModel>,
    private val clickListener: (v: View) -> Unit
) : ListAdapter<ProductModel, SimpleProductViewHolder>(diffUtilCallback) {

    init {
        submitList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleProductViewHolder {
        return SimpleProductViewHolder(
            ItemProductBinding.bind(parent.inflate(R.layout.item_product)),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: SimpleProductViewHolder, position: Int) {
        holder.bind(
            getItem(holder.bindingAdapterPosition)
        )
    }

    class SimpleProductViewHolder(
        private val binding: ItemProductBinding,
        clickListener: (v: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(clickListener)
        }

        fun bind(product: ProductModel) {
            binding.product.text = product.name
        }
    }
}