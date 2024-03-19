package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.IDProvider
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.UUID

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(
            ShoppingListDatabase.Schema,
            get(),
            "shopping_list.db"
        )
        ShoppingListDatabase(driver)
    }

    singleOf(::IDProviderAndroid) { bind<IDProvider>() }

    singleOf(::PostExecutionThreadAndroid) {
        bind<PostExecutionThread>()
    }
}

class IDProviderAndroid : IDProvider {
    override fun getId() = UUID.randomUUID().toString()
}

class PostExecutionThreadAndroid : PostExecutionThread {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
}
