package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel

sealed class ProductsViewState : ViewState {
    object Idle : ProductsViewState()

    sealed class ProductViewState : ProductsViewState() {
        data class Success(
            val listWithProducts: ShoppingListWithProductsModel,
            val viewItems: List<ProductsViewItem>,
            val shouldShowProducts: Boolean = true
        ) : ProductViewState()
        object DeleteShoppingList : ProductViewState()
    }

    data class Error(val message: String) : ProductsViewState()

}