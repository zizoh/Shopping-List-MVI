package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.sq.ShoppingList
import com.zizohanto.android.tobuy.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class CreateShoppingList(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, ShoppingList>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Unit?): Flow<ShoppingList> {
        return repository.createShoppingList()
    }
}