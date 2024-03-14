package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(ShoppingListDatabase.Schema, "shopping_list.db")
        ShoppingListDatabase(driver)
    }
}