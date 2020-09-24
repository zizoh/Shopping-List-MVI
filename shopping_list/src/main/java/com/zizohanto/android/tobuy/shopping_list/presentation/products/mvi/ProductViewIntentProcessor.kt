package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.Product.Companion.createNewProduct
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingList.Companion.createNewShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.usecase.GetShoppingListWithProducts
import com.zizohanto.android.tobuy.domain.usecase.SaveProduct
import com.zizohanto.android.tobuy.domain.usecase.SaveShoppingList
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductIntentProcessor
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.ProductViewResult
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.ShoppingListViewResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductViewIntentProcessor @Inject constructor(
    private val getShoppingListWithProducts: GetShoppingListWithProducts,
    private val saveProduct: SaveProduct,
    private val saveShoppingList: SaveShoppingList,
    private val productMapper: ProductModelMapper,
    private val shoppingListMapper: ShoppingListModelMapper
) : ProductIntentProcessor {

    override fun intentToResult(viewIntent: ProductsViewIntent): Flow<ProductsViewResult> {
        return when (viewIntent) {
            ProductsViewIntent.Idle -> flowOf(ProductsViewResult.Idle)
            is ShoppingListViewIntent.LoadShoppingListWithProducts -> {
                if (isNewShoppingList(viewIntent)) loadNewShoppingList()
                else loadShoppingListWithProducts(viewIntent.shoppingListId!!)
            }
            is ShoppingListViewIntent.SaveShoppingList -> flow {
                saveShoppingList(shoppingListMapper.mapToDomain(viewIntent.shoppingList))
            }
            is ProductViewIntent.EditProduct -> flow {
                ProductViewResult.EditProduct(productMapper.mapToDomain(viewIntent.product))
            }
            is ProductViewIntent.SaveProduct -> flow {
                saveProduct(productMapper.mapToDomain(viewIntent.product))
            }
            is ProductViewIntent.DeleteProduct -> flow {
                ProductViewResult.DeleteProduct(viewIntent.productId)
            }
        }
    }

    private fun isNewShoppingList(viewIntent: ShoppingListViewIntent.LoadShoppingListWithProducts) =
        viewIntent.shoppingListId == null

    private fun loadNewShoppingList(): Flow<ProductsViewResult> {
        val list: ShoppingList = createNewShoppingList()
        return flow {
            val product: Product = createNewProduct(list.id)
            emit(ProductViewResult.FirstProduct(product))
            emit(ShoppingListViewResult.NewShoppingList(list))
        }
    }

    private fun loadShoppingListWithProducts(shoppingListId: String): Flow<ProductsViewResult> {
        return getShoppingListWithProducts(shoppingListId)
            .flatMapLatest { listWithProducts: ShoppingListWithProducts ->
                merge(
                    flowOf(ShoppingListViewResult.Success(listWithProducts.shoppingList)),
                    flowOf(ProductViewResult.Success(listWithProducts.products))
                )
            }.catch { error ->
                error.printStackTrace()
            }
    }
}