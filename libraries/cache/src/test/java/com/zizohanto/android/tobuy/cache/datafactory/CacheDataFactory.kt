package com.zizohanto.android.tobuy.cache.datafactory

import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import java.util.*

object CacheDataFactory {

    fun makeShoppingListCacheModel(): ShoppingListCacheModel {
        return ShoppingListCacheModel(makeRandomString(), "", 0.0, 0L, 0L)
    }

    fun makeShoppingListCacheModels(count: Int): List<ShoppingListCacheModel> {
        val shoppingLists: MutableList<ShoppingListCacheModel> = mutableListOf()
        for (position in 0 until count) {
            shoppingLists.add(makeShoppingListCacheModel())
        }
        return shoppingLists
    }

    fun makeShoppingListEntity(): ShoppingListEntity {
        return ShoppingListEntity(makeRandomString(), "", 0.0, 0L, 0L)
    }

    fun makeShoppingListEntities(count: Int): List<ShoppingListEntity> {
        val shoppingLists: MutableList<ShoppingListEntity> = mutableListOf()
        for (position in 0 until count) {
            shoppingLists.add(makeShoppingListEntity())
        }
        return shoppingLists
    }

    fun makeProductEntities(
        count: Int,
        shoppingListId: String = makeRandomString()
    ): List<ProductEntity> {
        val products: MutableList<ProductEntity> = mutableListOf()
        for (position in 0 until count) {
            products.add(makeProductEntity(shoppingListId, position))
        }
        return products
    }

    fun makeProductEntity(
        shoppingListId: String,
        position: Int
    ): ProductEntity {
        return ProductEntity(makeRandomString(), shoppingListId, "", 0.0, position)
    }

    fun makeProductCacheModel(shoppingListId: String, position: Int = 0): ProductCacheModel {
        return ProductCacheModel(makeRandomString(), shoppingListId, "", 0.0, position)
    }

    fun makeProductCacheModels(count: Int, shoppingListId: String): List<ProductCacheModel> {
        val products: MutableList<ProductCacheModel> = mutableListOf()
        for (position in 0 until count) {
            products.add(makeProductCacheModel(shoppingListId, position))
        }
        return products
    }

    fun makeRandomString(): String = UUID.randomUUID().toString()

    fun getRandomIntWithinRange(start: Int, end: Int): Int = (start until end).shuffled().first()

}