package com.zizohanto.android.tobuy.data.mappers

import com.zizohanto.android.tobuy.data.mappers.base.CacheModelMapper
import com.zizohanto.android.tobuy.data.models.ProductCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsCacheModel
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
            productsMapper.mapToModelList(entity.productEntities).sortedBy { it.position }
        return ShoppingListWithProductsCacheModel(shoppingList, products)
    }

    override fun mapToEntity(model: ShoppingListWithProductsCacheModel): ShoppingListWithProductsEntity {
        val shoppingList = shoppingListMapper.mapToEntity(model.shoppingList)
        val products = productsMapper.mapToEntityList(model.products).sortedBy { it.position }
        return ShoppingListWithProductsEntity(shoppingList, products)
    }
}