package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.presentation.event.ViewEvent
import com.zizohanto.android.tobuy.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel

data class ShoppingListViewState(
    val isLoading: Boolean = false,
    val openProductScreenEvent: ViewEvent<String>? = null,
    val listWithProducts: List<ShoppingListWithProductsModel> = emptyList(),
    val error: String? = null
) : ViewState