package com.zizohanto.android.tobuy.di

import com.zizohanto.android.tobuy.presentation.mvi.products.ProductViewModel
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel {
        ProductViewModel(
            get(named("productStateMachine")),
            get()
        )
    }
    viewModel {
        ShoppingListViewModel(
            get(named("shoppingListStateMachine"))
        )
    }
}