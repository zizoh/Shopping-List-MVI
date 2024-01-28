package com.zizohanto.android.tobuy.domain.usecase

import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import com.zizohanto.android.tobuy.domain.sq.Product
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(product: Product) {
        return withContext(postExecutionThread.io) {
            repository.deleteProduct(product)
        }
    }
}