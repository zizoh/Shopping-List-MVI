package com.zizohanto.android.tobuy.domain.models

import java.util.*

data class Product(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double
) {
    companion object {
        fun createNewProduct(shoppingListId: String): Product {
            val id: String = UUID.randomUUID().toString()
            return Product(id, shoppingListId, "New product", 0.0)
        }
    }
}