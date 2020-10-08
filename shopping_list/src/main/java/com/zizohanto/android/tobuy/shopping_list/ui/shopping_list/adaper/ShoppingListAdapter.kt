package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.ShoppingListAdapter.ShoppingListViewHolder
import javax.inject.Inject

typealias ShoppingListClickListener = (ShoppingListModel) -> Unit

class ShoppingListAdapter @Inject constructor() :
    ListAdapter<ShoppingListModel, ShoppingListViewHolder>(diffUtilCallback) {

    var clickListener: ShoppingListClickListener? = null

    fun reset() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder(ItemShoppingListBinding.bind(parent.inflate(R.layout.item_shopping_list)))
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ShoppingListViewHolder(
        private val binding: ItemShoppingListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listModel: ShoppingListModel, clickListener: ShoppingListClickListener?) {
            binding.title.text = listModel.name
            binding.root.setOnClickListener {
                clickListener?.invoke(listModel)
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<ShoppingListModel>
            get() = object : DiffUtil.ItemCallback<ShoppingListModel>() {
                override fun areItemsTheSame(
                    oldItem: ShoppingListModel,
                    newItem: ShoppingListModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ShoppingListModel,
                    newItem: ShoppingListModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}