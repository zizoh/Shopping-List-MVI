package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.core.ext.removeFirst
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.*
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
                    val updatedProducts = list.products.map {
                        if (it.id == savedProduct.id) savedProduct else it
                    }
                    list.copy(products = updatedProducts)
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
                val product: ProductModel = productMapper.mapToModel(result.product)
                val listWithProducts = previous.listWithProducts?.products.orEmpty().toMutableList().apply {
                    if (isEmpty()) {
                        add(product)
                    } else {
                        if (product.position in 0 until size) {
                            add(product.position, product)
                        } else {
                            add(product)
                        }
                    }
                }
                ProductsViewState(
                    listWithProducts = previous.listWithProducts?.copy(products = listWithProducts),
                )
            }
        }
    }
}