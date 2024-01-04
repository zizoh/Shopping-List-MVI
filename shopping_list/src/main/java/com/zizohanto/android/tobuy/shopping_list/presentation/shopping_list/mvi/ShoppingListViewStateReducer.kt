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
            ShoppingListViewResult.Idle -> ShoppingListViewState()
            ShoppingListViewResult.Loading -> ShoppingListViewState(isLoading = true)
            is ShoppingListViewResult.Success -> {
                val listWithProducts =
                    listWithProductsMapper.mapToModelList(result.listWithProducts)
                ShoppingListViewState(
                    listWithProducts = listWithProducts
                )
            }
            is ShoppingListViewResult.NewShoppingListCreated -> {
                val shoppingList = listMapper.mapToModel(result.shoppingList)
                ShoppingListViewState(openProductScreenEvent = ViewEvent(shoppingList.id))
            }
            is ShoppingListViewResult.ShoppingListDeleted -> {
                val shoppingList = previous.listWithProducts
                if (shoppingList.size == 1) {
                    emptyList()
                } else {
                    shoppingList.removeFirst { it.shoppingList.id == result.shoppingListId }
                }
                val listWithProducts = if (shoppingList.size == 1) {
                    emptyList()
                } else {
                    shoppingList.removeFirst { it.shoppingList.id == result.shoppingListId }
                }
                ShoppingListViewState(listWithProducts = listWithProducts)
            }
            ShoppingListViewResult.Empty -> ShoppingListViewState()
            is ShoppingListViewResult.Error -> ShoppingListViewState(
                error = result.throwable.errorMessage
            )
        }
    }
}