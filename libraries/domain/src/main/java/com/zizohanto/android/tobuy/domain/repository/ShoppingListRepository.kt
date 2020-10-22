package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun saveShoppingList(shoppingList: ShoppingList)
    fun getShoppingList(id: String): Flow<ShoppingList>
    fun createShoppingList(): Flow<ShoppingList>
    fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts>
    fun getAllShoppingLists(): Flow<List<ShoppingListWithProducts>>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}