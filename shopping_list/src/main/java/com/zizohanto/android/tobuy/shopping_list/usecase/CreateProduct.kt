package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.exception.requireParams
import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.repository.ProductRepository
import com.zizohanto.android.tobuy.shopping_list.usecase.base.FlowUseCase
import com.zizohanto.android.tobuy.shoppinglist.sq.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Pair<String, Int>, List<Product>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Pair<String, Int>?): Flow<List<Product>> {
        val (id: String, newProductPosition: Int) = requireParams(params)
        return repository.createProductAtPosition(id, newProductPosition)
    }
}