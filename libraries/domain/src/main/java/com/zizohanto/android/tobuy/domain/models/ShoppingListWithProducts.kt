package com.zizohanto.android.tobuy.domain.models

data class ShoppingListWithProducts(
    val shoppingList: ShoppingList,
    val products: List<Product>
)