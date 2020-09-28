package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutProductsBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter
import com.zizohanto.android.tobuy.shopping_list.ui.products.checkDistinct
import com.zizohanto.android.tobuy.shopping_list.ui.products.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProductView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet),
    MVIView<ProductsViewIntent, ProductsViewState> {

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
            is ProductViewState.Success -> {
                with(binding) {
                    shoppingListTitle.append(state.listWithProducts.shoppingList.name)
                }
                productAdapter.submitList(state.listWithProducts.products)
            }
            is ProductViewState.SaveProduct -> {
                productAdapter.submitList(state.listWithProducts.products)
            }
            is ProductViewState.DeleteProduct -> {
                productAdapter.submitList(state.listWithProducts.products)
            }
            ProductViewState.SaveShoppingList -> {
            }
            ProductViewState.DeleteShoppingList -> TODO()
            is ProductsViewState.Error -> TODO()
        }
    }

    fun saveShoppingList(shoppingList: ShoppingListModel): Flow<ProductsViewIntent> {
        return binding.shoppingListTitle.textChanges.checkDistinct.map {
            ProductsViewIntent.ProductViewIntent.SaveShoppingList(shoppingList.copy(name = it))
        }
    }

    override val intents: Flow<ProductsViewIntent>
        get() = emptyFlow()
}
