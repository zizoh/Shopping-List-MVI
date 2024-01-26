package com.zizohanto.android.tobuy.cache.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "product",
    indices = [
        Index(value = ["shoppingListId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListCacheModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("shoppingListId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductCacheModel(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val shoppingListId: String,
    val name: String = "",
    val price: Double = 0.0,
    val position: Int
)