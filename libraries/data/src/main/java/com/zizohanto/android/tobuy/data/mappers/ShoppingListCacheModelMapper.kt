package com.zizohanto.android.tobuy.data.mappers

import com.zizohanto.android.tobuy.data.mappers.base.CacheModelMapper
import com.zizohanto.android.tobuy.data.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import javax.inject.Inject

class ShoppingListCacheModelMapper @Inject constructor() :
    CacheModelMapper<ShoppingListCacheModel, ShoppingListEntity> {

    override fun mapToModel(entity: ShoppingListEntity): ShoppingListCacheModel {
        return entity.run {
            ShoppingListCacheModel(id, name, budget, dateCreated, dateModified)
        }
    }

    override fun mapToEntity(model: ShoppingListCacheModel): ShoppingListEntity {
        return model.run {
            ShoppingListEntity(
                id,
                name,
                budget,
                dateCreated,
                dateModified
            )
        }
    }
}