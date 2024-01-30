package com.zizohanto.android.tobuy.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zizohanto.android.tobuy.cache.ProductCache
import com.zizohanto.android.tobuy.cache.ProductCacheImpl
import com.zizohanto.android.tobuy.cache.ShoppingListCache
import com.zizohanto.android.tobuy.cache.ShoppingListCacheImpl
import com.zizohanto.android.tobuy.sq.ProductQueries
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import com.zizohanto.android.tobuy.sq.ShoppingListQueries
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface CacheModule {

    @get:Binds
    val ShoppingListCacheImpl.shoppingListCache: ShoppingListCache

    @get:Binds
    val ProductCacheImpl.productCache: ProductCache

    companion object {
        @[Provides Singleton]
        fun provideDatabase(@ApplicationContext context: Context): ShoppingListDatabase {
            val driver =
                AndroidSqliteDriver(ShoppingListDatabase.Schema, context, "shopping_list.db")
            return ShoppingListDatabase(driver)
        }

        @[Provides Singleton]
        fun provideProductQueries(database: ShoppingListDatabase): ProductQueries {
            return database.productQueries
        }

        @[Provides Singleton]
        fun provideShoppingListQueries(database: ShoppingListDatabase): ShoppingListQueries {
            return database.shoppingListQueries
        }
    }
}
