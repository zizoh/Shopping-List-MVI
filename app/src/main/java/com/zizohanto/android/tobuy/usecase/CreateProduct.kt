package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.exception.requireParams
import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.ProductRepository
import com.zizohanto.android.tobuy.usecase.base.FlowUseCase
import com.zizohanto.android.tobuy.sq.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class CreateProduct(
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