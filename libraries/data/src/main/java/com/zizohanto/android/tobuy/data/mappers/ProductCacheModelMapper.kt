package com.zizohanto.android.tobuy.data.mappers

import com.zizohanto.android.tobuy.data.mappers.base.CacheModelMapper
import com.zizohanto.android.tobuy.data.models.ProductCacheModel
import com.zizohanto.android.tobuy.data.models.ProductEntity
import javax.inject.Inject

class ProductCacheModelMapper @Inject constructor() :
    CacheModelMapper<ProductCacheModel, ProductEntity> {

    override fun mapToModel(entity: ProductEntity): ProductCacheModel {
        return entity.run {
            ProductCacheModel(id, shoppingListId, name, price, position)
        }
    }

    override fun mapToEntity(model: ProductCacheModel): ProductEntity {
        return model.run {
            ProductEntity(id, shoppingListId, name, price, position)
        }
    }
}