package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.IDProvider
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSUUID

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(ShoppingListDatabase.Schema, "shopping_list.db")
        ShoppingListDatabase(driver)
    }

    singleOf(::IDProviderIOS) { bind<IDProvider>() }
    singleOf(::PostExecutionThreadIOS) { bind<PostExecutionThread>() }
}

class IDProviderIOS : IDProvider {
    override fun getId() = NSUUID().UUIDString()
}

class PostExecutionThreadIOS : PostExecutionThread {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.Default
    override val default: CoroutineDispatcher = Dispatchers.Default
}