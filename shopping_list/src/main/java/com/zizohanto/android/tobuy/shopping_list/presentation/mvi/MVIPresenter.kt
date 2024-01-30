package com.zizohanto.android.tobuy.shopping_list.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface MVIPresenter<out S : ViewState, in I : ViewIntent> {
    val viewState: Flow<S>
    fun processIntent(intents: Flow<I>)
}