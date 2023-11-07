package com.zizohanto.android.tobuy.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.CancellationException

inline fun <reified R> Flow<R>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline action: (R) -> Unit
) {
    this.onEach {
        action(it)
    }.launchIn(lifecycleOwner.lifecycleScope)
}

fun <E> SendChannel<E>.safeOffer(value: E): Boolean = !isClosedForSend && try {
    trySend(value).isSuccess
} catch (e: CancellationException) {
    false
}