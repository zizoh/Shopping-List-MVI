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
        val cacheModel: ProductCacheModel = dao.getProductWithId(id)
        return mapper.mapToEntity(cacheModel)
    }

    override suspend fun getProducts(id: String): List<ProductEntity> {
        val cacheModels: List<ProductCacheModel> = dao.getProducts(id)
        return mapper.mapToEntityList(cacheModels)
    }

    override suspend fun deleteProduct(id: String) {
        dao.deleteProduct(id)
    }

    override suspend fun deleteAllProducts() {
        dao.deleteAllProducts()
    }
}