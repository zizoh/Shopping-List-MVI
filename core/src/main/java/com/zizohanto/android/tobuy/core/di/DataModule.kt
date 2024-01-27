package com.zizohanto.android.tobuy.core.di

import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import com.zizohanto.android.tobuy.domain.repository.ProductRepositoryImpl
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @get:Binds
    val ShoppingListRepositoryImpl.shoppingListRepository: ShoppingListRepository

    @get:Binds
    val ProductRepositoryImpl.productRepository: ProductRepository

}