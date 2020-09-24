package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun saveProduct(product: Product)
    suspend fun getProduct(id: String): Flow<Product>
    suspend fun deleteProduct(id: String)
    suspend fun deleteAllProducts()
}