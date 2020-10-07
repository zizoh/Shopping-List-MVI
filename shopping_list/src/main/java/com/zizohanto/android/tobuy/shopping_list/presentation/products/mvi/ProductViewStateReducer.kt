package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductStateReducer
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewResult.*
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import javax.inject.Inject

class ProductViewStateReducer @Inject constructor(
    private val listWithProductsMapper: ShoppingListWithProductsModelMapper,
    private val mapper: ProductModelMapper
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
            is ProductViewResult.ProductAdded -> {
                val product: ProductModel = mapper.mapToModel(result.product)
                ProductViewState.ProductAdded(product)
            }
            is ProductViewResult.ProductSaved -> {
                ProductViewState.SaveProduct
            }
            is ProductViewResult.ProductDeleted -> {
                val listWithProducts: ShoppingListWithProductsModel =
                    listWithProductsMapper.mapToModel(result.shoppingListWithProducts)
                ProductViewState.DeleteProduct(listWithProducts)
            }
            is ProductViewResult.ShoppingListSaved -> {
                ProductViewState.SaveShoppingList
            }
            ProductViewResult.ShoppingListDeleted -> {
                ProductViewState.DeleteShoppingList
            }
            is Error -> TODO()
        }
    }
}