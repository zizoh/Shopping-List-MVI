package com.zizohanto.android.tobuy.data.models

import org.joda.time.Instant
import java.util.*

data class ShoppingListEntity(
    val id: String,
    val name: String,
    val budget: Double,
    val dateCreated: Long,
    val dateModified: Long
) {
    companion object {
        fun createNewShoppingList(): ShoppingListEntity {
            val shoppingListId: String = UUID.randomUUID().toString()
            return ShoppingListEntity(
                shoppingListId,
                "",
                0.0,
                getCurrentTime(),
                getCurrentTime()
            )

        }

        fun getCurrentTime(): Long {
            return Instant.now().millis
        }
    }
}