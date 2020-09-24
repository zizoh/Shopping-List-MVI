package com.zizohanto.android.tobuy.shopping_list.presentation.models

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

data class ShoppingListWithProductsModel(
    val shoppingList: ShoppingListModel,
    val products: List<ProductModel>
) {
    companion object {
        fun getShoppingListWithProductsModel(): ShoppingListWithProductsModel {
            val shoppingListId: String = UUID.randomUUID().toString()
            val shoppingListModel: ShoppingListModel = getNewShoppingListModel(shoppingListId)
            val products: List<ProductModel> = listOf(getNewProductModel(shoppingListId))
            return ShoppingListWithProductsModel(shoppingListModel, products)
        }

        private fun getNewShoppingListModel(shoppingListId: String): ShoppingListModel {
            val formattedDate: String = getCurrentTime()
            return ShoppingListModel(shoppingListId, "", 0.0, formattedDate, formattedDate)
        }

        private fun getNewProductModel(shoppingListId: String): ProductModel {
            val productId: String = UUID.randomUUID().toString()
            return ProductModel(productId, shoppingListId, "", 0.0)
        }

        private fun getCurrentTime(): String {
            val fmt: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date: LocalDate = LocalDate.now()
            return date.toString(fmt)
        }
    }
}