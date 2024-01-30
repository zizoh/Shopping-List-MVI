package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.IntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.StateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewResult
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewState

typealias ProductIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<ProductsViewIntent, ProductsViewResult>

typealias ProductStateReducer =
        @JvmSuppressWildcards ViewStateReducer<ProductsViewState, ProductsViewResult>

typealias ProductStateMachine =
        @JvmSuppressWildcards StateMachine<ProductsViewIntent, ProductsViewState, ProductsViewResult>