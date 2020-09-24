package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel

sealed class ProductsViewIntent : ViewIntent {
    object Idle : ProductsViewIntent()
    sealed class ShoppingListViewIntent : ProductsViewIntent() {
        data class LoadShoppingListWithProducts(
            val shoppingListId: String,
            val isNewShoppingList: Boolean
        ) : ProductsViewIntent()

        data class SaveShoppingList(val shoppingList: ShoppingListModel) : ShoppingListViewIntent()
    }

    sealed class ProductViewIntent : ProductsViewIntent() {
        data class EditProduct(val product: ProductModel) : ProductsViewIntent()
        data class SaveProduct(val product: ProductModel) : ProductsViewIntent()
        data class DeleteProduct(val productId: String) : ProductsViewIntent()
    }
}