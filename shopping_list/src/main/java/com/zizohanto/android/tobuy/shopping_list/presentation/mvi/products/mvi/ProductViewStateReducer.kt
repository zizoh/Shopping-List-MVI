package com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi

import com.zizohanto.android.tobuy.shopping_list.ext.removeFirst
import com.zizohanto.android.tobuy.shopping_list.ext.replaceFirst
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewResult.Error
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewResult.Idle
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewResult.ProductViewResult
import javax.inject.Inject

class ProductViewStateReducer @Inject constructor(
    private val listWithProductsMapper: ShoppingListWithProductsModelMapper,
    private val shoppingListModelMapper: ShoppingListModelMapper,
    private val productMapper: ProductModelMapper
) : ProductStateReducer {

    override fun reduce(
        previous: ProductsViewState,
        result: ProductsViewResult
    ): ProductsViewState {
        return when (result) {
            Idle -> ProductsViewState()
            is ProductViewResult.Success -> {
                val listWithProducts: ShoppingListWithProductsModel =
                    listWithProductsMapper.mapToModel(result.listWithProducts)
                ProductsViewState(listWithProducts = listWithProducts)
            }
            is ProductViewResult.ProductSaved -> {
                val listWithProducts = previous.listWithProducts?.let { list ->
                    val savedProduct: ProductModel = productMapper.mapToModel(result.product)
                    val products: List<ProductModel> =
                        list.products.replaceFirst(savedProduct) { it.id == savedProduct.id }
                    list.copy(products = products)
                }
                ProductsViewState(listWithProducts = listWithProducts)
            }
            is ProductViewResult.ProductDeleted -> {
                val listWithProducts = previous.listWithProducts?.let { list ->
                    val products: List<ProductModel> =
                        list.products.removeFirst { it.id == result.productId }
                    list.copy(products = products)
                }
                ProductsViewState(listWithProducts = listWithProducts)
            }
            is ProductViewResult.ShoppingListSaved -> {
                val listWithProducts = previous.listWithProducts?.let { list ->
                    val shoppingList: ShoppingListModel =
                        shoppingListModelMapper.mapToModel(result.shoppingList)
                    list.copy(shoppingList = shoppingList)
                }
                ProductsViewState(listWithProducts = listWithProducts)
            }
            ProductViewResult.ShoppingListDeleted -> {
                ProductsViewState()
            }
            is Error -> TODO()
            is ProductViewResult.ProductAddedAtPosition -> {
                ProductsViewState(
                    listWithProducts = previous.listWithProducts?.copy(
                        products = result.products.map { productMapper.mapToModel(it) }
                    )
                )
            }
        }
    }
}