package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.exception.requireParams
import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.shopping_list.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingListWithProducts @Inject constructor(
    private val repository: ShoppingListRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<String, ShoppingListWithProducts>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: String?): Flow<ShoppingListWithProducts> {
        requireParams(params)
        return repository.getShoppingListWithProducts(params)
    }
}