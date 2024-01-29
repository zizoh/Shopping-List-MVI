package com.zizohanto.android.tobuy.shopping_list.presentation.mappers

import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shoppinglist.sq.Product
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