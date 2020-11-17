package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun saveProduct(product: Product, shoppingListId: String)
    fun createProduct(shoppingListId: String): Flow<Product>
    fun getProduct(id: String): Flow<Product>
    fun getProducts(shoppingListId: String): Flow<List<Product>>
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProducts()
    fun createProductAtPosition(
        shoppingListId: String,
        position: Int
    ): Flow<Product>
}