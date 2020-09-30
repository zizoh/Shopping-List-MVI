package com.zizohanto.android.tobuy.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductCacheModel)

    @Query("SELECT * FROM product WHERE id=:productId")
    suspend fun getProductWithId(productId: String): ProductCacheModel

    @Query("SELECT * FROM product WHERE shoppingListId=:shoppingListId")
    suspend fun getProducts(shoppingListId: String): List<ProductCacheModel>

    @Query("DELETE FROM product WHERE id=:productId")
    suspend fun deleteProduct(productId: String)

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()
}