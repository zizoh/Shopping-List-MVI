package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.exception.requireParams
import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.repository.ShoppingListRepository
import com.zizohanto.android.tobuy.usecase.base.FlowUseCase
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