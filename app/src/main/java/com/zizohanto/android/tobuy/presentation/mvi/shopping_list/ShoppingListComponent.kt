package com.zizohanto.android.tobuy.presentation.mvi.shopping_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.zizohanto.android.tobuy.presentation.mvi.MVIPresenter
import com.zizohanto.android.tobuy.presentation.mvi.asValue
import com.zizohanto.android.tobuy.presentation.mvi.coroutineScope
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ShoppingListComponent(
    componentContext: ComponentContext,
    val onShoppingListClicked: (String) -> Unit
) : MVIPresenter<ShoppingListViewState, ShoppingListViewIntent>,
    KoinComponent,
    ComponentContext by componentContext {

    private val shoppingListStateMachine: ShoppingListStateMachine by inject(
        named("shoppingListStateMachine")
    )

    private val coroutineScope = coroutineScope()

    override val viewState: Value<ShoppingListViewState>
        get() = shoppingListStateMachine.viewState.asValue(
            initialValue = ShoppingListViewState(),
            lifecycle = lifecycle
        )

    init {
        shoppingListStateMachine.processor.launchIn(coroutineScope)
        loadShoppingLists()
    }

    override fun processIntent(intents: Flow<ShoppingListViewIntent>) {
        shoppingListStateMachine
            .processIntents(intents)
            .launchIn(coroutineScope)
    }

    fun onListDeleted(shoppingListId: String) {
        processIntent(ShoppingListViewIntent.DeleteShoppingList(shoppingListId))
    }

    fun onCreateShoppingList() {
        processIntent(ShoppingListViewIntent.CreateNewShoppingList)
    }

    fun loadShoppingLists() {
        processIntent(ShoppingListViewIntent.LoadShoppingLists)
    }

    private fun processIntent(intent: ShoppingListViewIntent) {
        processIntent(flowOf(intent))
    }
}