package com.zizohanto.android.tobuy.utilities

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

    fun getShoppingLists(products: List<ProductsViewItem.ProductModel> = listOf(getProductModel())): ShoppingListWithProductsModel {
        return ShoppingListWithProductsModel(
            getShoppingListModel(),
            products
        )
    }

    fun getShoppingLists(numberOfProducts: Int): ShoppingListWithProductsModel {
        val products = (1..numberOfProducts).map {
            getProductModel()
        }
        return ShoppingListWithProductsModel(getShoppingListModel(), products)
    }

    fun getShoppingListModel() = ProductsViewItem.ShoppingListModel(
        id = getRandomString(),
        name = getRandomString(),
        budget = getRandomDouble(),
        dateCreated = getRandomLong(),
        dateModified = getRandomLong()
    )

    fun getResultProductAddedAtPosition() =
        ProductsViewResult.ProductViewResult.ProductAddedAtPosition(getProduct())

    fun getRandomString(length: Int = 5) = (1..length)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    private fun getRandomInt() = Random.nextInt()

    private fun getRandomDouble(): Double = Random.nextDouble()

    private fun getRandomLong() = Random.nextLong()

    private fun getProduct() = Product(
        id = getRandomString(),
        shoppingListId = getRandomString(),
        name = getRandomString(),
        price = getRandomDouble(),
        position = getRandomInt(),
    )
}