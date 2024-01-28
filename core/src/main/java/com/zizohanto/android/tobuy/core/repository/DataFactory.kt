package com.zizohanto.android.tobuy.core.repository

import com.zizohanto.android.tobuy.domain.sq.Product
import com.zizohanto.android.tobuy.domain.sq.ShoppingList
import com.zizohanto.android.tobuy.core.utils.DateUtils
import java.util.UUID

object DataFactory {
    fun createShoppingList(
        id: String = UUID.randomUUID().toString()
    ) = ShoppingList(
        id = id,
        name = "",
        budget = 0.0,
        dateCreated = DateUtils.getCurrentTime(),
        dateModified = DateUtils.getCurrentTime()
    )

    fun createProduct(
        shoppingListId: String,
        updatedPosition: Int
    ) = Product(
        id = UUID.randomUUID().toString(),
        shoppingListId = shoppingListId,
        position = updatedPosition.toLong(),
        name = "",
        price = 0.0
    )
}