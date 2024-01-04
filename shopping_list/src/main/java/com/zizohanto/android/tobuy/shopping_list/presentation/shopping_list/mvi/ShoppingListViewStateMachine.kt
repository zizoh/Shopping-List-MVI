package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListStateReducer
import javax.inject.Inject

class ShoppingListViewStateMachine @Inject constructor(
    intentProcessor: ShoppingListIntentProcessor,
    reducer: ShoppingListStateReducer
) : ShoppingListStateMachine(
    intentProcessor,
    reducer,
    ShoppingListViewIntent.Idle,
    ShoppingListViewState()
)