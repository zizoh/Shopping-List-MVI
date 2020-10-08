package com.zizohanto.android.tobuy.shopping_list.navigation

import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel

interface NavigationDispatcher {
    fun openShoppingListDetail(shoppingList: ShoppingListModel)
    fun goBack()
}