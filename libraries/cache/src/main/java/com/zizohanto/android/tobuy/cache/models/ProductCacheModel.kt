package com.zizohanto.android.tobuy.cache.models

import androidx.room.*

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
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductCacheModel(
    @PrimaryKey
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double
)