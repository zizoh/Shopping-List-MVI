package com.zizohanto.android.tobuy.shopping_list.usecase

import com.zizohanto.android.tobuy.shopping_list.executor.PostExecutionThread
import com.zizohanto.android.tobuy.shopping_list.repository.ProductRepository
import com.zizohanto.android.tobuy.shoppinglist.sq.Product
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