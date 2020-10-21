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
            ShoppingListViewResult.Idle -> ShoppingListViewState.Idle
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
            is ShoppingListViewResult.ShoppingListDeleted -> {
                when (previous) {
                    ShoppingListViewState.Idle -> ShoppingListViewState.Idle
                    ShoppingListViewState.Loading -> ShoppingListViewState.Idle
                    is ShoppingListViewState.NewShoppingListLoaded -> ShoppingListViewState.Idle
                    is ShoppingListViewState.ShoppingListLoaded -> {
                        val shoppingList: List<ShoppingListModel> = previous.shoppingLists
                        if (shoppingList.size == 1) {
                            ShoppingListViewState.ShoppingListEmpty
                        } else {
                            val shoppingLists: List<ShoppingListModel> =
                                removeShoppingListFromList(shoppingList, result.shoppingListId)
                            ShoppingListViewState.ShoppingListLoaded(shoppingLists)
                        }
                    }
                    ShoppingListViewState.ShoppingListEmpty -> ShoppingListViewState.Idle
                    is ShoppingListViewState.Error -> ShoppingListViewState.Idle
                }
            }
            ShoppingListViewResult.Empty -> ShoppingListViewState.ShoppingListEmpty
            is ShoppingListViewResult.Error -> ShoppingListViewState.Error(result.throwable.errorMessage)
        }
    }

    private fun removeShoppingListFromList(
        shoppingList: List<ShoppingListModel>,
        shoppingListId: String
    ): List<ShoppingListModel> {
        val map: HashMap<String, ShoppingListModel> = hashMapOf()
        shoppingList.forEach {
            map[it.id] = it
        }
        map.remove(shoppingListId)
        return map.values.toList()
    }
}