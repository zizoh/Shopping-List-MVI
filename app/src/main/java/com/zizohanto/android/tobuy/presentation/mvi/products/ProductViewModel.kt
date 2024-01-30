package com.zizohanto.android.tobuy.presentation.mvi.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productStateMachine: ProductStateMachine,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MVIPresenter<ProductsViewState, ProductsViewIntent> {

    override val viewState: Flow<ProductsViewState>
        get() = productStateMachine.viewState

    init {
        productStateMachine.processor.launchIn(viewModelScope)
        processIntent(
            ProductsViewIntent.ProductViewIntent.LoadShoppingListWithProducts(
                savedStateHandle.get<String>(SHOPPING_LIST_ID_SAVED_STATE_KEY).orEmpty()
            )
        )
    }

    override fun processIntent(intents: Flow<ProductsViewIntent>) {
        productStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
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

    companion object {
        private const val SHOPPING_LIST_ID_SAVED_STATE_KEY = "shoppingListId"
    }
}