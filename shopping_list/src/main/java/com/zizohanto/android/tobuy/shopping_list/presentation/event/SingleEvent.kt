package com.zizohanto.android.tobuy.shopping_list.presentation.event

import java.util.concurrent.atomic.AtomicBoolean

abstract class SingleEvent<T>(private val content: T) {
    private val isConsumed = AtomicBoolean(false)
    fun consume(action: (T) -> Unit) {
        if (!isConsumed.getAndSet(true)) {
            action.invoke(content)
        }
    }

    fun reset() {
        isConsumed.set(false)
    }

    val peekContent: T
        get() = content
}

data class ViewEvent<T>(val value: T) : SingleEvent<T>(value)