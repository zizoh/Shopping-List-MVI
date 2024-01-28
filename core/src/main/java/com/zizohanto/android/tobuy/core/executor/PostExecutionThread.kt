package com.zizohanto.android.tobuy.core.executor

import kotlinx.coroutines.CoroutineDispatcher

interface PostExecutionThread {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}