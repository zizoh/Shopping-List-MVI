package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.core.ext.errorMessage
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListStateReducer
import javax.inject.Inject

class ShoppingListViewStateReducer @Inject constructor(
    private val shoppingListModelMapper: ShoppingListModelMapper
) : ShoppingListStateReducer {

    override fun reduce(
        previous: ShoppingListViewState,
        result: ShoppingListViewResult
    ): ShoppingListViewState {
        return when (result) {
            ShoppingListViewResult.Loading -> ShoppingListViewState.Loading
            is ShoppingListViewResult.Success -> ShoppingListViewState.ShoppingListLoaded(
                shoppingListModelMapper.mapToModelList(result.shoppingLists)
            )
            ShoppingListViewResult.Empty -> ShoppingListViewState.ShoppingListEmpty
            is ShoppingListViewResult.Error -> ShoppingListViewState.Error(result.throwable.errorMessage)
        }
    }
}