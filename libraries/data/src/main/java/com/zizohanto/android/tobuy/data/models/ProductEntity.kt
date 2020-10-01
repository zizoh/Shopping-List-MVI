package com.zizohanto.android.tobuy.data.models

import org.joda.time.Instant
import java.util.*

data class ProductEntity(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double,
    val dateAdded: Long
) {
    companion object {
        fun createNewProduct(shoppingListId: String): ProductEntity {
            val id: String = UUID.randomUUID().toString()
            val dateAdded: Long = Instant.now().millis
            return ProductEntity(id, shoppingListId, "", 0.0, dateAdded)
        }
    }
}