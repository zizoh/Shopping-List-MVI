package com.zizohanto.android.tobuy.domain.usecase

import com.zizohanto.android.tobuy.domain.exception.requireParams
import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import com.zizohanto.android.tobuy.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProductAtPosition @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Pair<String, Int>, Product>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Pair<String, Int>?): Flow<Product> {
        val (id: String, position: Int) = requireParams(params)
        return repository.createProductAtPosition(id, position)
    }
}