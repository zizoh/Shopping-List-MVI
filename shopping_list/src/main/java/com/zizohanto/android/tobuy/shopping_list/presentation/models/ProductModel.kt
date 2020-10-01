package com.zizohanto.android.tobuy.shopping_list.presentation.models

import com.zizohanto.android.tobuy.shopping_list.ui.products.DEBOUNCE_PERIOD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce

data class ProductModel(
    val id: String,
    val shoppingListId: String,
    val name: String,
    val price: Double
)

val Flow<ProductModel>.debounce: Flow<ProductModel>
    get() = this.debounce(DEBOUNCE_PERIOD)
        .conflate()
