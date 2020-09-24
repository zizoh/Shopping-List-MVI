package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel

sealed class ProductsViewState : ViewState {
    object Idle : ProductsViewState()
    sealed class ShoppingListState : ProductsViewState() {
        data class NewShoppingList(val listModel: ShoppingListModel) : ShoppingListState()
        data class Success(val listModel: ShoppingListModel) : ShoppingListState()
    }

    sealed class ProductViewState : ProductsViewState() {
        data class FirstProduct(val product: ProductModel) : ProductViewState()
        data class Success(val products: List<ProductModel>) : ProductViewState()
        data class EditProduct(val product: ProductModel) : ProductViewState()
        data class DeleteProduct(val productId: String) : ProductViewState()
    }

}