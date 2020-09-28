package com.zizohanto.android.tobuy.data.repository

import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
import com.zizohanto.android.tobuy.data.mappers.ShoppingListEntityMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListWithProductsEntityMapper
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*
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

    override fun createShoppingList(): Flow<ShoppingList> {
        return flowOf(createNewShoppingList())
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProductsEntity: ShoppingListWithProductsEntity? =
                shoppingListCache.getShoppingListWithProducts(id)
            if (listWithProductsEntity == null) {
                val shoppingList: ShoppingList = createNewShoppingList().copy(id = id)
                val newProduct: Product = createNewProduct(id)
                val shoppingListWithProducts =
                    ShoppingListWithProducts(shoppingList, listOf(newProduct))
                emit(shoppingListWithProducts)
            } else {
                emit(listWithProductsMapper.mapFromEntity(listWithProductsEntity))
            }
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

    companion object {
        fun createNewShoppingList(): ShoppingList {
            val shoppingListId: String = UUID.randomUUID().toString()
            val formattedDate: String = getCurrentTime()
            return ShoppingList(
                shoppingListId,
                "",
                0.0,
                formattedDate,
                formattedDate
            )

        }

        private fun getCurrentTime(): String {
            val fmt: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date: LocalDate = LocalDate.now()
            return date.toString(fmt)
        }

        fun createNewProduct(shoppingListId: String): Product {
            val id: String = UUID.randomUUID().toString()
            return Product(id, shoppingListId, "", 0.0)
        }
    }
}