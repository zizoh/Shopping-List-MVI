package com.zizohanto.android.tobuy.domain.usecase

import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(productId: String) {
        return withContext(postExecutionThread.io) {
            repository.deleteProduct(productId)
        }
    }
}