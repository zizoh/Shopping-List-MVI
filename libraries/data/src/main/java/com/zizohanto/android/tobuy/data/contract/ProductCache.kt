package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.data.models.ProductEntity

interface ProductCache {
    suspend fun saveProduct(productEntity: ProductEntity)
    suspend fun makeNewProduct(shoppingListId: String): ProductEntity
    suspend fun getProducts(id: String): List<ProductEntity>
    suspend fun deleteProduct(productEntity: ProductEntity)
    suspend fun deleteAllProducts()
    suspend fun makeNewProductAtPosition(
        shoppingListId: String,
        position: Int
    ): ProductEntity?
}