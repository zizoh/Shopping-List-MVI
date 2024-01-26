package com.zizohanto.android.tobuy.cache.models

data class ShoppingListCacheModel(
    val id: String,
    val name: String,
    val budget: Double = 0.0,
    val dateCreated: Long,
    val dateModified: Long
)

data class ShoppingListWithProductsCacheModel(
    val shoppingList: ShoppingListCacheModel,
    val products: List<ProductCacheModel>
)