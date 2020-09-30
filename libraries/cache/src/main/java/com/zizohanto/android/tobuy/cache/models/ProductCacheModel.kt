package com.zizohanto.android.tobuy.cache.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    indices = [
        Index(value = ["shoppingListId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListCacheModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("shoppingListId")
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