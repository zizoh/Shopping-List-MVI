package com.zizohanto.android.tobuy.presentation.mvi.products

import com.zizohanto.android.tobuy.presentation.mvi.IntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.StateMachine
import com.zizohanto.android.tobuy.presentation.mvi.ViewStateReducer
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewResult
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewState

typealias ProductIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<ProductsViewIntent, ProductsViewResult>

typealias ProductStateReducer =
        @JvmSuppressWildcards ViewStateReducer<ProductsViewState, ProductsViewResult>

typealias ProductStateMachine =
        @JvmSuppressWildcards StateMachine<ProductsViewIntent, ProductsViewState, ProductsViewResult>