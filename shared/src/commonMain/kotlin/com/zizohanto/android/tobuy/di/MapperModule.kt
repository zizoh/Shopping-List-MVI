package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.presentation.mappers.ShoppingListWithProductsModelMapper
import org.koin.dsl.module

val mapperModule = module {
    single { ShoppingListWithProductsModelMapper(get(), get()) }
    single { ShoppingListModelMapper() }
    single { ProductModelMapper() }
}