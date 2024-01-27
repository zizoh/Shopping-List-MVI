package com.zizohanto.android.tobuy.domain.mappers

import com.zizohanto.android.tobuy.domain.mappers.base.EntityMapper
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListEntity
import javax.inject.Inject

class ShoppingListEntityMapper @Inject constructor() :
    EntityMapper<ShoppingListEntity, ShoppingList> {

    override fun mapFromEntity(entity: ShoppingListEntity): ShoppingList {
        return entity.run {
            ShoppingList(id, name, budget, dateCreated, dateModified)
        }
    }

    override fun mapToEntity(domain: ShoppingList): ShoppingListEntity {
        return domain.run {
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