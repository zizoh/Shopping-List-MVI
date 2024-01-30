package com.zizohanto.android.tobuy.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.presentation.models.ShoppingListWithProductsModel

data class ProductsViewState(
    val listWithProducts: ShoppingListWithProductsModel? = null
) : ViewState