package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListStateMachine: ShoppingListStateMachine
) : ViewModel(), MVIPresenter<ShoppingListViewState, ShoppingListViewIntent> {

    override val viewState: Flow<ShoppingListViewState>
        get() = shoppingListStateMachine.viewState

    init {
        shoppingListStateMachine.processor.launchIn(viewModelScope)
    }

    override fun processIntent(intents: Flow<ShoppingListViewIntent>) {
        shoppingListStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }

    fun onListDeleted(shoppingListId: String) {
        processIntent(flowOf(ShoppingListViewIntent.DeleteShoppingList(shoppingListId)))
    }
}