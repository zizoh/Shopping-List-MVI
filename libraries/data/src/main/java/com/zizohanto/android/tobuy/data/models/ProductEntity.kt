package com.zizohanto.android.tobuy.data.models

data class ProductEntity(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double,
    val position: Int
)