package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.repository.ProductRepository
import com.zizohanto.android.tobuy.repository.ProductRepositoryImpl
import com.zizohanto.android.tobuy.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.repository.ShoppingListRepositoryImpl
import com.zizohanto.android.tobuy.usecase.CreateProduct
import com.zizohanto.android.tobuy.usecase.CreateShoppingList
import com.zizohanto.android.tobuy.usecase.DeleteProduct
import com.zizohanto.android.tobuy.usecase.DeleteShoppingList
import com.zizohanto.android.tobuy.usecase.GetShoppingListWithProducts
import com.zizohanto.android.tobuy.usecase.GetShoppingLists
import com.zizohanto.android.tobuy.usecase.SaveProduct
import com.zizohanto.android.tobuy.usecase.SaveShoppingList
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ShoppingListRepositoryImpl) { bind<ShoppingListRepository>() }
    singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() }
    singleOf(::GetShoppingListWithProducts)
    singleOf(::SaveProduct)
    singleOf(::ProductModelMapper)
    singleOf(::SaveShoppingList)
    singleOf(::ShoppingListModelMapper)
    singleOf(::CreateProduct)
    singleOf(::DeleteProduct)
    singleOf(::GetShoppingLists)
    singleOf(::CreateShoppingList)
    singleOf(::DeleteShoppingList)
}