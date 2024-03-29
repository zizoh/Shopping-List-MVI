package com.zizohanto.android.tobuy.utilities

import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewState

object DataFactory {
    fun getShoppingListStateWithList(): ShoppingListViewState {
        return ShoppingListViewState(listWithProducts = getShoppingLists())
    }

    fun getProductsViewState(): ProductsViewState {
        return ProductsViewState(getShoppingLists().first())
    }

    private fun getShoppingLists(): List<ShoppingListWithProductsModel> {
        return listOf(
            ShoppingListWithProductsModel(
                getShoppingListModel(),
                listOf(getProductModel())
            )
        )
    }

    private fun getProductModel() = ProductsViewItem.ProductModel("", "", "Vegetables", 19.59, 1)

    private fun getShoppingListModel() =
        ProductsViewItem.ShoppingListModel("", "Weekend", 0.0, 0L, 0L)
}