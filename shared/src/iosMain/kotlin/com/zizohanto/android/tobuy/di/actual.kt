package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zizohanto.android.tobuy.repository.IDProvider
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
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
}

class IDProviderIOS : IDProvider {
    override fun getId() = NSUUID().UUIDString()
}