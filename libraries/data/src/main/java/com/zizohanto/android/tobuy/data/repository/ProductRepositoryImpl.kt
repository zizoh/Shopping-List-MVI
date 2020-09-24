package com.zizohanto.android.tobuy.data.repository

import com.zizohanto.android.tobuy.data.contract.ProductCache
import com.zizohanto.android.tobuy.data.mappers.ProductEntityMapper
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productCache: ProductCache,
    private val mapper: ProductEntityMapper
) : ProductRepository {

    override suspend fun saveProduct(product: Product) {
        val productEntity: ProductEntity = mapper.mapToEntity(product)
        productCache.saveProduct(productEntity)
    }

    override suspend fun getProduct(id: String): Flow<Product> {
        return flow {
            val productEntity: ProductEntity = productCache.getProduct(id)
            emit(mapper.mapFromEntity(productEntity))
        }
    }

    override suspend fun deleteProduct(id: String) {
        productCache.deleteProduct(id)
    }

    override suspend fun deleteAllProducts() {
        productCache.deleteAllProducts()
    }
}