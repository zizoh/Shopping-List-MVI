package com.zizohanto.android.tobuy.shopping_list.presentation.products

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

class ProductViewModel @ViewModelInject constructor(
    private val productStateMachine: ProductStateMachine
) : ViewModel(), MVIPresenter<ProductsViewState, ProductsViewIntent> {

    override val viewState: Flow<ProductsViewState>
        get() = productStateMachine.viewState

    init {
        productStateMachine.processor.launchIn(viewModelScope)
    }

    override fun processIntent(intents: Flow<ProductsViewIntent>) {
        productStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}