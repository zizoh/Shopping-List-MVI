package com.zizohanto.android.tobuy.domain.usecase

import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import com.zizohanto.android.tobuy.domain.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.domain.sq.ShoppingList
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