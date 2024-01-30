package com.zizohanto.android.tobuy.shopping_list.presentation.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class StateMachine<I : ViewIntent, S : ViewState, out R : ViewResult>(
    private val intentProcessor: IntentProcessor<I, R>,
    private val reducer: ViewStateReducer<S, R>,
    initialIntent: I,
    initialState: S
) {

    private val viewStateFlow: MutableStateFlow<S> =
        MutableStateFlow(initialState)

    private val intentsChannel: MutableSharedFlow<I> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        intentsChannel.tryEmit(initialIntent)
    }

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.tryEmit(viewIntents)
    }

    val viewState: StateFlow<S>
        get() = viewStateFlow.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val processor: Flow<S> = intentsChannel
        .flatMapMerge { action ->
            intentProcessor.intentToResult(action)
        }.scan(initialState) { previous, result ->
            reducer.reduce(previous, result)
        }.distinctUntilChanged()
        .onEach { state ->
            viewStateFlow.tryEmit(state)
        }
}