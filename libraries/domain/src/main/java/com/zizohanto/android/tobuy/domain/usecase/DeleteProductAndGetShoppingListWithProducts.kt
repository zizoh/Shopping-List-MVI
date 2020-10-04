package com.zizohanto.android.tobuy.domain.usecase

import com.zizohanto.android.tobuy.domain.executor.PostExecutionThread
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.repository.ProductRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProductAndGetShoppingListWithProducts @Inject constructor(
    private val repository: ProductRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(product: Product): ShoppingListWithProducts {
        return withContext(postExecutionThread.io) {
            repository.deleteProductAndGetShoppingListWithProducts(product)
        }
    }
}