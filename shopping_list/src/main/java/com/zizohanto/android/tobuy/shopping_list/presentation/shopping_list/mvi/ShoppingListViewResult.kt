package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.presentation.mvi.ViewResult

sealed class ShoppingListViewResult : ViewResult {
    object Loading : ShoppingListViewResult()
    data class NewShoppingListCreated(val shoppingList: ShoppingList) : ShoppingListViewResult()
    data class Success(val shoppingLists: List<ShoppingList>) : ShoppingListViewResult()
    object Empty : ShoppingListViewResult()
    data class Error(val throwable: Throwable) : ShoppingListViewResult()

}