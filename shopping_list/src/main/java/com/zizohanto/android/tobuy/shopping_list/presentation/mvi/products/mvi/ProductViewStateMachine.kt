package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductStateReducer
import javax.inject.Inject

class ProductViewStateMachine @Inject constructor(
    intentProcessor: ProductIntentProcessor,
    reducer: ProductStateReducer
) : ProductStateMachine(
    intentProcessor,
    reducer,
    ProductsViewIntent.Idle,
    ProductsViewState()
)