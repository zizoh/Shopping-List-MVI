package com.zizohanto.android.tobuy.cache.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "shopping_list")
data class ShoppingListCacheModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val budget: Double = 0.0,
    val dateCreated: Long,
    val dateModified: Long
)

data class ShoppingListWithProductsCacheModel(
    @Embedded val shoppingList: ShoppingListCacheModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val products: List<ProductCacheModel>
)