package com.zizohanto.android.tobuy.domain.contract

import com.zizohanto.android.tobuy.domain.models.ProductEntity
import com.zizohanto.android.tobuy.domain.sq.ShoppingListQueries
import com.zizohanto.android.tobuy.domain.models.ShoppingListEntity
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProductsEntity
import javax.inject.Inject

interface ShoppingListCache {
    suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity)
    suspend fun updateShoppingList(id: String, name: String, dateModified: Long)
    suspend fun getShoppingList(id: String): ShoppingListEntity?
    suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProductsEntity?
    suspend fun getAllShoppingLists(): List<ShoppingListWithProductsEntity>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}

class ShoppingListCacheImpl @Inject constructor(
    private val queries: ShoppingListQueries
) : ShoppingListCache {

    override suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity) {
        with(shoppingListEntity) {
            queries.insertShoppingList(
                id,
                name,
                budget,
                dateCreated,
                dateModified
            )
        }
    }

    override suspend fun updateShoppingList(id: String, name: String, dateModified: Long) {
        queries.updateShoppingList(name, dateModified, id)
    }

    override suspend fun getShoppingList(id: String): ShoppingListEntity? {
        val shoppingList = queries.getShoppingListWithId(id).executeAsOneOrNull()
        return shoppingList?.let {
            ShoppingListEntity(it.id, it.name, it.budget, it.dateCreated, it.dateModified)
        }
    }

    override suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProductsEntity? {
        val results = queries.getShoppingListWithProductsOrNull(id).executeAsList()
        val shoppingList = results.firstOrNull()?.let {
            ShoppingListEntity(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }
        val products = results.map {
            ProductEntity(
                it.id_.orEmpty(),
                it.id,
                it.name_.orEmpty(),
                it.price ?: 0.0,
                (it.position ?: 0).toInt()
            )
        }
        return shoppingList?.let {
            ShoppingListWithProductsEntity(it, products)
        }
    }

    override suspend fun getAllShoppingLists(): List<ShoppingListWithProductsEntity> {
        val results = queries.getShoppingLists().executeAsList()
        val groupedResults = results.groupBy {
            ShoppingListEntity(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }

        val models = groupedResults.map { (shoppingList, products) ->
            ShoppingListWithProductsEntity(
                shoppingList,
                products.map {
                    ProductEntity(
                        it.id_.orEmpty(),
                        it.id,
                        it.name_.orEmpty(),
                        it.price ?: 0.0,
                        (it.position ?: 0).toInt()
                    )
                }
            )
        }
        return models.map {
            ShoppingListWithProductsEntity(it.shoppingList, it.products)
        }
    }

    override suspend fun deleteShoppingList(id: String) {
        queries.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        queries.deleteAllShoppingLists()
    }
}