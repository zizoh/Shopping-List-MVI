package com.zizohanto.android.tobuy.presentation.mappers

import com.zizohanto.android.tobuy.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.presentation.models.ShoppingListWithProductsModel

class ShoppingListWithProductsModelMapper (
    private val shoppingListModelMapper: ShoppingListModelMapper,
    private val productModelMapper: ProductModelMapper
) : ModelMapper<ShoppingListWithProductsModel, ShoppingListWithProducts> {

    override fun mapToModel(domain: ShoppingListWithProducts): ShoppingListWithProductsModel {
        return ShoppingListWithProductsModel(
            shoppingListModelMapper.mapToModel(domain.shoppingList),
            productModelMapper.mapToModelList(domain.products)
        )
    }

    override fun mapToDomain(model: ShoppingListWithProductsModel): ShoppingListWithProducts {
        return ShoppingListWithProducts(
            shoppingListModelMapper.mapToDomain(model.shoppingList),
            productModelMapper.mapToDomainList(model.products)
        )
    }
}