package com.zizohanto.android.tobuy.core.di

import com.zizohanto.android.tobuy.core.executor.PostExecutionThreadImpl
import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ExecutorModule {

    @get:[Binds Singleton]
    val PostExecutionThreadImpl.postExecutionThread: PostExecutionThread
}