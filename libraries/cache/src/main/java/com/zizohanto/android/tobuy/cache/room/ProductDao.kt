package com.zizohanto.android.tobuy.cache.room

import androidx.room.*
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductCacheModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(product: ProductCacheModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: List<ProductCacheModel>)

    @Query("SELECT * FROM product WHERE id=:productId")
    suspend fun getProductWithId(productId: String): ProductCacheModel

    @Query("SELECT * FROM product WHERE shoppingListId=:shoppingListId ORDER BY position")
    suspend fun getProducts(shoppingListId: String): List<ProductCacheModel>

    @Query("SELECT * FROM product WHERE shoppingListId = :shoppingListId AND position = :position LIMIT 1")
    suspend fun getProductAtPosition(
        position: Int,
        shoppingListId: String
    ): ProductCacheModel?

    @Query("SELECT EXISTS(SELECT 1 FROM product WHERE id = :id)")
    fun productExists(id: String): Boolean

    @Query("DELETE FROM product WHERE id=:productId")
    suspend fun deleteProduct(productId: String)

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()

    @Query("SELECT MAX(position) FROM product WHERE shoppingListId = :shoppingListId")
    suspend fun getLastPosition(shoppingListId: String): Int?
}