package com.zizohanto.android.tobuy.shopping_list.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object ShoppingList : Screen("shoppingList")

    data object Products : Screen(
        route = "products/{shoppingListId}",
        navArguments = listOf(
            navArgument("shoppingListId") {
                type = NavType.StringType
            }
        )
    ) {
        fun createRoute(shoppingListId: String) = "products/${shoppingListId}"
    }
}