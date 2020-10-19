package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zizohanto.android.tobuy.core.ext.inflate
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.ItemShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.ShoppingListAdapter.ShoppingListViewHolder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

typealias ShoppingListClickListener = (ShoppingListModel) -> Unit

typealias ShoppingListDeleteListener = (ShoppingListModel, Int) -> Unit

class ShoppingListAdapter @Inject constructor() :
    ListAdapter<ShoppingListModel, ShoppingListViewHolder>(diffUtilCallback) {

    var clickListener: ShoppingListClickListener? = null

    private var deleteListener: ShoppingListDeleteListener? = null

    val deletes: Flow<Pair<ShoppingListModel, Int>>
        get() = callbackFlow {
            val listener: ShoppingListDeleteListener = { list, position ->
                safeOffer(Pair(list, position))
                Unit
            }
            deleteListener = listener
            awaitClose {
                deleteListener = null
            }
        }.conflate()

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
        holder.bind(getItem(position), position)
    }

    class ShoppingListViewHolder(
        private val binding: ItemShoppingListBinding,
        private val clickListener: ShoppingListClickListener?,
        private val deleteListener: ShoppingListDeleteListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listModel: ShoppingListModel, position: Int) {
            binding.title.text = listModel.name
            binding.root.setOnClickListener {
                clickListener?.invoke(listModel)
            }

            binding.root.setOnLongClickListener {
                MaterialAlertDialogBuilder(it.context)
                    .setTitle(it.resources.getString(R.string.delete_shopping_list))
                    .setNegativeButton(it.resources.getString(R.string.cancel)) { _, _ -> }
                    .setPositiveButton(it.resources.getString(R.string.delete)) { _, _ ->
                        deleteListener?.invoke(listModel, position)
                    }
                    .show()
                true
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