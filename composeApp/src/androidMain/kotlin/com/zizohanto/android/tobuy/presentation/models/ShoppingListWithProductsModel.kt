package com.zizohanto.android.tobuy.presentation.models

import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem.ShoppingListModel

data class ShoppingListWithProductsModel(
    val shoppingList: ShoppingListModel,
    val products: List<ProductModel>
)