package com.zizohanto.android.tobuy.repository

import com.zizohanto.android.tobuy.cache.ProductCache
import com.zizohanto.android.tobuy.cache.ShoppingListCache
import com.zizohanto.android.tobuy.repository.DataFactory.createShoppingList
import com.zizohanto.android.tobuy.utils.DateUtils.getCurrentTime
import com.zizohanto.android.tobuy.sq.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProductRepository {
    suspend fun saveProduct(product: Product, shoppingListId: String)
    fun createProduct(shoppingListId: String): Flow<Product>
    fun getProducts(shoppingListId: String): Flow<List<Product>>
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProducts()
    fun createProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): Flow<List<Product>>
}

class ProductRepositoryImpl(
    private val productCache: ProductCache,
    private val shoppingListCache: ShoppingListCache
) : ProductRepository {

    override suspend fun saveProduct(product: Product, shoppingListId: String) {
        val shoppingList =
            shoppingListCache.getShoppingList(shoppingListId)
        if (shoppingList == null) {
            shoppingListCache.saveShoppingList(createShoppingList(id = shoppingListId))
        } else {
            shoppingListCache.updateShoppingList(
                shoppingList.id,
                shoppingList.name,
                getCurrentTime()
            )
        }
        productCache.saveProduct(product)
    }

    override fun createProduct(shoppingListId: String): Flow<Product> {
        return flow {
            emit(productCache.makeNewProduct(shoppingListId))
        }
    }

    override fun getProducts(shoppingListId: String): Flow<List<Product>> {
        return flow {
            val products = productCache.getProducts(shoppingListId).ifEmpty {
                listOf(productCache.makeNewProduct(shoppingListId))
            }
            emit(products)
        }
    }

    override suspend fun deleteProduct(product: Product) {
        productCache.deleteProduct(product)
    }

    override suspend fun deleteAllProducts() {
        productCache.deleteAllProducts()
    }

    override fun createProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): Flow<List<Product>> {
        return flow {
            emit(productCache.makeNewProductAtPosition(shoppingListId, newProductPosition))
        }
    }

}