package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.presentation.mvi.ViewResult

sealed class ProductsViewResult : ViewResult {
    object Idle : ProductsViewResult()
    sealed class ProductViewResult : ProductsViewResult() {
        data class Success(
            val listWithProducts: ShoppingListWithProducts
        ) : ProductViewResult()

        data class ProductSaved(
            val listWithProducts: ShoppingListWithProducts,
            val product: Product
        ) : ProductViewResult()

        data class ProductDeleted(val shoppingListWithProducts: ShoppingListWithProducts) :
            ProductViewResult()

        data class ShoppingListSaved(val shoppingList: ShoppingList) : ProductViewResult()
        object ShoppingListDeleted : ProductViewResult()
    }

    data class Error(val message: String) : ProductsViewResult()

}