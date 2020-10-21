package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.usecase.*
import com.zizohanto.android.tobuy.domain.usecase.DeleteProduct
import com.zizohanto.android.tobuy.domain.usecase.SaveProduct
import com.zizohanto.android.tobuy.domain.usecase.SaveShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.*
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.DeleteShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.ProductViewResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductViewIntentProcessor @Inject constructor(
    private val getShoppingListWithProducts: GetShoppingListWithProducts,
    private val saveProduct: SaveProduct,
    private val productMapper: ProductModelMapper,
    private val saveShoppingList: SaveShoppingList,
    private val listMapper: ShoppingListModelMapper,
    private val createProduct: CreateProduct,
    private val deleteProduct: DeleteProduct
) : ProductIntentProcessor {

    override fun intentToResult(viewIntent: ProductsViewIntent): Flow<ProductsViewResult> {
        return when (viewIntent) {
            ProductsViewIntent.Idle -> flowOf(ProductsViewResult.Idle)
            is LoadShoppingListWithProducts -> {
                loadShoppingListWithProducts(viewIntent.shoppingListId)
            }
            is AddNewProduct -> {
                createProduct(viewIntent.shoppingListId).map { product ->
                    ProductViewResult.ProductAdded(product)
                }
            }
            is ProductViewIntent.SaveProduct -> flow {
                saveProduct(
                    productMapper.mapToDomain(viewIntent.product),
                    listMapper.mapToDomain(viewIntent.shoppingList)
                )

                val product: Product = productMapper.mapToDomain(viewIntent.product)
                emit(ProductViewResult.ProductSaved(product))
            }
            is ProductViewIntent.DeleteProduct -> flow {
                val product: Product = productMapper.mapToDomain(viewIntent.product)
                deleteProduct(product)
                emit(ProductViewResult.ProductDeleted(viewIntent.position))
            }
            is ProductViewIntent.SaveShoppingList -> flow {
                val shoppingList: ShoppingList = listMapper.mapToDomain(viewIntent.shoppingList)
                saveShoppingList(shoppingList)

                emit(ProductViewResult.ShoppingListSaved(shoppingList))
            }
            is DeleteShoppingList -> flowOf(ProductViewResult.ShoppingListDeleted)
        }
    }

    private fun loadShoppingListWithProducts(
        shoppingListId: String
    ): Flow<ProductsViewResult> {
        return getShoppingListWithProducts(shoppingListId)
            .map { listWithProducts ->
                ProductViewResult.Success(
                    listWithProducts.copy(
                        products = sortList(listWithProducts.products)
                    )
                )
            }.catch { error ->
                error.printStackTrace()
            }
    }

    private fun sortList(products: List<Product>): List<Product> {
        return products.sortedBy { it.dateAdded }
    }

}