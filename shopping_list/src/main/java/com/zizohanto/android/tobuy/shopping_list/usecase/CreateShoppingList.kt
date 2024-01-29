package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.shopping_list.usecase.base.FlowUseCase
import com.zizohanto.android.tobuy.shoppinglist.sq.ShoppingList
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