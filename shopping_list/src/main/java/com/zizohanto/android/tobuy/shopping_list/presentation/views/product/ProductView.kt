package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutProductsBinding
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductAdapter
import com.zizohanto.android.tobuy.shopping_list.ui.products.adapter.ProductViewListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
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

    fun setCoroutineScope(scope: CoroutineScope) {
        productAdapter.coroutineScope = scope
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
                productAdapter.notifyItemChanged(viewItems.size)
            }
            ProductViewState.DeleteShoppingList -> TODO()
            is ProductsViewState.Error -> TODO()
        }
    }

    override val intents: Flow<ProductsViewIntent>
        get() = productAdapter.intents
}

@Composable
fun ShoppingListTitle(
    shoppingList: ProductsViewItem.ShoppingListModel,
    listener: ProductViewListener?
) {
    var shoppingListTitle by rememberSaveable { mutableStateOf(shoppingList.name) }
    Surface {
        TextField(
            value = shoppingListTitle,
            textStyle = MaterialTheme.typography.subtitle2,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent
            ),
            onValueChange = {
                val title = it.trim()
                shoppingListTitle = title
                listener?.onShoppingListEdit(shoppingList.copy(name = shoppingListTitle))
            }
        )
    }
}

@Preview
@Composable
fun ShoppingListTitlePreview() {
    ShoppingListTitle(
        ProductsViewItem.ShoppingListModel("", "Weekend", 0.0, 0L, 0L),
        null
    )
}
