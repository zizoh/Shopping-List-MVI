package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemProductEditableBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter.ProductViewHolder
import javax.inject.Inject

typealias ProductClickListener = (ProductModel) -> Unit

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductModel, ProductViewHolder>(diffUtilCallback) {

    var clickListener: ProductClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductEditableBinding.bind(parent.inflate(R.layout.item_product_editable)))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ProductViewHolder(
        private val binding: ItemProductEditableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, clickListener: ProductClickListener?) {
            binding.productName.setText(product.name)
            binding.root.setOnClickListener {
                clickListener?.invoke(product)
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<ProductModel>
            get() = object : DiffUtil.ItemCallback<ProductModel>() {
                override fun areItemsTheSame(
                    oldItem: ProductModel,
                    newItem: ProductModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ProductModel,
                    newItem: ProductModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}