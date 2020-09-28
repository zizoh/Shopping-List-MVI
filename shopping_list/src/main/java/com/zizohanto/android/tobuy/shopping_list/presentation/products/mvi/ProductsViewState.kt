package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel

sealed class ProductsViewState : ViewState {
    object Idle : ProductsViewState()

    sealed class ProductViewState : ProductsViewState() {
        data class Success(
            val listWithProducts: ShoppingListWithProductsModel
        ) : ProductViewState()

        data class SaveProduct(
            val listWithProducts: ShoppingListWithProductsModel,
            val product: ProductModel
        ) : ProductViewState()

        data class DeleteProduct(
            val listWithProducts: ShoppingListWithProductsModel
        ) : ProductViewState()

        object SaveShoppingList : ProductViewState()
        object DeleteShoppingList : ProductViewState()
    }

    data class Error(val message: String) : ProductsViewState()

}