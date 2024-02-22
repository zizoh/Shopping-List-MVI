package com.zizohanto.android.tobuy.cache

import com.zizohanto.android.tobuy.repository.DataFactory.createProduct
import com.zizohanto.android.tobuy.sq.Product
import com.zizohanto.android.tobuy.sq.ProductQueries

interface ProductCache {
    suspend fun saveProduct(product: Product)
    suspend fun makeNewProduct(shoppingListId: String): Product
    suspend fun getProducts(id: String): List<Product>
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProducts()
    suspend fun makeNewProductAtPosition(
        shoppingListId: String,
        newProductPosition: Int
    ): List<Product>
}

class ProductCacheImpl(private val queries: ProductQueries) : ProductCache {

    override suspend fun saveProduct(product: Product) {
        with(product) {
            val cachedProduct = queries.getProductAtPosition(
                shoppingListId,
                position
            ).executeAsOneOrNull()
            when {
                cachedProduct == null -> {
                    queries.insertProduct(
                        id,
                        shoppingListId,
                        name,
                        price,
                        position
                    )
                }
                queries.productExists(id).executeAsOne() -> {
                    queries.updateProduct(name, position, id)
                }
                else -> updateProductsPosition(this)
            }
        }

    }

    private fun updateProductsPosition(newProduct: Product) {
        val allProductsForId = queries.getProducts(newProduct.shoppingListId).executeAsList()
        val pos = newProduct.position
        val newList = allProductsForId.map { model ->
            if (pos == model.position) {
                val position = model.position + pos
                model.copy(position = position)
            } else model
        }.toMutableList()
        newList.add(newProduct)
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

    override suspend fun makeNewProduct(shoppingListId: String): Product {
        val position: Int? =
            queries.getLastPosition(shoppingListId).executeAsOneOrNull()?.MAX?.toInt()
        val updatedPosition = position?.plus(1) ?: 0
        return createProduct(shoppingListId, updatedPosition)
    }

    override suspend fun getProducts(id: String): List<Product> {
        return queries.getProducts(id).executeAsList()
    }

    override suspend fun deleteProduct(product: Product) {
        queries.deleteProduct(product.id)
        val allProductsForId =
            queries.getProducts(product.shoppingListId).executeAsList()
        val position = product.position
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
    ): List<Product> {
        val allProducts = queries.getProducts(shoppingListId).executeAsList()
        val lastProductIsNotEmpty = allProducts.lastOrNull()?.name?.isNotEmpty() ?: false
        val newList = if (allProducts.isEmpty() || lastProductIsNotEmpty) {
            val product = createProduct(shoppingListId, newProductPosition)
            val updatedProducts = allProducts.mapIndexed { index, model ->
                val isBelowNewProduct = index >= newProductPosition
                if (isBelowNewProduct) model.copy(position = model.position + 1) else model
            }.toMutableList()

            updatedProducts.add(newProductPosition, product)
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
        return newList
    }
}