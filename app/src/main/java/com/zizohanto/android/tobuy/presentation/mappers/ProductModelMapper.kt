package com.zizohanto.android.tobuy.presentation.mappers

import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.sq.Product
import javax.inject.Inject

class ProductModelMapper @Inject constructor() : ModelMapper<ProductModel, Product> {

    override fun mapToModel(domain: Product): ProductModel {
        return domain.run {
            ProductModel(
                id,
                shoppingListId,
                name,
                price,
                position.toInt()
            )
        }
    }

    override fun mapToDomain(model: ProductModel): Product {
        return model.run {
            Product(
                id,
                shoppingListId,
                name,
                price,
                position.toLong()
            )
        }
    }
}