package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewIntent

sealed class ShoppingListViewIntent : ViewIntent {
    object LoadShoppingLists : ShoppingListViewIntent()
    object CreateNewShoppingList : ShoppingListViewIntent()
}