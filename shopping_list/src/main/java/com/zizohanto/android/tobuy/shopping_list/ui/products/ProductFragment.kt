package com.zizohanto.android.tobuy.shopping_list.ui.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zizohanto.android.tobuy.core.view_binding.viewBinding
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.FragmentProductsBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.views.product.ProductsScreen
import com.zizohanto.android.tobuy.shopping_list.ui.theme.ShoppingListTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_products) {

    private val binding: FragmentProductsBinding by viewBinding(FragmentProductsBinding::bind)

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productsView.setContent {
            ShoppingListTheme {
                ProductsScreen { navigationDispatcher.goBack() }
            }
        }
    }
}