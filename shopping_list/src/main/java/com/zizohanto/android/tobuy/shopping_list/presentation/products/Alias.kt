package com.zizohanto.android.tobuy.shopping_list.presentation.products

import com.zizohanto.android.tobuy.presentation.mvi.IntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.StateMachine
import com.zizohanto.android.tobuy.presentation.mvi.ViewStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState

typealias ProductIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<ProductsViewIntent, ProductsViewResult>

typealias ProductStateReducer =
        @JvmSuppressWildcards ViewStateReducer<ProductsViewState, ProductsViewResult>

typealias ProductStateMachine =
        @JvmSuppressWildcards StateMachine<ProductsViewIntent, ProductsViewState, ProductsViewResult>