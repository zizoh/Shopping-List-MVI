package com.zizohanto.android.tobuy.shopping_list.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Provider

class NavigationDispatcherImpl @Inject constructor(
    private val navController: Provider<NavController>
): NavigationDispatcher {

    override fun openShoppingListDetail(shoppingListId: String) {
        TODO("Not yet implemented")
    }

    override fun goBack() {
        navController.get().navigateUp()
    }
}