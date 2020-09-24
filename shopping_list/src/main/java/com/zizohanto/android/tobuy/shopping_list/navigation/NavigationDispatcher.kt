package com.zizohanto.android.tobuy.shopping_list.navigation

interface NavigationDispatcher {
    fun openShoppingListDetail(shoppingListId: String, isNewShoppingList: Boolean)
    fun goBack()
}