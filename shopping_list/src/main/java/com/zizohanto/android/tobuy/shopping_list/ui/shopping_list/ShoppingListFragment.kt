package com.zizohanto.android.tobuy.shopping_list.ui.shopping_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zizohanto.android.tobuy.shopping_list.ui.theme.ShoppingListTheme
import com.zizohanto.android.tobuy.core.view_binding.viewBinding
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.FragmentShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list.ShoppingListsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {

    @Inject
    lateinit var navigator: NavigationDispatcher

    private val binding: FragmentShoppingListBinding by viewBinding(FragmentShoppingListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shoppingList.setContent {
            ShoppingListTheme {
                ShoppingListsScreen(
                    listCLick = { shoppingListId ->
                        navigator.openShoppingListDetail(shoppingListId)
                    }
                )
            }
        }
    }
}