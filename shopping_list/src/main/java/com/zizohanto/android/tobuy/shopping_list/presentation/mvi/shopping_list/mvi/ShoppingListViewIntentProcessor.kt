package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.shopping_list.ShoppingListIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.usecase.CreateShoppingList
import com.zizohanto.android.tobuy.shopping_list.usecase.DeleteShoppingList
import com.zizohanto.android.tobuy.shopping_list.usecase.GetShoppingLists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListViewIntentProcessor @Inject constructor(
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