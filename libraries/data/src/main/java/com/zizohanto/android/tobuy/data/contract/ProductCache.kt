package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.data.models.ProductEntity

interface ProductCache {
    suspend fun saveProduct(productEntity: ProductEntity)
    suspend fun getProduct(id: String): ProductEntity
    suspend fun deleteProduct(id: String)
    suspend fun deleteAllProducts()
}