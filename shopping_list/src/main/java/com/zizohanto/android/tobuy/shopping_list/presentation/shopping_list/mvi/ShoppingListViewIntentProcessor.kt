package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.domain.usecase.CreateShoppingList
import com.zizohanto.android.tobuy.domain.usecase.DeleteShoppingList
import com.zizohanto.android.tobuy.domain.usecase.GetShoppingLists
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListIntentProcessor
import kotlinx.coroutines.flow.*
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