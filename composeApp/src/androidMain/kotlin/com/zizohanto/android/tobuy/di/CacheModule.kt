package com.zizohanto.android.tobuy.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zizohanto.android.tobuy.cache.ProductCache
import com.zizohanto.android.tobuy.cache.ProductCacheImpl
import com.zizohanto.android.tobuy.cache.ShoppingListCache
import com.zizohanto.android.tobuy.cache.ShoppingListCacheImpl
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cacheModule = module {
    singleOf(::ShoppingListCacheImpl) { bind<ShoppingListCache>() }
    singleOf(::ProductCacheImpl) { bind<ProductCache>() }
    single {
        val driver = AndroidSqliteDriver(
            ShoppingListDatabase.Schema,
            get(),
            "shopping_list.db"
        )
        ShoppingListDatabase(driver)
    }
    single { get<ShoppingListDatabase>().productQueries }
    single { get<ShoppingListDatabase>().shoppingListQueries }
}
