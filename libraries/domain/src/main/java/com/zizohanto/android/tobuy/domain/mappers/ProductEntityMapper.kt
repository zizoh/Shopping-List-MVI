package com.zizohanto.android.tobuy.domain.mappers

import com.zizohanto.android.tobuy.domain.mappers.base.EntityMapper
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ProductEntity
import javax.inject.Inject

class ProductEntityMapper @Inject constructor() : EntityMapper<ProductEntity, Product> {

    override fun mapFromEntity(entity: ProductEntity): Product {
        return entity.run {
            Product(id, shoppingListId, name, price, position)
        }
    }

    override fun mapToEntity(domain: Product): ProductEntity {
        return domain.run {
            ProductEntity(id, shoppingListId, name, price, position)
        }
    }
}