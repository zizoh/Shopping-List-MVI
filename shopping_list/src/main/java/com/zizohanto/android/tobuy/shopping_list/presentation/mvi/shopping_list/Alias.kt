package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.IntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.StateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.ViewStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewResult
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi.ShoppingListViewState

typealias ShoppingListIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<ShoppingListViewIntent, ShoppingListViewResult>

typealias ShoppingListStateReducer =
        @JvmSuppressWildcards ViewStateReducer<ShoppingListViewState, ShoppingListViewResult>

typealias ShoppingListStateMachine =
        @JvmSuppressWildcards StateMachine<ShoppingListViewIntent, ShoppingListViewState, ShoppingListViewResult>