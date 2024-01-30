package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewIntent

sealed class ShoppingListViewIntent : ViewIntent {
    object Idle : ShoppingListViewIntent()
    object LoadShoppingLists : ShoppingListViewIntent()
    object CreateNewShoppingList : ShoppingListViewIntent()
    data class DeleteShoppingList(val shoppingListId: String) : ShoppingListViewIntent()
}