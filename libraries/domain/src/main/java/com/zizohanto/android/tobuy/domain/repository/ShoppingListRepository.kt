package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.contract.ProductCache
import com.zizohanto.android.tobuy.domain.contract.ShoppingListCache
import com.zizohanto.android.tobuy.domain.mappers.ProductEntityMapper
import com.zizohanto.android.tobuy.domain.mappers.ShoppingListEntityMapper
import com.zizohanto.android.tobuy.domain.mappers.ShoppingListWithProductsEntityMapper
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListEntity
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.domain.utils.DateUtils.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface ShoppingListRepository {
    suspend fun saveShoppingList(shoppingList: ShoppingList)
    fun getShoppingList(id: String): Flow<ShoppingList>
    fun createShoppingList(): Flow<ShoppingList>
    fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts>
    fun getAllShoppingLists(): Flow<List<ShoppingListWithProducts>>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListCache: ShoppingListCache,
    private val productCache: ProductCache,
    private val listMapper: ShoppingListEntityMapper,
    private val listWithProductsMapper: ShoppingListWithProductsEntityMapper,
    private val productMapper: ProductEntityMapper
) : ShoppingListRepository {

    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        val list: ShoppingListEntity =
            listMapper.mapToEntity(shoppingList).copy(dateModified = getCurrentTime())
        val shoppingListEntity: ShoppingListEntity? =
            shoppingListCache.getShoppingList(shoppingList.id)
        if (shoppingListEntity == null) {
            shoppingListCache.saveShoppingList(list)
        } else {
            shoppingListCache.updateShoppingList(list.id, list.name, list.dateModified)
        }
    }

    override fun getShoppingList(id: String): Flow<ShoppingList> {
        return flow {
            val shoppingListEntity: ShoppingListEntity? = shoppingListCache.getShoppingList(id)
            if (shoppingListEntity != null) {
                emit(listMapper.mapFromEntity(shoppingListEntity))
            }
        }
    }

    override fun createShoppingList(): Flow<ShoppingList> {
        val shoppingListEntity = ShoppingListEntity()
        return flowOf(listMapper.mapFromEntity(shoppingListEntity))
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProductsEntity: ShoppingListWithProductsEntity? =
                shoppingListCache.getShoppingListWithProductsOrNull(id)
            if (listWithProductsEntity == null) {
                val shoppingListEntity = ShoppingListEntity(id = id)
                val shoppingList: ShoppingList =
                    listMapper.mapFromEntity(shoppingListEntity)
                val shoppingListWithProducts =
                    ShoppingListWithProducts(shoppingList, addNewProduct(id))
                emit(shoppingListWithProducts)
            } else {
                val listWithProducts: ShoppingListWithProducts =
                    listWithProductsMapper.mapFromEntity(listWithProductsEntity)
                if (listWithProducts.products.isEmpty()) {
                    emit(listWithProducts.copy(products = addNewProduct(id)))
                } else emit(listWithProducts)
            }
        }
    }

    private suspend fun addNewProduct(id: String): List<Product> =
        listOf(productMapper.mapFromEntity(productCache.makeNewProduct(id)))

    override fun getAllShoppingLists(): Flow<List<ShoppingListWithProducts>> {
        return flow {
            val shoppingListsEntities: List<ShoppingListWithProductsEntity> =
                shoppingListCache.getAllShoppingLists()
            emit(shoppingListsEntities.map(listWithProductsMapper::mapFromEntity))
        }
    }

    override suspend fun deleteShoppingList(id: String) {
        shoppingListCache.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        shoppingListCache.deleteAllShoppingLists()
    }

}