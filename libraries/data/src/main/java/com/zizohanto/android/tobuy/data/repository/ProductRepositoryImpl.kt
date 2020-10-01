package com.zizohanto.android.tobuy.data.repository

import com.zizohanto.android.tobuy.data.contract.ProductCache
import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
import com.zizohanto.android.tobuy.data.mappers.ProductEntityMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListEntityMapper
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.data.models.ProductEntity.Companion.createNewProduct
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productCache: ProductCache,
    private val shoppingListCache: ShoppingListCache,
    private val mapper: ProductEntityMapper,
    private val listMapper: ShoppingListEntityMapper
) : ProductRepository {

    override suspend fun saveProduct(product: Product, shoppingList: ShoppingList) {
        val shoppingListEntity: ShoppingListEntity? =
            shoppingListCache.getShoppingList(shoppingList.id)
        if (shoppingListEntity == null) {
            shoppingListCache.saveShoppingList(listMapper.mapToEntity(shoppingList))
        }
        val productEntity: ProductEntity = mapper.mapToEntity(product)
        productCache.saveProduct(productEntity)
    }

    override fun createProduct(shoppingListId: String): Flow<Product> {
        val productEntity = createNewProduct(shoppingListId)
        return flowOf(mapper.mapFromEntity(productEntity))
    }

    override fun getProduct(id: String): Flow<Product> {
        return flow {
            val productEntity: ProductEntity = productCache.getProduct(id)
            emit(mapper.mapFromEntity(productEntity))
        }
    }

    override fun getProducts(shoppingListId: String): Flow<List<Product>> {
        return flow {
            val productEntities: List<ProductEntity> = productCache.getProducts(shoppingListId)
            if (productEntities.isEmpty()) {
                val productEntity: ProductEntity = createNewProduct(shoppingListId)
                val listWithNewProduct: List<Product> = listOf(mapper.mapFromEntity(productEntity))
                emit(listWithNewProduct)
            } else emit(mapper.mapFromEntityList(productEntities))
        }
    }

    override suspend fun deleteProduct(id: String) {
        productCache.deleteProduct(id)
    }

    override suspend fun deleteAllProducts() {
        productCache.deleteAllProducts()
    }

}