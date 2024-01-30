package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingLists @Inject constructor(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, List<ShoppingListWithProducts>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Unit?): Flow<List<ShoppingListWithProducts>> {
        return repository.getAllShoppingLists()
    }
}