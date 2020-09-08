package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.domain.usecase.GetShoppingLists
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListIntentProcessor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListViewIntentProcessor @Inject constructor(
    private val getShoppingLists: GetShoppingLists
) : ShoppingListIntentProcessor {

    override fun intentToResult(viewIntent: ShoppingListViewIntent): Flow<ShoppingListViewResult> {
        return when (viewIntent) {
            ShoppingListViewIntent.LoadShoppingLists -> loadShoppingLists()
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
            }
    }
}