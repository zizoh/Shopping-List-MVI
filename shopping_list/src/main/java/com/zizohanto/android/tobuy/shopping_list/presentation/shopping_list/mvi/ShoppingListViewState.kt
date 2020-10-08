package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.presentation.event.ViewEvent
import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel

sealed class ShoppingListViewState : ViewState {
    object Idle : ShoppingListViewState()
    object Loading : ShoppingListViewState()
    data class NewShoppingListLoaded(
        val shoppingList: ViewEvent<ShoppingListModel>
    ) : ShoppingListViewState()

    data class ShoppingListLoaded(
        val shoppingLists: List<ShoppingListModel>
    ) : ShoppingListViewState()

    object ShoppingListEmpty : ShoppingListViewState()
    data class Error(val message: String) : ShoppingListViewState()
}