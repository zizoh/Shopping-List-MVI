package com.zizohanto.android.tobuy.core.models

import com.zizohanto.android.tobuy.domain.sq.Product
import com.zizohanto.android.tobuy.domain.sq.ShoppingList

data class ShoppingListWithProducts(
    val shoppingList: ShoppingList,
    val products: List<Product>
)