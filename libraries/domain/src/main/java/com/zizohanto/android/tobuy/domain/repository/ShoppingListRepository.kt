package com.zizohanto.android.tobuy.domain.repository

import com.zizohanto.android.tobuy.domain.contract.ProductCache
import com.zizohanto.android.tobuy.domain.contract.ShoppingListCache
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.repository.DataFactory.createShoppingList
import com.zizohanto.android.tobuy.domain.sq.Product
import com.zizohanto.android.tobuy.domain.sq.ShoppingList
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
    private val productCache: ProductCache
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
        return flowOf(DataFactory.createShoppingList())
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProducts = shoppingListCache.getShoppingListWithProductsOrNull(id)
            val shoppingList = listWithProducts?.shoppingList ?: createShoppingList(id)
            val products = if (listWithProducts?.products?.isEmpty() == true) {
                addNewProduct(id)
            } else {
                listWithProducts?.products!!
            }
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