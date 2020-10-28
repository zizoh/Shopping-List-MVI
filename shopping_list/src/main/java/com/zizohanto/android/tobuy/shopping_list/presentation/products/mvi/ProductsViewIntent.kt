package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel

sealed class ProductsViewIntent : ViewIntent {
    object Idle : ProductsViewIntent()

    sealed class ProductViewIntent : ProductsViewIntent() {
        data class LoadShoppingListWithProducts(val shoppingListId: String) : ProductViewIntent()
        data class AddNewProductAtPosition(
            val shoppingListId: String,
            val position: Int,
            val index: Int
        ) : ProductViewIntent()

        data class SaveProduct(
            val product: ProductModel,
            val shoppingListId: String
        ) : ProductsViewIntent()

        data class DeleteProduct(val productId: String) : ProductsViewIntent()
        data class SaveShoppingList(val shoppingList: ShoppingListModel) : ProductsViewIntent()
        data class DeleteShoppingList(val shoppingListId: String) : ProductsViewIntent()
    }
}