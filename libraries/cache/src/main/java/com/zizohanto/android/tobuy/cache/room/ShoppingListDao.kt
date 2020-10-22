package com.zizohanto.android.tobuy.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListWithProductsCacheModel

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingListCacheModel)

    @Query("UPDATE shopping_list SET name=:name, dateModified=:dateModified WHERE id=:shoppingListId")
    suspend fun updateShoppingList(shoppingListId: String, name: String, dateModified: Long)

    @Query("SELECT * FROM shopping_list WHERE id=:shoppingListId")
    suspend fun getShoppingListWithId(shoppingListId: String): ShoppingListCacheModel?

    @Query("SELECT * FROM shopping_list")
    suspend fun getShoppingLists(): List<ShoppingListWithProductsCacheModel>

    @Query("SELECT * FROM shopping_list WHERE id=:shoppingListId")
    suspend fun getShoppingListWithProductsOrNull(shoppingListId: String): ShoppingListWithProductsCacheModel?

    @Query("SELECT * FROM shopping_list WHERE id=:shoppingListId")
    suspend fun getShoppingListWithProducts(shoppingListId: String): ShoppingListWithProductsCacheModel

    @Query("DELETE FROM shopping_list WHERE id=:shoppingListId")
    suspend fun deleteShoppingList(shoppingListId: String)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAllShoppingLists()
}