package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.usecase.CreateProduct
import com.zizohanto.android.tobuy.domain.usecase.GetShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.usecase.SaveProduct
import com.zizohanto.android.tobuy.domain.usecase.SaveShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.*
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.ProductViewResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductViewIntentProcessor @Inject constructor(
    private val getShoppingListWithProducts: GetShoppingListWithProducts,
    private val mapper: ShoppingListWithProductsModelMapper,
    private val saveProduct: SaveProduct,
    private val productMapper: ProductModelMapper,
    private val saveShoppingList: SaveShoppingList,
    private val listMapper: ShoppingListModelMapper,
    private val createProduct: CreateProduct
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
                saveProduct(productMapper.mapToDomain(viewIntent.product))

                val product: Product = productMapper.mapToDomain(viewIntent.product)
                emit(ProductViewResult.ProductSaved(product))
            }
            is DeleteProduct -> flow {
                val listWithProducts: ShoppingListWithProducts =
                    mapper.mapToDomain(viewIntent.shoppingListWithProductsModel)
                emit(ProductViewResult.ProductDeleted(listWithProducts))
            }
            is ProductViewIntent.SaveShoppingList -> flow {
                val shoppingList: ShoppingList =
                    listMapper.mapToDomain(viewIntent.shoppingList)
                saveShoppingList(shoppingList)

                emit(ProductViewResult.ShoppingListSaved(shoppingList))
            }
            is DeleteShoppingList -> flowOf(ProductViewResult.ShoppingListDeleted)
        }
    }

    private fun loadShoppingListWithNewProduct(shoppingListId: String): Flow<ProductViewResult.ProductAdded> {
        val productFlow: Flow<Product> = createProduct(shoppingListId)
        val listWithProductsFlow: Flow<ShoppingListWithProducts> =
            getShoppingListWithProducts(shoppingListId)
        return productFlow.zip(listWithProductsFlow) { product: Product, listWithProducts ->
            val products: MutableList<Product> = mutableListOf()
            val elements = listWithProducts.products
            products.addAll(elements)
            products.add(product)
            ProductViewResult.ProductAdded(
                listWithProducts.copy(
                    shoppingList = listWithProducts.shoppingList,
                    products = products
                )
            )
        }
    }

    private fun loadShoppingListWithProducts(
        shoppingListId: String
    ): Flow<ProductsViewResult> {
        return getShoppingListWithProducts(shoppingListId)
            .map { shoppingListWithProducts ->
                ProductViewResult.Success(shoppingListWithProducts)
            }.catch { error ->
                error.printStackTrace()
            }
    }

}