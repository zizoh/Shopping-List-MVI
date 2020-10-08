package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

class ShoppingListViewModel @ViewModelInject constructor(
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
}