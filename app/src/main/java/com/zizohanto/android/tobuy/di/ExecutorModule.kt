package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.executor.PostExecutionThreadImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val executorModule = module {
    singleOf(::PostExecutionThreadImpl) {
        bind<PostExecutionThread>()
    }
}