package com.zizohanto.android.tobuy.shopping_list.presentation.models

sealed class ProductsViewItem {
    data class ShoppingListModel(
        val id: String,
        val name: String,
        val budget: Double,
        val dateCreated: Long,
        val dateModified: Long
    ) : ProductsViewItem()

    data class ProductModel(
        val id: String,
        val shoppingListId: String,
        val name: String,
        val price: Double,
        val position: Int
    ) : ProductsViewItem()

    object ButtonItem : ProductsViewItem()
}