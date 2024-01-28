package com.zizohanto.android.tobuy.core.usecase

import com.zizohanto.android.tobuy.core.executor.PostExecutionThread
import com.zizohanto.android.tobuy.core.repository.ProductRepository
import com.zizohanto.android.tobuy.domain.sq.Product
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveProduct @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(product: Product, shoppingListId: String) {
        withContext(postExecutionThread.io) {
            repository.saveProduct(product, shoppingListId)
        }
    }
}