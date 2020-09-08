package com.zizohanto.android.tobuy.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface PostExecutionThread {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}