package com.zizohanto.android.tobuy.cache.models

import java.util.UUID

data class ProductCacheModel(
    val id: String = UUID.randomUUID().toString(),
    val shoppingListId: String,
    val name: String = "",
    val price: Double = 0.0,
    val position: Int
)