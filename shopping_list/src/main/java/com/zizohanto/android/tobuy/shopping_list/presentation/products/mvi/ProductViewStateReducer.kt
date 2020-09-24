package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.*
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ShoppingListState
import javax.inject.Inject

class ProductViewStateReducer @Inject constructor(
    private val listMapper: ShoppingListModelMapper,
    private val productMapper: ProductModelMapper
) : ProductStateReducer {

    override fun reduce(
        previous: ProductsViewState,
        result: ProductsViewResult
    ): ProductsViewState {
        return when (result) {
            Idle -> ProductsViewState.Idle
            ShoppingListViewResult.NewShoppingList -> {
                ShoppingListState.NewShoppingList
            }
            is ShoppingListViewResult.Success -> {
                val listModel: ShoppingListModel = listMapper.mapToModel(result.shoppingList)
                ShoppingListState.Success(listModel)
            }
            is ProductViewResult.FirstProduct -> {
                val product: ProductModel = productMapper.mapToModel(result.product)
                ProductViewState.FirstProduct(product)
            }
            is ProductViewResult.Success -> {
                val products: List<ProductModel> = productMapper.mapToModelList(result.products)
                ProductViewState.Success(products)
            }
            is ProductViewResult.EditProduct -> {
                val product: ProductModel = productMapper.mapToModel(result.product)
                ProductViewState.EditProduct(product)
            }
            is ProductViewResult.DeleteProduct -> {
                ProductViewState.DeleteProduct(result.productId)
            }
        }
    }
}