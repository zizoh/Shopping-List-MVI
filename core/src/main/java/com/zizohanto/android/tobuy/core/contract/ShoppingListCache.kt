package com.zizohanto.android.tobuy.core.contract

import com.zizohanto.android.tobuy.core.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.sq.Product
import com.zizohanto.android.tobuy.domain.sq.ShoppingList
import com.zizohanto.android.tobuy.domain.sq.ShoppingListQueries
import javax.inject.Inject

interface ShoppingListCache {
    suspend fun saveShoppingList(shoppingList: ShoppingList)
    suspend fun updateShoppingList(id: String, name: String, dateModified: Long)
    suspend fun getShoppingList(id: String): ShoppingList?
    suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProducts?
    suspend fun getAllShoppingLists(): List<ShoppingListWithProducts>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}

class ShoppingListCacheImpl @Inject constructor(
    private val queries: ShoppingListQueries
) : ShoppingListCache {

    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        with(shoppingList) {
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

    override suspend fun getShoppingList(id: String): ShoppingList? {
        val shoppingList = queries.getShoppingListWithId(id).executeAsOneOrNull()
        return shoppingList?.let {
            ShoppingList(it.id, it.name, it.budget, it.dateCreated, it.dateModified)
        }
    }

    override suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProducts? {
        val results = queries.getShoppingListWithProductsOrNull(id).executeAsList()
        val shoppingList = results.firstOrNull()?.let {
            ShoppingList(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }
        val products = results.map {
            Product(
                it.id_.orEmpty(),
                it.id,
                it.name_.orEmpty(),
                it.price ?: 0.0,
                it.position ?: 0
            )
        }
        return shoppingList?.let {
            ShoppingListWithProducts(it, products)
        }
    }

    override suspend fun getAllShoppingLists(): List<ShoppingListWithProducts> {
        val results = queries.getShoppingLists().executeAsList()
        val groupedResults = results.groupBy {
            ShoppingList(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }

        val models = groupedResults.map { (shoppingList, products) ->
            ShoppingListWithProducts(
                shoppingList,
                products.map {
                    Product(
                        it.id_.orEmpty(),
                        it.id,
                        it.name_.orEmpty(),
                        it.price ?: 0.0,
                        it.position ?: 0
                    )
                }
            )
        }
        return models.map {
            ShoppingListWithProducts(it.shoppingList, it.products)
        }
    }

    override suspend fun deleteShoppingList(id: String) {
        queries.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        queries.deleteAllShoppingLists()
    }
}