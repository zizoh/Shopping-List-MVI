package com.zizohanto.android.tobuy.presentation.mvi

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow

interface MVIPresenter<out S : ViewState, in I : ViewIntent> {
    val viewState: Value<S>
    fun processIntent(intents: Flow<I>)
}