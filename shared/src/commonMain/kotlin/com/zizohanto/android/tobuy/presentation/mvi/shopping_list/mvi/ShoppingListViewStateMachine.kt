package com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListStateMachine
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListStateReducer

class ShoppingListViewStateMachine(
    intentProcessor: ShoppingListIntentProcessor,
    reducer: ShoppingListStateReducer
) : ShoppingListStateMachine(
    intentProcessor,
    reducer,
    ShoppingListViewIntent.Idle,
    ShoppingListViewState()
)