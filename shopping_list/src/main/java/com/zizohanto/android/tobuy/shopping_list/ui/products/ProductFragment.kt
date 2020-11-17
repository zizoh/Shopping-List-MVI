package com.zizohanto.android.tobuy.shopping_list.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.zizohanto.android.tobuy.core.ext.observe
import com.zizohanto.android.tobuy.core.view_binding.viewBinding
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.FragmentProductsBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewIntent.ProductViewIntent.LoadShoppingListWithProducts
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_products),
    MVIView<ProductsViewIntent, ProductsViewState> {

    private val viewModel: ProductViewModel by viewModels()

    private val binding: FragmentProductsBinding by viewBinding(FragmentProductsBinding::bind)

    private val args: ProductFragmentArgs by navArgs()

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            loadShoppingListWithProducts.offer(LoadShoppingListWithProducts(args.shoppingListId))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        binding.toolbar.setNavigationOnClickListener {
            navigationDispatcher.goBack()
        }

        binding.productsView.setCoroutineScope(viewModel.viewModelScope)
        viewModel.processIntent(intents)
    }

    override fun render(state: ProductsViewState) {
        binding.productsView.render(state)
    }

    private val loadShoppingListWithProducts =
        ConflatedBroadcastChannel<LoadShoppingListWithProducts>()

    override val intents: Flow<ProductsViewIntent>
        get() = merge(
            loadShoppingListWithProducts.asFlow(),
            binding.productsView.intents
        )
}