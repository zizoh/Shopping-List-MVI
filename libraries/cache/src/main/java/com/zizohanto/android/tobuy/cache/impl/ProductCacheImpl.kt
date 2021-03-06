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
        val newProduct: ProductCacheModel = mapper.mapToModel(productEntity)
        val cachedProduct: ProductCacheModel? =
            dao.getProductAtPosition(
                newProduct.position,
                newProduct.shoppingListId
            )
        when {
            cachedProduct == null -> dao.insertProduct(newProduct)
            dao.productExists(newProduct.id) -> dao.updateProduct(newProduct)
            else -> updateProductsPosition(newProduct)
        }

    }

    private suspend fun updateProductsPosition(newProduct: ProductCacheModel) {
        val allProductsForId: List<ProductCacheModel> =
            dao.getProducts(newProduct.shoppingListId)
        var pos: Int = newProduct.position
        val newList: MutableList<ProductCacheModel> = allProductsForId.map { model ->
            if (pos == model.position) {
                model.copy(position = ++pos)
            } else model
        }.toMutableList()
        newList.add(newProduct)
        dao.insertProducts(newList)
    }

    override suspend fun makeNewProduct(shoppingListId: String): ProductEntity {
        val position: Int? = dao.getLastPosition(shoppingListId)
        return if (position != null) {
            mapper.mapToEntity(
                ProductCacheModel(
                    shoppingListId = shoppingListId,
                    position = position + 1
                )
            )
        } else {
            mapper.mapToEntity(
                ProductCacheModel(
                    shoppingListId = shoppingListId,
                    position = 0
                )
            )
        }
    }

    override suspend fun getProducts(id: String): List<ProductEntity> {
        val cacheModels: List<ProductCacheModel> = dao.getProducts(id)
        return mapper.mapToEntityList(cacheModels)
    }

    override suspend fun deleteProduct(productEntity: ProductEntity) {
        dao.deleteProduct(productEntity.id)
        val allProductsForId: List<ProductCacheModel> =
            dao.getProducts(productEntity.shoppingListId)
        val position: Int = productEntity.position
        val newList: MutableList<ProductCacheModel> = allProductsForId.map { model ->
            when (model.position) {
                in position..allProductsForId.size -> {
                    model.copy(position = model.position - 1)
                }
                else -> model
            }
        }.toMutableList()
        dao.insertProducts(newList)
    }

    override suspend fun deleteAllProducts() {
        dao.deleteAllProducts()
    }

    override suspend fun makeNewProductAtPosition(
        shoppingListId: String,
        position: Int
    ): ProductEntity {
        val product = ProductCacheModel(
            shoppingListId = shoppingListId,
            position = position
        )
        val allProductsForId: List<ProductCacheModel> =
            dao.getProducts(shoppingListId)
        val newList: MutableList<ProductCacheModel> = allProductsForId.map { model ->
            when (model.position) {
                in position..allProductsForId.size -> {
                    model.copy(position = model.position + 1)
                }
                else -> model
            }
        }.toMutableList()
        newList.add(product)
        dao.insertProducts(newList)
        return mapper.mapToEntity(product)
    }
}