package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.contract.ProductCache
import com.zizohanto.android.tobuy.domain.contract.ShoppingListCache
import com.zizohanto.android.tobuy.domain.mappers.ProductEntityMapper
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ProductEntity
import com.zizohanto.android.tobuy.domain.models.ShoppingListEntity
import com.zizohanto.android.tobuy.domain.utils.DateUtils.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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

class ProductRepositoryImpl @Inject constructor(
    private val productCache: ProductCache,
    private val shoppingListCache: ShoppingListCache,
    private val mapper: ProductEntityMapper
) : ProductRepository {

    override suspend fun saveProduct(product: Product, shoppingListId: String) {
        val shoppingListEntity: ShoppingListEntity? =
            shoppingListCache.getShoppingList(shoppingListId)
        if (shoppingListEntity == null) {
            shoppingListCache.saveShoppingList(ShoppingListEntity(id = shoppingListId))
        } else {
            shoppingListCache.updateShoppingList(
                shoppingListEntity.id,
                shoppingListEntity.name,
                getCurrentTime()
            )
        }
        val productEntity: ProductEntity = mapper.mapToEntity(product)
        productCache.saveProduct(productEntity)
    }

    override fun createProduct(shoppingListId: String): Flow<Product> {
        return flow {
            val productEntity: ProductEntity = productCache.makeNewProduct(shoppingListId)
            emit(mapper.mapFromEntity(productEntity))
        }
    }

    override fun getProducts(shoppingListId: String): Flow<List<Product>> {
        return flow {
            val productEntities: List<ProductEntity> = productCache.getProducts(shoppingListId)
            if (productEntities.isEmpty()) {
                val productEntity: ProductEntity = productCache.makeNewProduct(shoppingListId)
                val listWithNewProduct: List<Product> = listOf(mapper.mapFromEntity(productEntity))
                emit(listWithNewProduct)
            } else emit(mapper.mapFromEntityList(productEntities))
        }
    }

    override suspend fun deleteProduct(product: Product) {
        val productEntity: ProductEntity = mapper.mapToEntity(product)
        productCache.deleteProduct(productEntity)
    }

    override suspend fun deleteAllProducts() {
        productCache.deleteAllProducts()
    }

    override fun createProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): Flow<List<Product>> {
        return flow {
            emit(
                productCache.makeNewProductAtPosition(shoppingListId, newProductPosition).map {
                    mapper.mapFromEntity(it)
                }
            )
        }
    }

}