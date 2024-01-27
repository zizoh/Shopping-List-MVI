package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.cache.sq.Product
import com.zizohanto.android.tobuy.cache.sq.ProductQueries
import com.zizohanto.android.tobuy.data.models.ProductEntity
import javax.inject.Inject

interface ProductCache {
    suspend fun saveProduct(productEntity: ProductEntity)
    suspend fun makeNewProduct(shoppingListId: String): ProductEntity
    suspend fun getProducts(id: String): List<ProductEntity>
    suspend fun deleteProduct(productEntity: ProductEntity)
    suspend fun deleteAllProducts()
    suspend fun makeNewProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): List<ProductEntity>
}

class ProductCacheImpl @Inject constructor(private val queries: ProductQueries) : ProductCache {

    override suspend fun saveProduct(productEntity: ProductEntity) {
        with(productEntity) {
            val cachedProduct = queries.getProductAtPosition(
                shoppingListId,
                position.toLong()
            ).executeAsOneOrNull()
            when {
                cachedProduct == null -> {
                    queries.insertProduct(
                        id,
                        shoppingListId,
                        name,
                        price,
                        position.toLong()
                    )
                }

                queries.productExists(id).executeAsOne() -> {
                    queries.updateProduct(name, position.toLong(), id)
                }

                else -> updateProductsPosition(this)
            }
        }

    }

    private fun updateProductsPosition(newProduct: ProductEntity) {
        val allProductsForId =
            queries.getProducts(newProduct.shoppingListId).executeAsList()
        val pos: Int = newProduct.position
        val newList = allProductsForId.map { model ->
            if (pos.toLong() == model.position) {
                val position = model.position + pos
                model.copy(position = position)
            } else model
        }.toMutableList()
        newList.add(
            Product(
                id = newProduct.id,
                shoppingListId = newProduct.shoppingListId,
                name = newProduct.name,
                price = newProduct.price,
                position = newProduct.position.toLong()
            )
        )
        newList.forEach {
            queries.insertProduct(
                it.id,
                it.shoppingListId,
                it.name,
                it.price,
                it.position
            )
        }
    }

    override suspend fun makeNewProduct(shoppingListId: String): ProductEntity {
        val position: Int? =
            queries.getLastPosition(shoppingListId).executeAsOneOrNull()?.MAX?.toInt()
        val updatedPosition = position?.plus(1) ?: 0
        return ProductEntity(
            shoppingListId = shoppingListId,
            position = updatedPosition
        )
    }

    override suspend fun getProducts(id: String): List<ProductEntity> {
        return queries.getProducts(id).executeAsList().map {
            ProductEntity(
                id = it.id,
                shoppingListId = it.shoppingListId,
                name = it.name,
                price = it.price,
                position = it.position.toInt()
            )
        }
    }

    override suspend fun deleteProduct(productEntity: ProductEntity) {
        queries.deleteProduct(productEntity.id)
        val allProductsForId =
            queries.getProducts(productEntity.shoppingListId).executeAsList()
        val position: Int = productEntity.position
        val newList = allProductsForId.map { model ->
            when (model.position) {
                in position..allProductsForId.size -> {
                    model.copy(position = model.position - 1)
                }

                else -> model
            }
        }.toMutableList()
        newList.forEach {
            queries.insertProduct(
                it.id,
                it.shoppingListId,
                it.name,
                it.price,
                it.position
            )
        }
    }

    override suspend fun deleteAllProducts() {
        queries.deleteAllProducts()
    }

    override suspend fun makeNewProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): List<ProductEntity> {
        val allProducts = queries.getProducts(shoppingListId).executeAsList()
        val lastProductIsNotEmpty = allProducts.lastOrNull()?.name?.isNotEmpty() ?: false
        val newList = if (allProducts.isEmpty() || lastProductIsNotEmpty) {
            val product = ProductEntity(
                shoppingListId = shoppingListId,
                position = newProductPosition
            )
            val updatedProducts = allProducts.mapIndexed { index, model ->
                val isBelowNewProduct = index >= newProductPosition
                if (isBelowNewProduct) model.copy(position = model.position + 1) else model
            }.toMutableList()

            updatedProducts.add(
                newProductPosition,
                Product(
                    product.id,
                    product.shoppingListId,
                    product.name,
                    product.price,
                    product.position.toLong()
                )
            )
            updatedProducts.forEach {
                queries.insertProduct(
                    it.id,
                    it.shoppingListId,
                    it.name,
                    it.price,
                    it.position
                )
            }
            updatedProducts
        } else {
            allProducts
        }
        return newList.map {
            ProductEntity(
                it.id,
                it.shoppingListId,
                it.name,
                it.price,
                it.position.toInt()
            )
        }
    }
}