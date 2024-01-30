package com.zizohanto.android.tobuy.models

import com.zizohanto.android.tobuy.sq.Product
import com.zizohanto.android.tobuy.sq.ShoppingList

data class ShoppingListWithProducts(
    val shoppingList: ShoppingList,
    val products: List<Product>
)