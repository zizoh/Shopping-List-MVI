package com.zizohanto.android.tobuy.repository

import com.zizohanto.android.tobuy.sq.Product
import com.zizohanto.android.tobuy.sq.ShoppingList
import com.zizohanto.android.tobuy.utils.DateUtils
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