package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutProductsBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.ui.products.DEBOUNCE_PERIOD
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter
import com.zizohanto.android.tobuy.shopping_list.ui.products.checkDistinct
import com.zizohanto.android.tobuy.shopping_list.ui.products.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.android.view.clicks
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
                if (!binding.shoppingListTitle.textHasChanged()) {
                    binding.shoppingListTitle.setText(state.listWithProducts.shoppingList.name)
                }
                binding.shoppingListTitle.setChanged(false)
                productAdapter.submitList(state.listWithProducts.products)

                binding.shoppingListTitle.isVisible = true
                binding.products.isVisible = true
                binding.addNewProduct.isVisible = true
            }
            ProductViewState.DeleteShoppingList -> TODO()
            is ProductsViewState.Error -> TODO()
        }
    }

    fun saveShoppingList(shoppingList: ShoppingListModel): Flow<ProductsViewIntent> {
        return binding.shoppingListTitle.textChanges.checkDistinct
            .onEach { binding.shoppingListTitle.setChanged(true) }
            .map { ProductViewIntent.SaveShoppingList(shoppingList.copy(name = it)) }
    }

    fun saveProduct(shoppingList: ShoppingListModel): Flow<ProductsViewIntent> {
        return productAdapter.edits.debounce(DEBOUNCE_PERIOD).map { product ->
            ProductViewIntent.SaveProduct(product, shoppingList)
        }
    }

    fun createNewProduct(shoppingList: ShoppingListModel): Flow<ProductsViewIntent> {
        return binding.addNewProduct.clicks().map {
            ProductViewIntent.AddNewProduct(shoppingList.id)
        }
    }

    fun addNewProductAtPosition(shoppingList: ShoppingListModel): Flow<ProductsViewIntent> {
        return addNewProductIntent.asFlow().map { (pos, index) ->
            ProductViewIntent.AddNewProductAtPosition(shoppingList.id, pos, index)
        }
    }

    private val deleteProductIntent: Flow<ProductsViewIntent>
        get() = productAdapter.deletes.map { product ->
            ProductViewIntent.DeleteProduct(product.id)
        }

    override val intents: Flow<ProductsViewIntent>
        get() = deleteProductIntent
}
