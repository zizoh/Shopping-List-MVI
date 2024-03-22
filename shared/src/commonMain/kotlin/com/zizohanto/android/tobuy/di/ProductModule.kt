package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.presentation.mvi.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.products.ProductStateMachine
import com.zizohanto.android.tobuy.presentation.mvi.products.ProductStateReducer
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductViewIntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductViewStateMachine
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductViewStateReducer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val productModule = module {
    factory<ProductIntentProcessor>(named("productIntentProcessor")) {
        ProductViewIntentProcessor(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory<ProductStateReducer>(named("productStateReducer")) {
        ProductViewStateReducer(
            get(),
            get(),
            get(),
        )
    }
    factory<ProductStateMachine>(named("productStateMachine")) {
        ProductViewStateMachine(
            get(named("productIntentProcessor")),
            get(named("productStateReducer")),
        )
    }
}