package com.zizohanto.android.tobuy.shopping_list.presentation.models

import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel

data class ShoppingListWithProductsModel(
    val shoppingList: ShoppingListModel,
    val products: List<ProductModel>
)