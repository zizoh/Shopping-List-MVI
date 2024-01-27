package com.zizohanto.android.tobuy.domain.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zizohanto.android.tobuy.domain.contract.ProductCache
import com.zizohanto.android.tobuy.domain.contract.ProductCacheImpl
import com.zizohanto.android.tobuy.domain.contract.ShoppingListCache
import com.zizohanto.android.tobuy.domain.contract.ShoppingListCacheImpl
import com.zizohanto.android.tobuy.domain.sq.ProductQueries
import com.zizohanto.android.tobuy.domain.sq.ShoppingListDatabase
import com.zizohanto.android.tobuy.domain.sq.ShoppingListQueries
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