package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity

interface ShoppingListCache {
    suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity)
    suspend fun updateShoppingList(id: String, name: String, dateModified: Long)
    suspend fun getShoppingList(id: String): ShoppingListEntity?
    suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProductsEntity?
    suspend fun getShoppingListWithProducts(id: String): ShoppingListWithProductsEntity
    suspend fun getAllShoppingLists(): List<ShoppingListWithProductsEntity>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}