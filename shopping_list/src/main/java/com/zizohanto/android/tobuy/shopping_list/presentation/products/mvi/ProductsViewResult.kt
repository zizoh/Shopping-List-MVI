package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.presentation.mvi.ViewResult

sealed class ProductsViewResult : ViewResult {
    object Idle : ProductsViewResult()

    sealed class ShoppingListViewResult : ProductsViewResult() {
        object NewShoppingList : ShoppingListViewResult()
        data class Success(val shoppingList: ShoppingList) : ShoppingListViewResult()
    }

    sealed class ProductViewResult : ProductsViewResult() {
        data class FirstProduct(val product: Product) : ProductViewResult()
        data class Success(val products: List<Product>) : ProductViewResult()
        data class EditProduct(val product: Product) : ProductViewResult()
        data class DeleteProduct(val productId: String) : ProductViewResult()
    }

}