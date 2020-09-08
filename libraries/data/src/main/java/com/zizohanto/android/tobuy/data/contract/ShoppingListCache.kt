package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity

interface ShoppingListCache {
    suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity)
    suspend fun getShoppingList(id: String): ShoppingListEntity
    suspend fun getShoppingListWithProducts(id: String): ShoppingListWithProductsEntity
    suspend fun getAllShoppingLists(): List<ShoppingListEntity>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}