package com.zizohanto.android.tobuy.data.repository

import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
import com.zizohanto.android.tobuy.data.mappers.ProductEntityMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListEntityMapper
import com.zizohanto.android.tobuy.data.mappers.ShoppingListWithProductsEntityMapper
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity.Companion.createNewShoppingList
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListCache: ShoppingListCache,
    private val shoppingListMapper: ShoppingListEntityMapper,
    private val listWithProductsMapper: ShoppingListWithProductsEntityMapper,
    private val productMapper: ProductEntityMapper
) : ShoppingListRepository {

    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        val shoppingListEntity: ShoppingListEntity = shoppingListMapper.mapToEntity(shoppingList)
        shoppingListCache.saveShoppingList(shoppingListEntity)
    }

    override fun getShoppingList(id: String): Flow<ShoppingList> {
        return flow {
            val shoppingListEntity: ShoppingListEntity? = shoppingListCache.getShoppingList(id)
            if (shoppingListEntity != null) {
                emit(shoppingListMapper.mapFromEntity(shoppingListEntity))
            }
        }
    }

    override fun createShoppingList(): Flow<ShoppingList> {
        val shoppingListEntity: ShoppingListEntity = createNewShoppingList()
        return flowOf(shoppingListMapper.mapFromEntity(shoppingListEntity))
    }

    override fun getShoppingListWithProducts(id: String): Flow<ShoppingListWithProducts> {
        return flow {
            val listWithProductsEntity: ShoppingListWithProductsEntity? =
                shoppingListCache.getShoppingListWithProducts(id)
            if (listWithProductsEntity == null) {
                val shoppingListEntity: ShoppingListEntity = createNewShoppingList().copy(id = id)
                val shoppingList: ShoppingList =
                    shoppingListMapper.mapFromEntity(shoppingListEntity)
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

    private fun addNewProduct(id: String): List<Product> {
        val newProduct: ProductEntity = ProductEntity.createNewProduct(id)
        return listOf(productMapper.mapFromEntity(newProduct))
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