package com.zizohanto.android.tobuy.shopping_list.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.zizohanto.android.tobuy.core.ext.safeOffer
import com.zizohanto.android.tobuy.core.view_binding.viewBinding
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.FragmentProductsBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.LoadShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.ProductsView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_products) {

    private val viewModel: ProductViewModel by viewModels()

    private val binding: FragmentProductsBinding by viewBinding(FragmentProductsBinding::bind)

    private val args: ProductFragmentArgs by navArgs()

    private var productViewListener: ProductViewListener? = null

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            loadShoppingListWithProducts.trySend(LoadShoppingListWithProducts(args.shoppingListId))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            navigationDispatcher.goBack()
        }
        binding.productsView.setContent {
            ProductsView(viewModel, productViewListener)
        }
        viewModel.processIntent(intents)
    }

    private val loadShoppingListWithProducts =
        ConflatedBroadcastChannel<LoadShoppingListWithProducts>()

    private val intents: Flow<ProductsViewIntent>
        get() {
            val flows = callbackFlow {
                val listener: ProductViewListener = object : ProductViewListener {
                    override fun onProductEdit(product: ProductsViewItem.ProductModel) {
                        safeOffer(ProductsViewIntent.ProductViewIntent.SaveProduct(product))
                    }

                    override fun onProductDelete(product: ProductsViewItem.ProductModel) {
                        safeOffer(ProductsViewIntent.ProductViewIntent.DeleteProduct(product))
                    }

                    override fun onAddNewProduct(shoppingListId: String, newProductPosition: Int) {
                        safeOffer(
                            ProductsViewIntent.ProductViewIntent.AddNewProductAtPosition(
                                shoppingListId,
                                newProductPosition
                            )
                        )
                    }

                    override fun onShoppingListEdit(shoppingList: ProductsViewItem.ShoppingListModel) {
                        safeOffer(
                            ProductsViewIntent.ProductViewIntent.SaveShoppingList(
                                shoppingList.copy(name = shoppingList.name)
                            )
                        )
                    }
                }
                productViewListener = listener
                awaitClose {
                    productViewListener = null
                }
            }
            return merge(
                loadShoppingListWithProducts.asFlow(),
                flows
            )
        }
}