package com.zizohanto.android.tobuy.presentation.mvi.products

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.presentation.mvi.asValue
import com.zizohanto.android.tobuy.presentation.mvi.coroutineScope
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ProductsComponent(
    componentContext: ComponentContext,
    shoppingListId: String,
    val onBackPressed: () -> Unit
) : MVIPresenter<ProductsViewState, ProductsViewIntent>,
    KoinComponent,
    ComponentContext by componentContext {

    private val productStateMachine: ProductStateMachine by inject(named("productStateMachine"))

    private val coroutineScope = coroutineScope()

    override val viewState: Value<ProductsViewState>
        get() = productStateMachine.viewState.asValue(
            initialValue = ProductsViewState(),
            lifecycle = lifecycle
        )

    init {
        productStateMachine.processor.launchIn(coroutineScope)
        processIntent(
            ProductsViewIntent.ProductViewIntent.LoadShoppingListWithProducts(shoppingListId)
        )
    }

    override fun processIntent(intents: Flow<ProductsViewIntent>) {
        productStateMachine
            .processIntents(intents)
            .launchIn(coroutineScope)
    }

    fun addNewProduct(shoppingListId: String, newProductPosition: Int) {
        processIntent(
            ProductsViewIntent.ProductViewIntent.AddNewProductAtPosition(
                shoppingListId,
                newProductPosition
            )
        )
    }

    fun updateProduct(product: ProductsViewItem.ProductModel) {
        processIntent(ProductsViewIntent.ProductViewIntent.SaveProduct(product))
    }

    fun deleteProduct(product: ProductsViewItem.ProductModel) {
        processIntent(ProductsViewIntent.ProductViewIntent.DeleteProduct(product))
    }

    fun updateShoppingList(shoppingListModel: ProductsViewItem.ShoppingListModel) {
        processIntent(ProductsViewIntent.ProductViewIntent.SaveShoppingList(shoppingListModel))
    }

    private fun processIntent(intent: ProductsViewIntent) {
        processIntent(flowOf(intent))
    }
}