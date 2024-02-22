package com.zizohanto.android.tobuy.di

import org.koin.core.KoinApplication

fun KoinApplication.commonModule() {
    modules(
        cacheModule,
        dataModule,
        executorModule,
        mapperModule,
        platformModule(),
        productModule,
        shoppingListModule
    )
}