package com.zizohanto.android.tobuy.core.di

import com.zizohanto.android.tobuy.data.repository.ProductRepositoryImpl
import com.zizohanto.android.tobuy.data.repository.ShoppingListRepositoryImpl
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
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