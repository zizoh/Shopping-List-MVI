package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zizohanto.android.tobuy.core.ext.observe
import com.zizohanto.android.tobuy.core.view_binding.viewBinding
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.FragmentShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent.LoadShoppingLists
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list),
    MVIView<ShoppingListViewIntent, ShoppingListViewState> {

    @Inject
    lateinit var navigator: NavigationDispatcher

    private val viewModel: ShoppingListViewModel by viewModels()

    private val binding: FragmentShoppingListBinding by viewBinding(FragmentShoppingListBinding::bind)

    private val loadShoppingLists = ConflatedBroadcastChannel<LoadShoppingLists>()

    override val intents: Flow<ShoppingListViewIntent>
        get() = merge(
            loadShoppingLists.asFlow(),
            binding.shoppingList.intents,
            binding.shoppingList.retryIntent()
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processIntent(intents)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    override fun onResume() {
        super.onResume()

        loadShoppingLists.trySend(LoadShoppingLists)
    }

    override fun render(state: ShoppingListViewState) {
        with(viewModel) {
            binding.shoppingList.render(
                state,
                { shoppingListId ->
                    navigator.openShoppingListDetail(shoppingListId)
                },
                { shoppingListId ->
                    onListDeleted(shoppingListId)
                }
            )
        }
    }
}