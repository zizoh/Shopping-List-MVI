package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.cache.ProductCache
import com.zizohanto.android.tobuy.cache.ProductCacheImpl
import com.zizohanto.android.tobuy.cache.ShoppingListCache
import com.zizohanto.android.tobuy.cache.ShoppingListCacheImpl
import com.zizohanto.android.tobuy.sq.ShoppingListDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cacheModule = module {
    singleOf(::ShoppingListCacheImpl) { bind<ShoppingListCache>() }
    singleOf(::ProductCacheImpl) { bind<ProductCache>() }
    single { get<ShoppingListDatabase>().productQueries }
    single { get<ShoppingListDatabase>().shoppingListQueries }
}

expect fun platformModule(): Module