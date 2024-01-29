package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.shopping_list.usecase.base.FlowUseCase
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