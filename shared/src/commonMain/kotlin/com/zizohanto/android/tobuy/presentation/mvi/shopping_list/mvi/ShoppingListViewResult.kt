package com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.presentation.mvi.ViewResult
import com.zizohanto.android.tobuy.sq.ShoppingList

sealed class ShoppingListViewResult : ViewResult {
    object Idle : ShoppingListViewResult()
    object Loading : ShoppingListViewResult()
    data class NewShoppingListCreated(val shoppingList: ShoppingList) : ShoppingListViewResult()
    data class Success(
        val listWithProducts: List<ShoppingListWithProducts>
    ) : ShoppingListViewResult()

    data class ShoppingListDeleted(val shoppingListId: String) : ShoppingListViewResult()
    object Empty : ShoppingListViewResult()
    data class Error(val throwable: Throwable) : ShoppingListViewResult()

}