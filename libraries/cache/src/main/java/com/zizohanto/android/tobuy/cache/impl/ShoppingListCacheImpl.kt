package com.zizohanto.android.tobuy.cache.impl

import com.zizohanto.android.tobuy.cache.mappers.ShoppingListCacheModelMapper
import com.zizohanto.android.tobuy.cache.mappers.ShoppingListWithProductsCacheModelMapper
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListWithProductsCacheModel
import com.zizohanto.android.tobuy.cache.room.ShoppingListDao
import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import javax.inject.Inject

class ShoppingListCacheImpl @Inject constructor(
    private val dao: ShoppingListDao,
    private val listMapper: ShoppingListCacheModelMapper,
    private val listWithProductsMapper: ShoppingListWithProductsCacheModelMapper
) : ShoppingListCache {

    override suspend fun saveShoppingList(shoppingListEntity: ShoppingListEntity) {
        val shoppingList: ShoppingListCacheModel = listMapper.mapToModel(shoppingListEntity)
        dao.insertShoppingList(shoppingList)
    }

    override suspend fun updateShoppingList(id: String, name: String, dateModified: Long) {
        dao.updateShoppingList(id, name, dateModified)
    }

    override suspend fun getShoppingList(id: String): ShoppingListEntity? {
        val shoppingList: ShoppingListCacheModel? = dao.getShoppingListWithId(id)
        return if (shoppingList == null) null
        else listMapper.mapToEntity(shoppingList)
    }

    override suspend fun getShoppingListWithProductsOrNull(id: String): ShoppingListWithProductsEntity? {
        val listWithProducts: ShoppingListWithProductsCacheModel? =
            dao.getShoppingListWithProductsOrNull(id)
        return if (listWithProducts == null) null
        else listWithProductsMapper.mapToEntity(listWithProducts)
    }

    override suspend fun getShoppingListWithProducts(id: String): ShoppingListWithProductsEntity {
        val listWithProducts: ShoppingListWithProductsCacheModel =
            dao.getShoppingListWithProducts(id)
        return listWithProductsMapper.mapToEntity(listWithProducts)
    }

    override suspend fun getAllShoppingLists(): List<ShoppingListWithProductsEntity> {
        val models: List<ShoppingListWithProductsCacheModel> = dao.getShoppingLists()
        return listWithProductsMapper.mapToEntityList(models)
    }

    override suspend fun deleteShoppingList(id: String) {
        dao.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        dao.deleteAllShoppingLists()
    }
}