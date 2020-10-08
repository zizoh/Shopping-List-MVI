package com.zizohanto.android.tobuy.shopping_list.presentation.models

data class ShoppingListWithProductsModel(
    val shoppingList: ShoppingListModel,
    val products: List<ProductModel>
)