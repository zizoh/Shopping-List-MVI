package com.zizohanto.android.tobuy.shopping_list.navigation

import androidx.navigation.NavController
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.ShoppingListFragmentDirections
import javax.inject.Inject
import javax.inject.Provider

class NavigationDispatcherImpl @Inject constructor(
    private val navController: Provider<NavController>
) : NavigationDispatcher {

    override fun openShoppingListDetail(shoppingList: ShoppingListModel) {
        navController.get().navigate(
            ShoppingListFragmentDirections.actionShoppingListFragmentToProductFragment(shoppingList)
        )
    }

    override fun goBack() {
        navController.get().navigateUp()
    }
}