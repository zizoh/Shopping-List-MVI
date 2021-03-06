package com.zizohanto.android.tobuy.shopping_list.di

import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductViewIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductViewStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
interface ProductModule {

    @get:Binds
    val ProductViewIntentProcessor.intentProcessor: ProductIntentProcessor

    @get:Binds
    val ProductViewStateReducer.reducer: ProductStateReducer

    @get:Binds
    val ProductViewStateMachine.stateMachine: ProductStateMachine
}