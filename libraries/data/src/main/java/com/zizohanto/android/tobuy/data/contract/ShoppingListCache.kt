package com.zizohanto.android.tobuy.data.contract

import com.zizohanto.android.tobuy.cache.sq.ShoppingListQueries
import com.zizohanto.android.tobuy.data.mappers.ShoppingListCacheModelMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListWithProductsCacheModelMapper
import com.zizohanto.android.tobuy.data.models.ProductCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
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
    private val queries: ShoppingListQueries,
    private val listMapper: ShoppingListCacheModelMapper,
    private val listWithProductsMapper: ShoppingListWithProductsCacheModelMapper
) : ShoppingListCache {

    override suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity) {
        val shoppingList: ShoppingListCacheModel = listMapper.mapToModel(shoppingListEntity)
        queries.insertShoppingList(
            shoppingList.id,
            shoppingList.name,
            shoppingList.budget,
            shoppingList.dateCreated,
            shoppingList.dateModified
        )
    }

    override suspend fun updateShoppingList(id: String, name: String, dateModified: Long) {
        queries.updateShoppingList(name, dateModified, id)
    }

    override suspend fun getShoppingList(id: String): ShoppingListEntity? {
        val shoppingList = queries.getShoppingListWithId(id).executeAsOneOrNull()
        return if (shoppingList == null) null
        else listMapper.mapToEntity(
            ShoppingListCacheModel(
                shoppingList.id,
                shoppingList.name,
                shoppingList.budget,
                shoppingList.dateCreated,
                shoppingList.dateModified
            )
        )
    }

    override suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProductsEntity? {
        val results = queries.getShoppingListWithProductsOrNull(id).executeAsList()
        val shoppingList = results.firstOrNull()?.let {
            ShoppingListCacheModel(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }
        val products = results.map {
            ProductCacheModel(
                it.id_.orEmpty(),
                it.id,
                it.name_.orEmpty(),
                it.price ?: 0.0,
                (it.position ?: 0).toInt()
            )
        }
        return shoppingList?.let {
            listWithProductsMapper.mapToEntity(ShoppingListWithProductsCacheModel(it, products))
        }
    }

    override suspend fun getAllShoppingLists(): List<ShoppingListWithProductsEntity> {
        val results = queries.getShoppingLists().executeAsList()
        val groupedResults = results.groupBy {
            ShoppingListCacheModel(
                it.id,
                it.name,
                it.budget,
                it.dateCreated,
                it.dateModified
            )
        }

        val models = groupedResults.map { (shoppingList, products) ->
            ShoppingListWithProductsCacheModel(
                shoppingList,
                products.map {
                    ProductCacheModel(
                        it.id_.orEmpty(),
                        it.id,
                        it.name_.orEmpty(),
                        it.price ?: 0.0,
                        (it.position ?: 0).toInt()
                    )
                }
            )
        }
        return listWithProductsMapper.mapToEntityList(models)
    }

    override suspend fun deleteShoppingList(id: String) {
        queries.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        queries.deleteAllShoppingLists()
    }
}