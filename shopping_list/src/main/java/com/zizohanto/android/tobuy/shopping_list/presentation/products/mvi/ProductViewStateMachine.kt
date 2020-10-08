package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import javax.inject.Inject

class ProductViewStateMachine @Inject constructor(
    intentProcessor: ProductIntentProcessor,
    reducer: ProductStateReducer
) : ProductStateMachine(
    intentProcessor,
    reducer,
    ProductsViewIntent.Idle,
    ProductsViewState.Idle
)