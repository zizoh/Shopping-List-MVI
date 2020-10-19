package com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.core.ext.getImage
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcherImpl
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.ShoppingListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import reactivecircus.flowbinding.android.view.clicks
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListsView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet),
    MVIView<ShoppingListViewIntent, ShoppingListViewState> {

    @Inject
    lateinit var shoppingListAdapter: ShoppingListAdapter

    @Inject
    lateinit var navigator: NavigationDispatcherImpl

    private var binding: LayoutShoppingListBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutShoppingListBinding.inflate(inflater, this, true)
        with(binding) {
            shoppingLists.adapter = shoppingListAdapter.apply {
                clickListener = navigator::openShoppingListDetail
            }
        }
    }

    fun retryIntent(): Flow<ShoppingListViewIntent> =
        binding.emptyState.clicks.map {
            ShoppingListViewIntent.LoadShoppingLists
        }

    override fun render(state: ShoppingListViewState) {
        when (state) {
            ShoppingListViewState.Idle -> {
            }
            ShoppingListViewState.Loading -> {
                with(binding) {
                    progressBar.isVisible = !shoppingLists.isVisible
                    emptyState.isVisible = false
                }
            }
            is ShoppingListViewState.ShoppingListLoaded -> {
                with(binding) {
                    progressBar.isVisible = false
                    emptyState.isVisible = false
                    shoppingLists.isVisible = true
                }
                shoppingListAdapter.submitList(state.shoppingLists)
            }
            ShoppingListViewState.ShoppingListEmpty -> {
                shoppingListAdapter.reset()
                with(binding) {
                    progressBar.isVisible = false
                    shoppingLists.isVisible = false
                    emptyState.isVisible = true
                    emptyState.setImage(context.getImage(R.drawable.ic_empty))
                    emptyState.setTitle(context.getString(R.string.no_data))
                    emptyState.resetCaption()
                    emptyState.isButtonVisible = false
                }
            }
            is ShoppingListViewState.Error -> {
                with(binding) {
                    shoppingLists.isVisible = false
                    progressBar.isVisible = false
                    emptyState.isVisible = true
                    emptyState.setImage(context.getImage(R.drawable.ic_error_page_2))
                    emptyState.setCaption(state.message)
                    emptyState.setTitle(context.getString(R.string.an_error_occurred))
                    emptyState.isButtonVisible = true
                }
            }
            is ShoppingListViewState.NewShoppingListLoaded -> {
                state.shoppingList.consume(navigator::openShoppingListDetail)
            }
        }
    }

    private val createNewShoppingListIntent: Flow<ShoppingListViewIntent>
        get() = binding.addShoppingList.clicks().map {
            ShoppingListViewIntent.CreateNewShoppingList
        }

    private val deleteShoppingListIntent: Flow<ShoppingListViewIntent>
        get() = shoppingListAdapter.deletes.map {
            val shoppingList: ShoppingListModel = it.first
            val position: Int = it.second
            ShoppingListViewIntent.DeleteShoppingList(shoppingList.id, position)
        }

    override val intents: Flow<ShoppingListViewIntent>
        get() = merge(createNewShoppingListIntent, deleteShoppingListIntent)
}
