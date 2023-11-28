package com.zizohanto.android.tobuy.shopping_list.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.ProductsScreen
import com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list.ShoppingListsScreen

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
        composable(route = Screen.ShoppingList.route) {
            ShoppingListsScreen(
                listCLick = { shoppingListId ->
                    navController.navigate(Screen.Products.createRoute(shoppingListId))
                }
            )
        }
        composable(route = Screen.Products.route, arguments = Screen.Products.navArguments) {
            ProductsScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}