package com.zizohanto.android.tobuy.shopping_list.presentation.mappers

import com.zizohanto.android.tobuy.core.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import javax.inject.Inject

class ShoppingListWithProductsModelMapper @Inject constructor(
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