package com.zizohanto.android.tobuy.repository

import com.zizohanto.android.tobuy.cache.ProductCache
import com.zizohanto.android.tobuy.cache.ShoppingListCache
import com.zizohanto.android.tobuy.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.sq.Product
import com.zizohanto.android.tobuy.sq.ShoppingList
import com.zizohanto.android.tobuy.utils.DateUtils.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

interface ShoppingListRepository {
    suspend fun saveShoppingList(shoppingList: ShoppingList)
    fun getShoppingList(id: String): Flow<ShoppingList>
    fun createShoppingList(): Flow<ShoppingList>
    fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts>
    fun getAllShoppingLists(): Flow<List<ShoppingListWithProducts>>
    suspend fun deleteShoppingList(id: String)
    suspend fun deleteAllShoppingLists()
}

class ShoppingListRepositoryImpl(
    private val shoppingListCache: ShoppingListCache,
    private val productCache: ProductCache,
    private val idProvider: IDProvider
) : ShoppingListRepository {

    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        val list = shoppingList.copy(dateModified = getCurrentTime())
        val cachedList = shoppingListCache.getShoppingList(shoppingList.id)
        if (cachedList == null) {
            shoppingListCache.saveShoppingList(list)
        } else {
            shoppingListCache.updateShoppingList(list.id, list.name, list.dateModified)
        }
    }

    override fun getShoppingList(id: String): Flow<ShoppingList> {
        return flow {
            shoppingListCache.getShoppingList(id)?.let {
                emit(it)
            }
        }
    }

    override fun createShoppingList(): Flow<ShoppingList> {
        return flowOf(DataFactory.createShoppingList(idProvider.getId()))
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProducts = shoppingListCache.getShoppingListWithProductsOrNull(id)
            val shoppingList = listWithProducts?.shoppingList ?: DataFactory.createShoppingList(id)
            val products = listWithProducts?.products.takeIf {
                it?.isNotEmpty() == true
            } ?: addNewProduct(id)
            emit(ShoppingListWithProducts(shoppingList, products))
        }
    }

    private suspend fun addNewProduct(id: String): List<Product> =
        listOf(productCache.makeNewProduct(id))

    override fun getAllShoppingLists(): Flow<List<ShoppingListWithProducts>> {
        return flow {
            emit(shoppingListCache.getAllShoppingLists())
        }
    }

    override suspend fun deleteShoppingList(id: String) {
        shoppingListCache.deleteShoppingList(id)
    }

    override suspend fun deleteAllShoppingLists() {
        shoppingListCache.deleteAllShoppingLists()
    }

}