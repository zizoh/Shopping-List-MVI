package com.zizohanto.android.tobuy.usecase

import com.zizohanto.android.tobuy.executor.PostExecutionThread
import com.zizohanto.android.tobuy.repository.ProductRepository
import com.zizohanto.android.tobuy.sq.Product
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