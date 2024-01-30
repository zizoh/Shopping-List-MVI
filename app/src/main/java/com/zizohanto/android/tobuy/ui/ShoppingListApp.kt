package com.zizohanto.android.tobuy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zizohanto.android.tobuy.presentation.views.product.ProductsScreen
import com.zizohanto.android.tobuy.presentation.views.shopping_list.ShoppingListsScreen

@Composable
fun ShoppingListApp() {
    val navController = rememberNavController()
    ShoppingListNavHost(navController = navController)
}

@Composable
fun ShoppingListNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.ShoppingList.route) {
        composable(route = Screen.ShoppingList.route) { entry ->
            val shouldRefresh = entry.savedStateHandle.remove<Boolean>(IS_FROM_BACK_PRESS) ?: false
            ShoppingListsScreen(
                shouldRefreshList = shouldRefresh,
                listCLick = { shoppingListId ->
                    navController.navigate(Screen.Products.createRoute(shoppingListId))
                }
            )
        }
        composable(route = Screen.Products.route, arguments = Screen.Products.navArguments) {
            ProductsScreen(
                onBackPressed = {
                    with(navController) {
                        previousBackStackEntry?.savedStateHandle?.set(IS_FROM_BACK_PRESS, true)
                        navigateUp()
                    }
                }
            )
        }
    }
}

private const val IS_FROM_BACK_PRESS = "is_from_back_press"