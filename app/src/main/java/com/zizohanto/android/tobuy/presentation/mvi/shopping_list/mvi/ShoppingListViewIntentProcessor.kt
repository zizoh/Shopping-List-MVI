package com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.usecase.CreateShoppingList
import com.zizohanto.android.tobuy.usecase.DeleteShoppingList
import com.zizohanto.android.tobuy.usecase.GetShoppingLists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ShoppingListViewIntentProcessor(
    private val getShoppingLists: GetShoppingLists,
    private val createShoppingList: CreateShoppingList,
    private val deleteShoppingList: DeleteShoppingList
) : ShoppingListIntentProcessor {

    override fun intentToResult(viewIntent: ShoppingListViewIntent): Flow<ShoppingListViewResult> {
        return when (viewIntent) {
            ShoppingListViewIntent.Idle -> flowOf(ShoppingListViewResult.Idle)
            ShoppingListViewIntent.LoadShoppingLists -> loadShoppingLists()
            ShoppingListViewIntent.CreateNewShoppingList -> loadNewShoppingList()
            is ShoppingListViewIntent.DeleteShoppingList -> flow {
                val shoppingListId = viewIntent.shoppingListId
                deleteShoppingList(shoppingListId)
                emit(ShoppingListViewResult.ShoppingListDeleted(shoppingListId))
            }
        }
    }

    private fun loadShoppingLists(): Flow<ShoppingListViewResult> {
        return getShoppingLists()
            .map { shoppingList ->
                if (shoppingList.isEmpty()) {
                    ShoppingListViewResult.Empty
                } else {
                    ShoppingListViewResult.Success(shoppingList)
                }
            }.catch { error ->
                error.printStackTrace()
                emit(ShoppingListViewResult.Error(error))
            }
    }

    private fun loadNewShoppingList(): Flow<ShoppingListViewResult> {
        return createShoppingList()
            .map(ShoppingListViewResult::NewShoppingListCreated)
            .catch { error ->
                error.printStackTrace()
            }
    }
}