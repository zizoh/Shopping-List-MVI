package com.zizohanto.android.tobuy.shopping_list.navigation

interface NavigationDispatcher {
    fun openShoppingListDetail(shoppingListId: String)
    fun goBack()
}