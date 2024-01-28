package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.event.ViewEvent
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel

data class ShoppingListViewState(
    val isLoading: Boolean = false,
    val openProductScreenEvent: ViewEvent<String>? = null,
    val listWithProducts: List<ShoppingListWithProductsModel> = emptyList(),
    val error: String? = null
) : ViewState