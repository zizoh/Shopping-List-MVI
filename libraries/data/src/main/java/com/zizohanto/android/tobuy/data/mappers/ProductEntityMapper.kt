package com.zizohanto.android.tobuy.data.mappers

import com.zizohanto.android.tobuy.data.mappers.base.EntityMapper
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.domain.models.Product
import javax.inject.Inject

class ProductEntityMapper @Inject constructor() : EntityMapper<ProductEntity, Product> {

    override fun mapFromEntity(entity: ProductEntity): Product {
        return entity.run {
            Product(id, shoppingListId, name, price, dateAdded)
        }
    }

    override fun mapToEntity(domain: Product): ProductEntity {
        return domain.run {
            ProductEntity(id, shoppingListId, name, price, dateAdded)
        }
    }
}