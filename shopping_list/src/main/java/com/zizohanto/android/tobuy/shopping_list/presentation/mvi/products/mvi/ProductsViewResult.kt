package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.shopping_list.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewResult
import com.zizohanto.android.tobuy.shoppinglist.sq.Product
import com.zizohanto.android.tobuy.shoppinglist.sq.ShoppingList

sealed class ProductsViewResult : ViewResult {
    object Idle : ProductsViewResult()
    sealed class ProductViewResult : ProductsViewResult() {
        data class Success(
            val listWithProducts: ShoppingListWithProducts
        ) : ProductViewResult()

        data class ProductAddedAtPosition(
            val products: List<Product>,
            val newProductPosition: Int
        ) : ProductViewResult()

        data class ProductSaved(val product: Product) : ProductViewResult()
        data class ProductDeleted(val productId: String) : ProductViewResult()
        data class ShoppingListSaved(val shoppingList: ShoppingList) : ProductViewResult()
        data object ShoppingListDeleted : ProductViewResult()
    }

    data class Error(val message: String) : ProductsViewResult()

}