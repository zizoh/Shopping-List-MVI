package com.zizohanto.android.tobuy.core.usecase

import com.zizohanto.android.tobuy.core.executor.PostExecutionThread
import com.zizohanto.android.tobuy.core.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.domain.sq.ShoppingList
import com.zizohanto.android.tobuy.core.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateShoppingList @Inject constructor(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, ShoppingList>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Unit?): Flow<ShoppingList> {
        return repository.createShoppingList()
    }
}