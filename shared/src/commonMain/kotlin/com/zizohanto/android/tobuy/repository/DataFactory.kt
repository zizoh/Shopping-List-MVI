package com.zizohanto.android.tobuy.repository

import com.zizohanto.android.tobuy.sq.Product
import com.zizohanto.android.tobuy.sq.ShoppingList
import com.zizohanto.android.tobuy.utils.DateUtils

object DataFactory {
    fun createShoppingList(
        shoppingListId: String
    ) = ShoppingList(
        id = shoppingListId,
        name = "",
        budget = 0.0,
        dateCreated = DateUtils.getCurrentTime(),
        dateModified = DateUtils.getCurrentTime()
    )

    fun createProduct(
        productId: String,
        shoppingListId: String,
        updatedPosition: Int
    ) = Product(
        id = productId,
        shoppingListId = shoppingListId,
        position = updatedPosition.toLong(),
        name = "",
        price = 0.0
    )
}