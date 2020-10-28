package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi

import com.zizohanto.android.tobuy.core.ext.errorMessage
import com.zizohanto.android.tobuy.core.ext.removeFirst
import com.zizohanto.android.tobuy.presentation.event.ViewEvent
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.ShoppingListStateReducer
import javax.inject.Inject

class ShoppingListViewStateReducer @Inject constructor(
    private val listMapper: ShoppingListModelMapper,
    private val listWithProductsMapper: ShoppingListWithProductsModelMapper
) : ShoppingListStateReducer {

    override fun reduce(
        previous: ShoppingListViewState,
        result: ShoppingListViewResult
    ): ShoppingListViewState {
        return when (result) {
            ShoppingListViewResult.Idle -> ShoppingListViewState.Idle
            ShoppingListViewResult.Loading -> ShoppingListViewState.Loading
            is ShoppingListViewResult.Success -> {
                val shoppingLists: List<ShoppingListWithProductsModel> =
                    listWithProductsMapper.mapToModelList(result.listWithProducts)
                ShoppingListViewState.ShoppingListLoaded(shoppingLists)
            }
            is ShoppingListViewResult.NewShoppingListCreated -> {
                val shoppingList: ShoppingListModel = listMapper.mapToModel(result.shoppingList)
                ShoppingListViewState.NewShoppingListLoaded(ViewEvent(shoppingList.id))
            }
            is ShoppingListViewResult.ShoppingListDeleted -> {
                when (previous) {
                    ShoppingListViewState.Idle -> ShoppingListViewState.Idle
                    ShoppingListViewState.Loading -> ShoppingListViewState.Idle
                    is ShoppingListViewState.NewShoppingListLoaded -> ShoppingListViewState.Idle
                    is ShoppingListViewState.ShoppingListLoaded -> {
                        val shoppingList: List<ShoppingListWithProductsModel> =
                            previous.listWithProducts
                        if (shoppingList.size == 1) {
                            ShoppingListViewState.ShoppingListEmpty
                        } else {
                            val shoppingLists: List<ShoppingListWithProductsModel> =
                                shoppingList.removeFirst { it.shoppingList.id == result.shoppingListId }
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
}