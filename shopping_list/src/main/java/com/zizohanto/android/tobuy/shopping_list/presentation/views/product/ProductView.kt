package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutProductsBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ShoppingListState
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@AndroidEntryPoint
class ProductView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet),
    MVIView<ProductViewIntent, ProductsViewState> {

    @Inject
    lateinit var productAdapter: ProductAdapter

    private var binding: LayoutProductsBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutProductsBinding.inflate(inflater, this, true)
        binding.products.adapter = productAdapter
    }

    override fun render(state: ProductsViewState) {
        when (state) {
            ProductsViewState.Idle -> {
            }
            is ShoppingListState.NewShoppingList -> {
                with(binding) {
                    shoppingListTitle.text = state.listModel.name
                }
            }
            is ShoppingListState.Success -> {
                with(binding) {
                    shoppingListTitle.text = state.listModel.name
                }
            }
            is ProductViewState.FirstProduct -> {
                val newProduct: List<ProductModel> = listOf(state.product)
                productAdapter.submitList(newProduct)
            }
            is ProductViewState.Success -> {
                productAdapter.submitList(state.products)
            }
            is ProductViewState.EditProduct -> {
                TODO()
            }
            is ProductViewState.DeleteProduct -> {
                TODO()
            }
        }
    }

    override val intents: Flow<ProductViewIntent>
        get() = emptyFlow()
}
