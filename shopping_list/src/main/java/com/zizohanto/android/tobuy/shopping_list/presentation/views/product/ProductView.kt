package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutProductsBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.ui.products.DEBOUNCE_PERIOD
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProductView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet),
    MVIView<ProductsViewIntent, ProductsViewState> {

    @Inject
    lateinit var productAdapter: ProductAdapter

    private var binding: LayoutProductsBinding

    private val addNewProductIntent =
        ConflatedBroadcastChannel<Pair<Int, Int>>()

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutProductsBinding.inflate(inflater, this, true)
        binding.products.adapter = productAdapter.apply {
            addNewProductListener = { position, index ->
                addNewProductIntent.safeOffer(Pair(position, index))
            }
        }
    }

    override fun render(state: ProductsViewState) {
        when (state) {
            ProductsViewState.Idle -> {
            }
            is ProductViewState.Success -> {
                binding.products.isVisible = true
                val viewItems: MutableList<ProductsViewItem> =
                    mutableListOf<ProductsViewItem>().apply {
                        add(state.listWithProducts.shoppingList)
                        addAll(state.listWithProducts.products)
                        add(ProductsViewItem.ButtonItem)
                    }
                productAdapter.submitList(viewItems)
            }
            ProductViewState.DeleteShoppingList -> TODO()
            is ProductsViewState.Error -> TODO()
        }
    }

    fun saveShoppingList(): Flow<ProductsViewIntent> {
        return productAdapter.shoppingListEdits.debounce(DEBOUNCE_PERIOD).map { shoppingList ->
            ProductViewIntent.SaveShoppingList(shoppingList.copy(name = shoppingList.name))
        }
    }

    fun saveProduct(shoppingListId: String): Flow<ProductsViewIntent> {
        return productAdapter.productEdits.debounce(DEBOUNCE_PERIOD).map { product ->
            ProductViewIntent.SaveProduct(product, shoppingListId)
        }
    }

    fun addNewProductAtPosition(shoppingListId: String): Flow<ProductsViewIntent> {
        return addNewProductIntent.asFlow().map { (pos, index) ->
            ProductViewIntent.AddNewProductAtPosition(shoppingListId, pos, index)
        }
    }

    private val deleteProductIntent: Flow<ProductsViewIntent>
        get() = productAdapter.deletes.map { (id) ->
            ProductViewIntent.DeleteProduct(id)
        }

    override val intents: Flow<ProductsViewIntent>
        get() = deleteProductIntent
}
