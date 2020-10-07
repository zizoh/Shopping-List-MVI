package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.usecase.*
import com.zizohanto.android.tobuy.domain.usecase.SaveProduct
import com.zizohanto.android.tobuy.domain.usecase.SaveShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.*
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
    private val deleteProduct: DeleteProductAndGetShoppingListWithProducts
) : ProductIntentProcessor {

    override fun intentToResult(viewIntent: ProductsViewIntent): Flow<ProductsViewResult> {
        return when (viewIntent) {
            ProductsViewIntent.Idle -> flowOf(ProductsViewResult.Idle)
            is LoadShoppingListWithProducts -> {
                loadShoppingListWithProducts(viewIntent.shoppingListId)
            }
            is AddNewProduct -> {
                loadShoppingListWithNewProduct(viewIntent.shoppingListId)
            }
            is ProductViewIntent.SaveProduct -> flow {
                saveProduct(
                    productMapper.mapToDomain(viewIntent.product),
                    listMapper.mapToDomain(viewIntent.shoppingList)
                )

                val product: Product = productMapper.mapToDomain(viewIntent.product)
                emit(ProductViewResult.ProductSaved(product))
            }
            is DeleteProduct -> flow {
                val product: Product = productMapper.mapToDomain(viewIntent.product)
                val listWithProducts: ShoppingListWithProducts = deleteProduct(product)
                emit(ProductViewResult.ProductDeleted(listWithProducts))
            }
            is ProductViewIntent.SaveShoppingList -> flow {
                val shoppingList: ShoppingList = listMapper.mapToDomain(viewIntent.shoppingList)
                saveShoppingList(shoppingList)

                emit(ProductViewResult.ShoppingListSaved(shoppingList))
            }
            is DeleteShoppingList -> flowOf(ProductViewResult.ShoppingListDeleted)
        }
    }

    private fun loadShoppingListWithNewProduct(shoppingListId: String): Flow<ProductViewResult.ProductAdded> {
        return createProduct(shoppingListId).map { product: Product ->
            ProductViewResult.ProductAdded(product)
        }
    }

    private fun getSortedListOfProducts(
        existingProducts: List<Product>,
        product: Product
    ): List<Product> {
        val products: MutableList<Product> = mutableListOf()
        products.addAll(existingProducts)
        products.add(product)
        return sortList(products)
    }

    private fun sortList(products: MutableList<Product>): List<Product> {
        return products.sortedBy { it.dateAdded }
    }

    private fun loadShoppingListWithProducts(
        shoppingListId: String
    ): Flow<ProductsViewResult> {
        return getShoppingListWithProducts(shoppingListId)
            .map { listWithProducts ->
                ProductViewResult.Success(
                    listWithProducts.copy(
                        products = sortList(listWithProducts.products.toMutableList())
                    )
                )
            }.catch { error ->
                error.printStackTrace()
            }
    }

}