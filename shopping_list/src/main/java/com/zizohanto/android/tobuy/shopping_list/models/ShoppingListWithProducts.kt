package com.zizohanto.android.tobuy.shopping_list.models

import com.zizohanto.android.tobuy.shoppinglist.sq.Product
import com.zizohanto.android.tobuy.shoppinglist.sq.ShoppingList

data class ShoppingListWithProducts(
    val shoppingList: ShoppingList,
    val products: List<Product>
)