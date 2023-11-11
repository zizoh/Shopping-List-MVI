package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.core.ext.removeFirst
import com.zizohanto.android.tobuy.core.ext.replaceFirst
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem.ShoppingListModel
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
                ProductViewState.Success(
                    listWithProducts,
                    getSuccessViewItems(listWithProducts.shoppingList, listWithProducts.products)
                )
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
                        ProductViewState.Success(
                            listWithProducts.copy(products = products),
                            getSuccessViewItems(listWithProducts.shoppingList, products)
                        )
                    }
                    ProductViewState.DeleteShoppingList -> TODO()
                    is ProductsViewState.Error -> ProductsViewState.Idle

                }
            }
            is ProductViewResult.ProductDeleted -> {
                when (previous) {
                    ProductsViewState.Idle -> ProductsViewState.Idle
                    is ProductViewState.Success -> {
                        val listWithProducts: ShoppingListWithProductsModel =
                            previous.listWithProducts
                        val products: List<ProductModel> =
                            listWithProducts.products.removeFirst { it.id == result.productId }
                        ProductViewState.Success(
                            listWithProducts.copy(products = products),
                            getSuccessViewItems(listWithProducts.shoppingList, products)
                        )
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
                        with(listWithProducts) {
                            ProductViewState.Success(
                                this,
                                getSuccessViewItems(this.shoppingList, this.products)
                            )
                        }
                    }
                    ProductViewState.DeleteShoppingList -> TODO()
                    is ProductsViewState.Error -> ProductsViewState.Idle
                }
            }
            ProductViewResult.ShoppingListDeleted -> {
                ProductViewState.DeleteShoppingList
            }
            is Error -> TODO()
            is ProductViewResult.ProductAddedAtPosition -> when (previous) {
                ProductsViewState.Idle -> ProductsViewState.Idle
                is ProductViewState.Success -> {
                    val product: ProductModel = productMapper.mapToModel(result.product)
                    val currentList: MutableList<ProductModel> =
                        previous.listWithProducts.products.toMutableList()
                    if (currentList.isEmpty()) {
                        currentList.add(product)
                    } else {
                        currentList.apply { add(product.position, product) }
                    }
                    ProductViewState.Success(
                        previous.listWithProducts.copy(products = currentList),
                        getSuccessViewItems(
                            previous.listWithProducts.shoppingList,
                            currentList
                        )
                    )
                }
                ProductViewState.DeleteShoppingList -> TODO()
                is ProductsViewState.Error -> TODO()

            }
        }
    }

    private fun getSuccessViewItems(
        shoppingList: ShoppingListModel,
        products: List<ProductModel>
    ): List<ProductsViewItem> {
        return buildList {
            add(shoppingList)
            addAll(products)
            add(ProductsViewItem.ButtonItem(shoppingList.id, products.size))
        }
    }

}