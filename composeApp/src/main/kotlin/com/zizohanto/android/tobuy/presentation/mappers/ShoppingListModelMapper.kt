package com.zizohanto.android.tobuy.presentation.mappers

import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.sq.ShoppingList

class ShoppingListModelMapper : ModelMapper<ShoppingListModel, ShoppingList> {

    override fun mapToModel(domain: ShoppingList): ShoppingListModel {
        return ShoppingListModel(
            domain.id,
            domain.name,
            domain.budget,
            domain.dateCreated,
            domain.dateModified
        )
    }

    override fun mapToDomain(model: ShoppingListModel): ShoppingList {
        return ShoppingList(
            model.id,
            model.name,
            model.budget,
            model.dateCreated,
            model.dateModified
        )
    }
}