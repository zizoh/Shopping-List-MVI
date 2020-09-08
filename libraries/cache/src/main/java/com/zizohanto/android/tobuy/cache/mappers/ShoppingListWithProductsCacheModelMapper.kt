package com.zizohanto.android.tobuy.cache.mappers

import com.zizohanto.android.tobuy.cache.mappers.base.CacheModelMapper
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListWithProductsCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import javax.inject.Inject

class ShoppingListWithProductsCacheModelMapper @Inject constructor(
    private val shoppingListMapper: ShoppingListCacheModelMapper,
    private val productsMapper: ProductCacheModelMapper,
) : CacheModelMapper<ShoppingListWithProductsCacheModel, ShoppingListWithProductsEntity> {

    override fun mapToModel(entity: ShoppingListWithProductsEntity): ShoppingListWithProductsCacheModel {
        val shoppingList: ShoppingListCacheModel =
            shoppingListMapper.mapToModel(entity.shoppingListEntity)
        val products: List<ProductCacheModel> =
            productsMapper.mapToModelList(entity.productEntities)
        return ShoppingListWithProductsCacheModel(shoppingList, products)
    }

    override fun mapToEntity(model: ShoppingListWithProductsCacheModel): ShoppingListWithProductsEntity {
        val shoppingList = shoppingListMapper.mapToEntity(model.shoppingList)
        val products = productsMapper.mapToEntityList(model.products)
        return ShoppingListWithProductsEntity(shoppingList, products)
    }
}