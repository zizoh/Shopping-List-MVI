package com.zizohanto.android.tobuy.core.usecase

import com.zizohanto.android.tobuy.core.executor.PostExecutionThread
import com.zizohanto.android.tobuy.core.repository.ShoppingListRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteShoppingList @Inject constructor(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(shoppingListId: String) {
        withContext(postExecutionThread.io) {
            repository.deleteShoppingList(shoppingListId)
        }
    }
}