package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.shoppinglist.sq.ShoppingList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveShoppingList @Inject constructor(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(shoppingList: ShoppingList) {
        withContext(postExecutionThread.io) {
            repository.saveShoppingList(shoppingList)
        }
    }
}