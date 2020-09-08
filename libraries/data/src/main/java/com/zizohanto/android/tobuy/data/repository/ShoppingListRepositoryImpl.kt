package com.zizohanto.android.tobuy.data.repository

import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
import com.zizohanto.android.tobuy.data.mappers.ShoppingListEntityMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListWithProductsEntityMapper
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListCache: ShoppingListCache,
    private val shoppingListMapper: ShoppingListEntityMapper,
    private val listWithProductsMapper: ShoppingListWithProductsEntityMapper
) : ShoppingListRepository {

    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        val shoppingListEntity: ShoppingListEntity = shoppingListMapper.mapToEntity(shoppingList)
        shoppingListCache.saveShoppingList(shoppingListEntity)
    }

    override fun getShoppingList(id: String): Flow<ShoppingList> {
        return flow {
            val shoppingListEntity: ShoppingListEntity = shoppingListCache.getShoppingList(id)
            emit(shoppingListMapper.mapFromEntity(shoppingListEntity))
        }
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProductsEntity: ShoppingListWithProductsEntity =
                shoppingListCache.getShoppingListWithProducts(id)
            emit(listWithProductsMapper.mapFromEntity(listWithProductsEntity))
        }
    }

    override fun getAllShoppingLists(): Flow<List<ShoppingList>> {
        return flow {
            val shoppingListsEntities: List<ShoppingListEntity> =
                shoppingListCache.getAllShoppingLists()
            emit(shoppingListsEntities.map(shoppingListMapper::mapFromEntity))
        }
    }

    override suspend fun deleteShoppingList(id: String) {
        shoppingListCache.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        shoppingListCache.deleteAllShoppingLists()
    }
}