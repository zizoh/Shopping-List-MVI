package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.ShoppingListRepository
import kotlinx.coroutines.withContext

class DeleteShoppingList(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(shoppingListId: String) {
        withContext(postExecutionThread.io) {
            repository.deleteShoppingList(shoppingListId)
        }
    }
}