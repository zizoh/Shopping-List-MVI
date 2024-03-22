package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListStateMachine
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListStateReducer
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewIntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewStateMachine
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewStateReducer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val shoppingListModule = module {
    factory<ShoppingListIntentProcessor>(named("shoppingListIntentProcessor")) {
        ShoppingListViewIntentProcessor(get(), get(), get())
    }
    factory<ShoppingListStateReducer>(named("shoppingListStateReducer")) {
        ShoppingListViewStateReducer(get(), get())
    }
    factory<ShoppingListStateMachine>(named("shoppingListStateMachine")) {
        ShoppingListViewStateMachine(
            get(named("shoppingListIntentProcessor")),
            get(named("shoppingListStateReducer")),
        )
    }
}