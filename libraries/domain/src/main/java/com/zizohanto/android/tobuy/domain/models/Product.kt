package com.zizohanto.android.tobuy.domain.models

data class Product(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double
)