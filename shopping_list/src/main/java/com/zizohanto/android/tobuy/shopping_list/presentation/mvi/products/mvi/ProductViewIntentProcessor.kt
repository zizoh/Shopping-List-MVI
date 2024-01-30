package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.shopping_list.usecase.CreateProduct
import com.zizohanto.android.tobuy.shopping_list.usecase.DeleteProduct
import com.zizohanto.android.tobuy.shopping_list.usecase.GetShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.usecase.SaveProduct
import com.zizohanto.android.tobuy.shopping_list.usecase.SaveShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewIntent.ProductViewIntent.*
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewIntent.ProductViewIntent.DeleteShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewResult.ProductViewResult
import com.zizohanto.android.tobuy.shoppinglist.sq.Product
import com.zizohanto.android.tobuy.shoppinglist.sq.ShoppingList
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
            is ProductViewIntent.SaveProduct -> flow {
                val product: Product = productMapper.mapToDomain(viewIntent.product)
                saveProduct(product, product.shoppingListId)
                emit(ProductViewResult.ProductSaved(product))
            }
            is ProductViewIntent.DeleteProduct -> flow {
                val product: Product = productMapper.mapToDomain(viewIntent.product)
                deleteProduct(product)
                emit(ProductViewResult.ProductDeleted(product.id))
            }
            is ProductViewIntent.SaveShoppingList -> flow {
                val shoppingList: ShoppingList = listMapper.mapToDomain(viewIntent.shoppingList)
                saveShoppingList(shoppingList)

                emit(ProductViewResult.ShoppingListSaved(shoppingList))
            }
            is DeleteShoppingList -> flowOf(ProductViewResult.ShoppingListDeleted)
            is AddNewProductAtPosition -> createProduct(
                Pair(viewIntent.shoppingListId, viewIntent.newProductPosition)
            ).map { products ->
                ProductViewResult.ProductAddedAtPosition(products, viewIntent.newProductPosition)
            }
        }
    }

    private fun loadShoppingListWithProducts(
        shoppingListId: String
    ): Flow<ProductsViewResult> {
        return getShoppingListWithProducts(shoppingListId)
            .map(ProductViewResult::Success)
            .catch { error -> error.printStackTrace() }
    }
}