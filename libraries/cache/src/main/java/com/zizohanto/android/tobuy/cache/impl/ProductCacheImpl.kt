package com.zizohanto.android.tobuy.cache.impl

import com.zizohanto.android.tobuy.cache.mappers.ProductCacheModelMapper
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.cache.room.ProductDao
import com.zizohanto.android.tobuy.data.contract.ProductCache
import com.zizohanto.android.tobuy.data.models.ProductEntity
import javax.inject.Inject

class ProductCacheImpl @Inject constructor(
    private val dao: ProductDao,
    private val mapper: ProductCacheModelMapper
) : ProductCache {

    override suspend fun saveProduct(productEntity: ProductEntity) {
        val cacheModel: ProductCacheModel = mapper.mapToModel(productEntity)
        dao.insertProduct(cacheModel)
    }

    override suspend fun getProduct(id: String): ProductEntity {
        val productEntity: ProductCacheModel = dao.getProductWithId(id)
        return mapper.mapToEntity(productEntity)
    }

    override suspend fun deleteProduct(id: String) {
        dao.deleteProduct(id)
    }

    override suspend fun deleteAllProducts() {
        dao.deleteAllProducts()
    }
}