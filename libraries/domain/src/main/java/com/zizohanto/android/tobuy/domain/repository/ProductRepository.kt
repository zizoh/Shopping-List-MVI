package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun saveProduct(product: Product, shoppingList: ShoppingList)
    fun createProduct(shoppingListId: String): Flow<Product>
    fun getProduct(id: String): Flow<Product>
    fun getProducts(shoppingListId: String): Flow<List<Product>>
    suspend fun deleteProduct(id: String)
    suspend fun deleteProductAndGetShoppingListWithProducts(product: Product): ShoppingListWithProducts
    suspend fun deleteAllProducts()
}