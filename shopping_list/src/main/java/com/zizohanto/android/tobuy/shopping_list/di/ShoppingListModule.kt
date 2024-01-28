package com.zizohanto.android.tobuy.shopping_list.di

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.ShoppingListStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.ShoppingListStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@InstallIn(ActivityRetainedComponent::class)
@Module
interface ShoppingListModule {

    @get:Binds
    val ShoppingListViewIntentProcessor.intentProcessor: ShoppingListIntentProcessor

    @get:Binds
    val ShoppingListViewStateReducer.reducer: ShoppingListStateReducer

    @get:Binds
    val ShoppingListViewStateMachine.stateMachine: ShoppingListStateMachine
}