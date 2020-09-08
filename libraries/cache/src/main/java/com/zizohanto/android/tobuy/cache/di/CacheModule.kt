package com.zizohanto.android.tobuy.cache.di

import android.content.Context
import com.zizohanto.android.tobuy.cache.impl.ShoppingListCacheImpl
import com.zizohanto.android.tobuy.cache.room.ProductDao
import com.zizohanto.android.tobuy.cache.room.ShoppingListDao
import com.zizohanto.android.tobuy.cache.room.ShoppingListDatabase
import com.zizohanto.android.tobuy.data.contract.ShoppingListCache
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

    companion object {
        @[Provides Singleton]
        fun provideDatabase(@ApplicationContext context: Context): ShoppingListDatabase {
            return ShoppingListDatabase.build(context)
        }

        @[Provides Singleton]
        fun provideProductDao(database: ShoppingListDatabase): ProductDao {
            return database.productDao
        }

        @[Provides Singleton]
        fun provideShoppingListDao(database: ShoppingListDatabase): ShoppingListDao {
            return database.shoppingListDao
        }
    }
}
