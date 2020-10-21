package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.core.ext.replaceFirst
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.*
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
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
            Idle -> ProductsViewState.Idle
            is ProductViewResult.Success -> {
                val listWithProducts: ShoppingListWithProductsModel =
                    listWithProductsMapper.mapToModel(result.listWithProducts)
                ProductViewState.Success(listWithProducts)
            }
            is ProductViewResult.ProductSaved -> {
                when (previous) {
                    ProductsViewState.Idle -> ProductsViewState.Idle
                    is ProductViewState.Success -> {
                        val listWithProducts: ShoppingListWithProductsModel =
                            previous.listWithProducts
                        val savedProduct: ProductModel = productMapper.mapToModel(result.product)
                        val products: List<ProductModel> =
                            listWithProducts.products.replaceFirst(savedProduct) { it.id == savedProduct.id }
                        ProductViewState.Success(listWithProducts.copy(products = products))
                    }
                    ProductViewState.DeleteShoppingList -> TODO()
                    is ProductsViewState.Error -> ProductsViewState.Idle

                }
            }
            is ProductViewResult.ProductAdded -> {
                when (previous) {
                    ProductsViewState.Idle -> ProductsViewState.Idle

                    is ProductViewState.Success -> {
                        val listWithProducts: ShoppingListWithProductsModel =
                            previous.listWithProducts
                        val mutableList = listWithProducts.products.toMutableList()
                        val product: ProductModel = productMapper.mapToModel(result.product)
                        mutableList.add(product)
                        ProductViewState.Success(listWithProducts.copy(products = mutableList))
                    }
                    ProductViewState.DeleteShoppingList -> TODO()

                    is ProductsViewState.Error -> TODO()

                }
            }
            is ProductViewResult.ProductDeleted -> {
                when (previous) {
                    ProductsViewState.Idle -> ProductsViewState.Idle
                    is ProductViewState.Success -> {
                        val listWithProducts: ShoppingListWithProductsModel =
                            previous.listWithProducts
                        val products: List<ProductModel> = listWithProducts.products
                        val mutableList = products.toMutableList()
                        if (mutableList.size == 1) {
                            ProductViewState.Success(listWithProducts.copy(products = emptyList()))
                        } else {
                            mutableList.removeAt(result.position)
                            ProductViewState.Success(listWithProducts.copy(products = mutableList))
                        }
                    }
                    ProductViewState.DeleteShoppingList -> TODO()
                    is ProductsViewState.Error -> TODO()
                }
            }
            is ProductViewResult.ShoppingListSaved -> {
                when (previous) {
                    ProductsViewState.Idle -> ProductsViewState.Idle
                    is ProductViewState.Success -> {
                        val shoppingListModel: ShoppingListModel =
                            shoppingListModelMapper.mapToModel(result.shoppingList)
                        val listWithProducts: ShoppingListWithProductsModel =
                            previous.listWithProducts.copy(shoppingList = shoppingListModel)
                        ProductViewState.Success(listWithProducts)
                    }
                    ProductViewState.DeleteShoppingList -> TODO()
                    is ProductsViewState.Error -> ProductsViewState.Idle
                }
            }
            ProductViewResult.ShoppingListDeleted -> {
                ProductViewState.DeleteShoppingList
            }
            is Error -> TODO()
        }
    }

}