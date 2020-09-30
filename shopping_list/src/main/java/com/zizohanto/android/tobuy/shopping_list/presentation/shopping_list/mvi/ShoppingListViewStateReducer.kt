package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.core.ext.errorMessage
import com.zizohanto.android.tobuy.presentation.event.ViewEvent
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListStateReducer
import javax.inject.Inject

class ShoppingListViewStateReducer @Inject constructor(
    private val listMapper: ShoppingListModelMapper
) : ShoppingListStateReducer {

    override fun reduce(
        previous: ShoppingListViewState,
        result: ShoppingListViewResult
    ): ShoppingListViewState {
        return when (result) {
            ShoppingListViewResult.Loading -> ShoppingListViewState.Loading
            is ShoppingListViewResult.Success -> {
                val shoppingLists: List<ShoppingListModel> =
                    listMapper.mapToModelList(result.shoppingLists)
                ShoppingListViewState.ShoppingListLoaded(shoppingLists)
            }
            is ShoppingListViewResult.NewShoppingListCreated -> {
                val shoppingList: ShoppingListModel = listMapper.mapToModel(result.shoppingList)
                ShoppingListViewState.NewShoppingListLoaded(ViewEvent(shoppingList))
            }
            ShoppingListViewResult.Empty -> ShoppingListViewState.ShoppingListEmpty
            is ShoppingListViewResult.Error -> ShoppingListViewState.Error(result.throwable.errorMessage)
        }
    }
}