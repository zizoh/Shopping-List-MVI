package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel

data class ProductsViewState(
    val listWithProducts: ShoppingListWithProductsModel? = null
) : ViewState