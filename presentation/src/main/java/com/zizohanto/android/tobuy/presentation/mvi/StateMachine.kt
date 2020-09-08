package com.zizohanto.android.tobuy.presentation.mvi

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

abstract class StateMachine<I : ViewIntent, S : ViewState, out R : ViewResult>(
    private val intentProcessor: IntentProcessor<I, R>,
    private val reducer: ViewStateReducer<S, R>,
    initialIntent: I,
    initialState: S
) {

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val intentsChannel: ConflatedBroadcastChannel<I> =
        ConflatedBroadcastChannel(initialIntent)

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.offer(viewIntents)
    }

    val viewState: StateFlow<S>
        get() = viewStateFlow

    val processor: Flow<S> = intentsChannel.asFlow()
        .flatMapMerge { action ->
            intentProcessor.intentToResult(action)
        }.scan(initialState) { previous, result ->
            reducer.reduce(previous, result)
        }.onEach { recipeViewState ->
            viewStateFlow.value = recipeViewState
        }
}