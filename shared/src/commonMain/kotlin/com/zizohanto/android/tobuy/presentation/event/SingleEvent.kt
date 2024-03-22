package com.zizohanto.android.tobuy.presentation.event

import kotlinx.atomicfu.atomic

abstract class SingleEvent<T>(private val content: T) {
    private val isConsumed = atomic(false)
    fun consume(action: (T) -> Unit) {
        if (!isConsumed.getAndSet(true)) {
            action.invoke(content)
        }
    }

    fun reset() {
        isConsumed.lazySet(false)
    }

    val peekContent: T
        get() = content
}

data class ViewEvent<T>(val value: T) : SingleEvent<T>(value)