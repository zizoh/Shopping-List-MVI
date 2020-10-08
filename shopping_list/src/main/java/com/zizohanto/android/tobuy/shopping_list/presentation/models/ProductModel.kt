package com.zizohanto.android.tobuy.shopping_list.presentation.models

data class ProductModel(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double,
    val dateAdded: Long
)
