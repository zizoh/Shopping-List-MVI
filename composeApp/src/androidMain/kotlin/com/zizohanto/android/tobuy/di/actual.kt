package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(
            ShoppingListDatabase.Schema,
            get(),
            "shopping_list.db"
        )
        ShoppingListDatabase(driver)
    }
}
