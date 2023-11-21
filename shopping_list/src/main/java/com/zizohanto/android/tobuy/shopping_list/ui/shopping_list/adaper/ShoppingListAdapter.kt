package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper

import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list.ShoppingListItem
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.ShoppingListAdapter.ShoppingListViewHolder
import javax.inject.Inject

class ShoppingListAdapter @Inject constructor() :
    ListAdapter<ShoppingListWithProductsModel, ShoppingListViewHolder>(diffUtilCallback) {

    private var clickListener: (String) -> Unit = {}

    private var deleteListener: (String) -> Unit = {}

    fun submitList(
        listWithProducts: List<ShoppingListWithProductsModel>,
        listCLick: (String) -> Unit,
        listDelete: (String) -> Unit
    ) {
        clickListener = listCLick
        deleteListener = listDelete
        submitList(listWithProducts)
    }

    fun reset() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder(
            ItemShoppingListBinding.bind(parent.inflate(R.layout.item_shopping_list)),
            clickListener,
            deleteListener
        )
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShoppingListViewHolder(
        private val binding: ItemShoppingListBinding,
        private val clickListener: (String) -> Unit,
        private val deleteListener: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listWithProducts: ShoppingListWithProductsModel) {
            binding.root.setContent {
                MaterialTheme {
                    ShoppingListItem(
                        listWithProducts,
                        {
                            clickListener.invoke(it)
                        }, {
                            deleteListener.invoke(it)
                        }
                    )
                }
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<ShoppingListWithProductsModel>
            get() = object : DiffUtil.ItemCallback<ShoppingListWithProductsModel>() {
                override fun areItemsTheSame(
                    oldItem: ShoppingListWithProductsModel,
                    newItem: ShoppingListWithProductsModel
                ): Boolean {
                    return oldItem.shoppingList.id == newItem.shoppingList.id
                }

                override fun areContentsTheSame(
                    oldItem: ShoppingListWithProductsModel,
                    newItem: ShoppingListWithProductsModel
                ): Boolean {
                    return oldItem.products.size == newItem.products.size &&
                            oldItem.shoppingList == newItem.shoppingList
                }
            }
    }
}