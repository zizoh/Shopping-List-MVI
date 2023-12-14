package com.zizohanto.android.tobuy.shopping_list.utilities

import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import kotlin.random.Random

object DataFactory {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getProductModel() = ProductsViewItem.ProductModel(
        id = getRandomString(),
        shoppingListId = getRandomString(),
        name = getRandomString(),
        price = getRandomDouble(),
        position = getRandomInt(),
    )

    fun getShoppingListModel() = ProductsViewItem.ShoppingListModel(
        id = getRandomString(),
        name = getRandomString(),
        budget = getRandomDouble(),
        dateCreated = getRandomLong(),
        dateModified = getRandomLong()
    )

    fun getRandomString(length: Int = 5) = (1..length)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    private fun getRandomInt() = Random.nextInt()

    private fun getRandomDouble(): Double = Random.nextDouble()

    private fun getRandomLong() = Random.nextLong()
}